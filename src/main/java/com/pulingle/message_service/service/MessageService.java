package com.pulingle.message_service.service;

import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.entity.Message;

/**
 * Created by @杨健 on 2018/4/1 17:38
 *
 * @Des: 消息处理服务
 */
public interface MessageService {
    RespondBody sendMessage(Message message);

}
