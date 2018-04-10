package com.pulingle.message_service.mapper;

import com.pulingle.message_service.domain.entity.Message;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("insert into message(type,read_status,send_user_id,rece_user_id,send_time,delete_status,content) values(#{type},#{read_status},#{send_user_id},#{rece_user_id},#{send_time},#{delete_status},#{content})")
    void addFriendRequest(Message message);
}