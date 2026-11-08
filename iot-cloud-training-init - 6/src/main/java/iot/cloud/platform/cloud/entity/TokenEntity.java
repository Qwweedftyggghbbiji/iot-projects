package iot.cloud.platform.cloud.entity;

import java.util.Date;

public class TokenEntity {
  private String userId;
  private String token;
  private Date expiredTime;
  private Long expiredTs;

  public boolean expired(){
    long now=new Date().getTime();
    return expiredTs<now;
  }

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

  public Date getExpiredTime() {
    return expiredTime;
  }

  public void setExpiredTime(Date expiredTime) {
    this.expiredTime = expiredTime;
  }

  public Long getExpiredTs() {
    return expiredTs;
  }

  public void setExpiredTs(Long expiredTs) {
    this.expiredTs = expiredTs;
  }

}
