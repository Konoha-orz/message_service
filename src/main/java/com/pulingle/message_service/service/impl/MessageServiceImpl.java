package com.pulingle.message_service.service.impl;

import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.entity.Message;
import com.pulingle.message_service.mapper.MessageMapper;
import com.pulingle.message_service.service.MessageService;
import com.pulingle.message_service.utils.RespondBuilder;
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

    @Resource
    MessageMapper messageMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
    * @param: sendUser,receUser,content,type
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
            respondBody=RespondBuilder.buildNormalResponse("发送成功");
        }catch (Exception e){
            e.printStackTrace();
            respondBody= RespondBuilder.buildErrorResponse(e.getMessage());
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
            Message message = new Message();
            message.setSendUserId(sendUserId);
            message.setReceUserId(receUserId);
            message.setType(2);
            message.setReadStatus(0);
            message.setSendTime(new Date());
            message.setDeleteStatus(0);
            message.setContent("好友请求消息");
            messageMapper.addFriendRequest(message);
            respondBody = RespondBuilder.buildNormalResponse("请求成功！");
        }catch (Exception e){
            respondBody = RespondBuilder.buildErrorResponse("请求失败！");
            e.printStackTrace();
        }
        return respondBody;
    }

    /**
     * @param userId 用户id
     * @return 返回体
     * 通过userid 查询所有的好友请求
     */
    @Override
    public RespondBody getFriendRequest(long userId) {
        RespondBody respondBody;
        try {
            List<Message> list = messageMapper.getMessageByUserId(userId);
            List<Map> returnlist = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                long sendUserId = list.get(i).getSendUserId();
                long messageId = list.get(i).getMessageId();
                Date sendTime = list.get(i).getSendTime();
                map.put("sendUserId", sendUserId);
                map.put("messageId", messageId);
                map.put("sendTime", sendTime);
                returnlist.add(map);
            }
            respondBody = RespondBuilder.buildNormalResponse(returnlist);
        }catch (Exception e){
            respondBody = RespondBuilder.buildErrorResponse("请求失败！");
        }
        return respondBody;
    }


}
