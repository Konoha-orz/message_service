package com.pulingle.message_service.feign;

import com.pulingle.message_service.domain.dto.RespondBody;
import com.pulingle.message_service.domain.dto.UserIdListDTO;
import com.pulingle.message_service.domain.entity.UserBasicInfo;
import com.pulingle.message_service.domain.entity.User_info;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by @杨健 on 2018/4/7 18:07
 *
 * @Des: user-service中的提供用户基础信息的接口
 */
@Component("outUserInfoFeign")
@FeignClient(name = "USER-SERVICE")
@RequestMapping(value = "outUserInfo")
public interface OutUserInfoFeign {

    /**
     * @param: idList(用户Id列表)
     * @return: RespondBody
     * @Des: 根据用户Id列表查询基本信息，用于动态服务
     */
    @RequestMapping(value = "/getUserBasicInfoForMoment",method = RequestMethod.POST)
    public RespondBody getUserBasicInfoForMoment(@RequestBody UserIdListDTO userIdListDTO);

    /**
     * @param: userId,friendList
     * @return: RespondBody
     * @Des: 判断是否为好友
     */
    @RequestMapping(value = "/isFriend",method = RequestMethod.POST)
    public RespondBody isFriend(@RequestBody User_info user_info);

    /**
     * @param: userId
     * @return: Map
     * @Des: 根据用户ID查询用户信息
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public RespondBody getUserInfo(@RequestBody UserBasicInfo userBasicInfo);


}
