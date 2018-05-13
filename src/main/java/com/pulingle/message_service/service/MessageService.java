package com.pulingle.message_service.service;

import com.pulingle.message_service.domain.dto.MessageDTO;
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

    /**
    * @param: messageDTO(userId,currentPage,pageSize)
    * @return: RespondBody
    * @Des: 分页查询用户收到的私信
    */
    RespondBody queryMessage(MessageDTO messageDTO);

    /**
    * @param: messageId
    * @return: RespondBody
    * @Des: 设置消息已读状态
    */
    RespondBody readMessage(long sendUserId,long receUserId);

    /**
    * @param: userId
    * @return: RespondBody
    * @Des: 查询用户新的消息数
    */
    RespondBody countNewMessages(long userId);

    /**
    * @param: userId
    * @return: RespondBody
    * @Des: 查询各类新消息数
    */
    RespondBody getMessageNum(long userId);

    /**
    * @param: userId
    * @return: RespondBody
    * @Des: 获取用户好友发送最新消息的内容以及userId
    */
    RespondBody getNewMessageFriendIdList(long userId);

    /**
    * @param: userId,friendId,pageSize,currentPage
    * @return: RespondBody
    * @Des: 查看好友发送给你的私信消息
    */
    RespondBody getMessageRecords(long userId,long friendId,int pageSize,int currentPage);

    /**
     * @param: userId,friendId
     * @return: RespondBody
     * @Des: 删除好友之间的消息记录
     */
    RespondBody deleteFriendMessage(long userId ,long friendId);
}
