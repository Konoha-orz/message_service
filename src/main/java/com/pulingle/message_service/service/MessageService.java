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

    /**
     * @param userId 用户id
     * @return 返回体
     * 根据用户id查询所有的好友请求
     */
    RespondBody getFriendRequest(long userId);

    /**
     * @param messageId 消息id
     * @return 返回体
     * 删除对应id 的好友请求消息
     */
    RespondBody deleteFriendRequest(long messageId);
}
