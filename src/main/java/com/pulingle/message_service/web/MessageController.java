package com.pulingle.message_service.web;

import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.entity.Message;
import com.pulingle.message_service.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by @杨健 on 2018/4/1 18:00
 *
 * @Des:
 */
@RestController
@RequestMapping(value = "message")
public class MessageController {
    @Resource
    MessageService messageService;

    /**
    * @param: sendUserId,receUserId,content,type
    * @return: RespondBody
    * @Des: 发送消息接口
    */
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public RespondBody sendMessage(@RequestBody Message message){
        return messageService.sendMessage(message);
    }

    /**
     * @param sendUserId 发送请求id
     * @param receUserId 接受请求id
     * @return 返回体
     * 发送好友请求
     */
    @RequestMapping("/sendFriendRequest")
    public @ResponseBody RespondBody  sendFriendRequest(long sendUserId,long receUserId){
        return messageService.sendFriendRequest(sendUserId,receUserId);
    }

    /**
     * @param userId 用户id
     * @return 返回体
     * 根据用户id 查询所有的好友请求
     */
    @RequestMapping("/getFriendRequest")
    public @ResponseBody RespondBody getFriendRequest(long userId){
        return messageService.getFriendRequest(userId);
    }
}
