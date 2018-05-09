package com.pulingle.message_service.service.impl;

import com.pulingle.message_service.domain.dto.MessageDTO;
import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.dto.UserIdListDTO;
import com.pulingle.message_service.domain.entity.Message;
import com.pulingle.message_service.domain.entity.UserBasicInfo;
import com.pulingle.message_service.domain.entity.User_info;
import com.pulingle.message_service.feign.OutUserInfoFeign;
import com.pulingle.message_service.mapper.MessageMapper;
import com.pulingle.message_service.service.MessageService;
import com.pulingle.message_service.utils.RespondBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by @杨健 on 2018/4/1 17:47
 *
 * @Des: 消息处理服务实现类
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    //UserBasicInfo类的键值名前缀
    private final String USER_BASIC_INFO = "UBI";
    //好友列表的字符串前缀
    final String FRIENDS_LIST_STR = "FL";

    @Resource
    MessageMapper messageMapper;

    @Autowired
    OutUserInfoFeign outUserInfoFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param: sendUser, receUser, content, type
     * @return: RespondBody
     * @Des: 发送消息业务处理
     */
    @Override
    public RespondBody sendMessage(Message message) {
        RespondBody respondBody;
        try {
            Date date = new Date();
            message.setSendTime(date);
            messageMapper.send(message);
            respondBody = RespondBuilder.buildNormalResponse("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            respondBody = RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }

    /**
     * @param sendUserId 发送请求id
     * @param receUserId 接受请求id
     * @return 返回体
     */
    @Override
    public RespondBody sendFriendRequest(long sendUserId, long receUserId) {
        RespondBody respondBody;
        try {
            //先通过Feign调用接口判断是否已经是好友
            User_info user_info=new User_info();
            user_info.setUser_id(sendUserId);
            user_info.setFriends_list(FRIENDS_LIST_STR+receUserId);
            RespondBody result=outUserInfoFeign.isFriend(user_info);
            if(result.getStatus().equals("0"))
                return RespondBuilder.buildErrorResponse("调用Feign判断是否好友失败");
            else {
                int flag=(Integer)result.getData();
                //0不是好友，1是好友
                if(flag==0){
                    Message message = new Message();
                    message.setSendUserId(sendUserId);
                    message.setReceUserId(receUserId);
                    message.setType(2);
                    message.setReadStatus(0);
                    message.setSendTime(new Date());
                    message.setDeleteStatus(0);
                    message.setContent("申请添加你为好友");
                    //判断是否已经发送好友请求
                    if(messageMapper.haveSendFriendRequest(message)>0)
                        respondBody=RespondBuilder.buildErrorResponse("好友申请已发送");
                    else {
                        messageMapper.addFriendRequest(message);
                        respondBody = RespondBuilder.buildNormalResponse("请求成功！");
                    }
                }else
                    respondBody=RespondBuilder.buildErrorResponse("对方已经是你好友");
            }
        } catch (Exception e) {
            respondBody = RespondBuilder.buildErrorResponse("请求失败！");
            e.printStackTrace();
        }
        return respondBody;
    }

    @Override
    public RespondBody getFriendRequest(long userId) {
        RespondBody respondBody;
        try {
            List<Map> list = messageMapper.getFriendRequest(userId);
            for(Map map:list){
                String id=String.valueOf(map.get("sendUserId"));
                UserBasicInfo userBasicInfo=new UserBasicInfo();
                userBasicInfo.setUserId(Long.valueOf(id));
                RespondBody userInfoRespondBody=outUserInfoFeign.getUserInfo(userBasicInfo);
                map.put("userInfo",userInfoRespondBody.getData());
            }
            respondBody = RespondBuilder.buildNormalResponse(list);
        } catch (Exception e) {
            e.printStackTrace();
            respondBody = RespondBuilder.buildErrorResponse("请求失败！");
        }
        return respondBody;
    }

    /**
     * @param messageId 消息id
     * @return 返回体
     * 删除对应id 的好友请求消息
     */
    @Override
    public RespondBody deleteFriendRequest(long messageId) {
        RespondBody respondBody;
        try {
            messageMapper.deleteFriendRequest(messageId);
            respondBody = RespondBuilder.buildNormalResponse("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            respondBody = RespondBuilder.buildErrorResponse("删除失败！");
        }
        return respondBody;
    }

    @Override
    public RespondBody queryMessage(MessageDTO messageDTO) {
        RespondBody respondBody;
        try {
            //计算分页查询的条件
            long recordNum = messageMapper.countMessageByUserId(messageDTO.getUserId());
            double d = (double) recordNum / (double) messageDTO.getPageSize();
            long pageNum = (long) Math.ceil(d);
            int offset = (messageDTO.getCurrentPage() - 1) * messageDTO.getPageSize();
            //查询记录
            List<Map> resultList = messageMapper.queryUserMessage(messageDTO.getUserId(), offset, messageDTO.getPageSize());
            //根据查询出来的消息，获取显示的用户Id列表
            Set<String> newIdSet = new HashSet<>();
            for (Map map : resultList) {
                newIdSet.add(String.valueOf(map.get("sendUserId")));
            }
            if(newIdSet.size()!=0) {
                //通过Feign调用user-service,获取用户基本信息，并重构返回信息
                List<String> newIdList = new ArrayList<>(newIdSet);
                UserIdListDTO newUserIdListDTO = new UserIdListDTO();
                newUserIdListDTO.setIdList(newIdList);
                RespondBody newUserInfoBody = outUserInfoFeign.getUserBasicInfoForMoment(newUserIdListDTO);
                if (newUserInfoBody.getStatus().equals("0"))
                    return RespondBuilder.buildErrorResponse("获取用户信息失败");
                //消息信息与用户信息合并
                Map userMap = (Map) newUserInfoBody.getData();
                for (Map map : resultList) {
                    //组合用户基本信息
                    int userId = (int) map.get("sendUserId");
                    Map basicMap = (Map) userMap.get(USER_BASIC_INFO + userId);
                    if (basicMap.get("nickname") != null)
                        map.put("nickname", basicMap.get("nickname"));
                    if (basicMap.get("profilePictureUrl") != null)
                        map.put("profilePictureUrl", basicMap.get("profilePictureUrl"));
                }
            }
            Map resultMap = new HashMap();
            resultMap.put("messageList", resultList);
            resultMap.put("pageNum", pageNum);
            resultMap.put("pageSize", messageDTO.getPageSize());
            resultMap.put("currentPage", messageDTO.getCurrentPage());
            resultMap.put("recordNum", recordNum);
            respondBody = RespondBuilder.buildNormalResponse(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            respondBody = RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }

    @Override
    public RespondBody readMessage(long messageId) {
        RespondBody respondBody;
        try {
            if (messageId==0)
                return RespondBuilder.buildErrorResponse("messageId不能为0或空");
            messageMapper.updateRead(messageId);
            respondBody=RespondBuilder.buildNormalResponse("设置已阅成功");
        }catch (Exception e){
            e.printStackTrace();
            respondBody=RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }

    @Override
    public RespondBody countNewMessages(long userId) {
        RespondBody respondBody;
        try{
            if(userId==0)
                return RespondBuilder.buildErrorResponse("messageId不能为0或空");
            respondBody=RespondBuilder.buildNormalResponse(messageMapper.countNewMessages(userId));
        }catch (Exception e){
            e.printStackTrace();
            respondBody=RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }

    @Override
    public RespondBody getMessageNum(long userId) {
        RespondBody respondBody;
        try {
            if(userId==0)
                return RespondBuilder.buildErrorResponse("messageId不能为0或空");
            Map resultMap=new HashMap();
            int letterNum=messageMapper.countLetter(userId);
            int friendRequestNum=messageMapper.countFriendRequest(userId);
            resultMap.put("letterNum",letterNum);
            resultMap.put("friendRequestNum",friendRequestNum);
            respondBody=RespondBuilder.buildNormalResponse(resultMap);
            logger.info("MessageService: "+userId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            respondBody=RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }

    @Override
    public RespondBody getNewMessageFriendIdList(long userId) {
        RespondBody respondBody;
        try{
            LinkedHashSet idSet=new LinkedHashSet();
            HashMap contentMap=new HashMap();
            List<Map> list=messageMapper.getNewMessageFriendIdList(userId);
            for(Map map:list){
                idSet.add(map.get("send_user_id"));
                if(!contentMap.containsKey(map.get("send_user_id")))
                    contentMap.put(map.get("send_user_id"),map.get("content"));
            }
            HashMap result=new HashMap();
            result.put("ideSet",idSet);
            result.put("contentMap",contentMap);
            respondBody=RespondBuilder.buildNormalResponse(result);
        }catch (Exception e){
            e.printStackTrace();
            respondBody=RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }

    @Override
    public RespondBody getMessageRecords(long userId, long friendId, int pageSize, int currentPage) {
        RespondBody respondBody;
        try{
            //计算分页查询的条件
            long recordNum = messageMapper.countFriendMessageAmount(userId,friendId);
            double d = (double) recordNum / (double)pageSize;
            long pageNum = (long) Math.ceil(d);
            int offset = (currentPage - 1) * pageSize;
            //查询记录信息
            List<Map> resultList=messageMapper.queryMessageRecord(userId,friendId,offset,pageSize);
            //设置与该好友的全部消息为已阅
            messageMapper.updateMessageStatusForFriend(userId,friendId);
            HashMap map=new HashMap();
            map.put("recordNum",recordNum);
            map.put("pageNum",pageNum);
            map.put("pageSize",pageSize);
            map.put("currentPage",currentPage);
            map.put("resultList",resultList);
            respondBody=RespondBuilder.buildNormalResponse(map);
        }catch (Exception e){
            e.printStackTrace();
            respondBody=RespondBuilder.buildErrorResponse(e.getMessage());
        }
        return respondBody;
    }


}
