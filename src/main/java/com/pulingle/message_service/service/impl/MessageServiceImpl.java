package com.pulingle.message_service.service.impl;

import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.entity.Message;
import com.pulingle.message_service.mapper.MessageMapper;
import com.pulingle.message_service.service.MessageService;
import com.pulingle.message_service.utils.RespondBuilder;
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

}
