package com.pulingle.message_service.service.impl;

import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.entity.Message;
import com.pulingle.message_service.mapper.MessageMapper;
import com.pulingle.message_service.service.MessageService;
import com.pulingle.message_service.utils.JwtUtil;
import com.pulingle.message_service.utils.RespondBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public RespondBody sendFriendRequest(long sendUserId, long receUserId) {
        RespondBody respondBody = new RespondBody();
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
            respondBody.setStatus("1");
            respondBody.setMsg("0000");
        }catch (Exception e){
            respondBody.setStatus("0");
            respondBody.setMsg("插入失败");
        }
        return respondBody;
    }


}
