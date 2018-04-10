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

    /**
     * @param sendUserId 发送请求id
     * @param receUserId 接受请求id
     * @return 返回体
     */
    RespondBody sendFriendRequest(long sendUserId,long receUserId);
}
