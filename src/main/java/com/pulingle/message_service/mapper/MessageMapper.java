package com.pulingle.message_service.mapper;

import com.pulingle.message_service.domain.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
    int send(Message message);
    List<Map> findMessage();

    @Insert("insert into message(type,read_status,send_user_id,rece_user_id,send_time,delete_status,content) values (#{type},#{readStatus},#{sendUserId},#{receUserId},#{sendTime},#{deleteStatus},#{content})")
    void addFriendRequest(Message message);

    @Select("select message_id as messageId,send_user_id as sendUserId,send_time as sendTime from message where rece_user_id=#{userId} and type=2")
    List<Message> getFriendRequest(long userId);

    @Delete("delete from message where message_id = #{messageId}")
    void deleteFriendRequest(long messageId);
}
