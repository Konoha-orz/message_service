package com.pulingle.message_service.domain.entity;

import java.io.Serializable;

/**
 * Created by @杨健 on 2018/4/12 15:41
 *
 * @Des: 用户基础信息传输类
 */

public class UserBasicInfo implements Serializable{
    /**
     * 用户Id
     */
    private long userId;

    /**
     *  用户昵称
     */
    private String nickname;

    /**
     * 用户头像URL
     */
    private String profilePictureUrl;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    @Override
    public String toString() {
        return "UserBasicInfoDTO{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
