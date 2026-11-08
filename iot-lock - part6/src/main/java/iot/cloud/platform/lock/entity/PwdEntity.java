package iot.cloud.platform.lock.entity;

import java.util.Date;

public class PwdEntity {
    private Long id;
    private String typ;       // 密码类型：fixed-固定，temp-临时
    private String pwd;       // 密码内容
    private String status;    // 状态：enabled-启用，disabled-禁用
    private Date uptTime;     // 更新时间
    private Date expiredTime; // 过期时间（仅临时密码有效）

    // 所有属性的Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUptTime() {
        return uptTime;
    }

    public void setUptTime(Date uptTime) {
        this.uptTime = uptTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }
}