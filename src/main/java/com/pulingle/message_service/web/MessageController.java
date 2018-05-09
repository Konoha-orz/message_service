package com.pulingle.message_service.web;

import com.pulingle.message_service.domain.dto.MessageDTO;
import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.dto.UserIdListDTO;
import com.pulingle.message_service.domain.entity.Message;
import com.pulingle.message_service.domain.entity.UserBasicInfo;
import com.pulingle.message_service.service.MessageService;
import com.pulingle.message_service.utils.IpUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
     */
    @RequestMapping("/sendFriendRequest")
    public  RespondBody  sendFriendRequest(long sendUserId,long receUserId){
        return messageService.sendFriendRequest(sendUserId,receUserId);
    }

    /**
     * @param userId 用户id
     * @return 返回体
     * 根据用户id查询所有的好友请求
     */
    @RequestMapping("/getFriendRequest")
    public RespondBody  getFriendRequest(@RequestBody  UserBasicInfo userBasicInfo){
        return messageService.getFriendRequest(userBasicInfo.getUserId());
    }

    /**
     * @param
     * @return 返回体
     * 删除对应id 的好友请求消息
     */
    @RequestMapping("/deleteFriendRequest")
    public  RespondBody deleteFriendRequest(@RequestBody Message message){
        return messageService.deleteFriendRequest(message.getMessageId());
    }

    /**
     * @param: messageDTO(userId,currentPage,pageSize)
     * @return: RespondBody
     * @Des: 分页查询用户收到的私信
     */
    @RequestMapping(value = "/queryMessage",method = RequestMethod.POST)
    public RespondBody queryMessage(@RequestBody MessageDTO messageDTO){
        return messageService.queryMessage(messageDTO);
    }

    /**
     * @param: messageId
     * @return: RespondBody
     * @Des: 设置消息已读状态
     */
    @RequestMapping(value = "/readMessage",method = RequestMethod.POST)
    public RespondBody readMessage(@RequestBody Message message){
        return messageService.readMessage(message.getMessageId());
    }

    @RequestMapping(value = "/countNewMessages",method = RequestMethod.POST)
    public RespondBody countNewMessage(@RequestBody UserIdListDTO userIdListDTO){
        return messageService.countNewMessages(userIdListDTO.getUserId());
    }

    @RequestMapping(value = "/getMessageNum",method = RequestMethod.POST)
    public RespondBody getMessageNum(@RequestBody UserIdListDTO userIdListDTO){
        return messageService.getMessageNum(userIdListDTO.getUserId());
    }

    /**
     * @param: userId
     * @return: RespondBody
     * @Des: 获取用户好友发送最新消息的内容以及userId
     */
    @RequestMapping(value = "/getNewMessageFriendIdList",method = RequestMethod.POST)
    public RespondBody getNewMessageFriendIdList(@RequestBody UserBasicInfo userBasicInfo){
        return messageService.getNewMessageFriendIdList(userBasicInfo.getUserId());
    }

    /**
     * @param: userId,friendId,pageSize,currentPage
     * @return: RespondBody
     * @Des: 查看好友发送给你的私信消息
     */
    @RequestMapping(value = "/getMessageRecords",method = RequestMethod.POST)
    public RespondBody getMessageRecords(@RequestBody MessageDTO messageDTO){
        return messageService.getMessageRecords(messageDTO.getUserId(),messageDTO.getFriendId(),messageDTO.getPageSize(),messageDTO.getCurrentPage());
    }

}
