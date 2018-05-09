package com.pulingle.message_service.mapper;

import com.pulingle.message_service.domain.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by @杨健 on 2018/4/1 17:43
 *
 * @Des: 消息Mapper
 */
@Component("messageMapper")
public interface MessageMapper {
    /**
    * @param: message
    * @return: int
    * @Des: 发送消息，插入一条记录
    */
    int send(Message message);

    /**
    * @param:
    * @return:
    * @Des: 查询某个用户的所有好友请求
    */
    List<Map> getFriendRequest(@Param("userId") long userId);

    @Delete("delete from message where message_id = #{messageId}")
    void deleteFriendRequest(long messageId);

    /**
    * @param: userId，offset,size
    * @return: List<Message>
    * @Des: 分页查询用户收到的消息
    */
    List<Map> queryUserMessage(@Param("userId") long userId, @Param("offset")int offset, @Param("size")int size);

    /**
    * @param: userId
    * @return: long
    * @Des: 统计用户收到的消息记录数
    */
    long countMessageByUserId(@Param("userId")long userId);

    /**
    * @param: messageId
    * @return: int
    * @Des: 根据messageId 更新 为已读状态
    */
    int updateRead(@Param("messageId")long messageId);

    /**
    * @param: userId
    * @return: int
    * @Des: 查询新的消息数
    */
    int countNewMessages(@Param("userId")long userId);

    /**
    * @param: userId
    * @return: int
    * @Des: 查询新的私信数
    */
    int countLetter(@Param("userId")long userId);

    /**
     * @param: userId
     * @return: int
     * @Des: 查询新的好友请求数
     */
    int countFriendRequest(@Param("userId")long userId);

    /**
    * @param:
    * @return:
    * @Des: 新增发送好友请求消息
    */
    int addFriendRequest(Message message);

    /**
    * @param: sendUserId,receUserId
    * @return: int
    * @Des: 查询是否已经发送好友请求
    */
    int haveSendFriendRequest(Message message);

    /**
    * @param: userId
    * @return: List<Long>
    * @Des: 查询用户收到好友发送新消息的Id列表
    */
    List<Map> getNewMessageFriendIdList(@Param("userId")long userId);

    /**
    * @param: userId,friendId
    * @return: long
    * @Des: 获取用户与某用户的私信消息记录数
    */
    long countFriendMessageAmount(@Param("userId")long userId,@Param("friendId")long friendId);

    /**
    * @param: userId,friendId,offset,size
    * @return: List<Map>
    * @Des: 分页查询用户与某用户的私信消息记录
    */
    List<Map> queryMessageRecord(@Param("userId")long userId,@Param("friendId")long friendId,@Param("offset")int offset,@Param("size")int size);

    /**
    * @param: userId,friendId
    * @return: int
    * @Des: 设置某用户与某用户的私信消息为已读
    */
    int updateMessageStatusForFriend(@Param("userId")long userId,@Param("friendId")long friendId);
}
