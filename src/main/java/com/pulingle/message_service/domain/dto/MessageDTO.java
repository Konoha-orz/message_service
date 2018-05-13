package com.pulingle.message_service.domain.dto;

import java.io.Serializable;

/**
 * Created by @杨健 on 2018/4/18 15:41
 *
 * @Des:
 */

public class MessageDTO implements Serializable {
    /**
     * 用户ID
     */
    private long userId;

    /**
     * 好友Id
     */
    private long friendId;

    /**
     * 当前页面数
     */
    private int currentPage;

    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String authCode;




    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
