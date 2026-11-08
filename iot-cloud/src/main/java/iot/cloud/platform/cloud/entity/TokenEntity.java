package iot.cloud.platform.cloud.entity;

import java.util.Date;

public class TokenEntity {
    // 1. 补充缺失的核心属性：token 字符串
    private String userId;

    /**
     * 访问令牌字符串 (对应 impl 中的 token.setToken)
     */
    private String token;

    /**
     * 过期时间戳 (毫秒级)，用于业务逻辑判断是否过期
     */
    private Long expiredTs;

    /**
     * 过期时间 (Date类型)，通常用于存入数据库或日志展示
     */
    private Date expiredTime;

    // --- Getter and Setter Methods ---

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiredTs() {
        return expiredTs;
    }

    public void setExpiredTs(Long expiredTs) {
        this.expiredTs = expiredTs;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    // --- Business Logic Method ---

    /**
     * 判断 Token 是否已过期
     * 逻辑：当前时间 > 过期时间戳，即为过期
     */
    public boolean expired() {
        long now = new Date().getTime();
        // 如果过期时间戳小于当前时间，说明已经过期了
        return expiredTs < now;
    }
}
