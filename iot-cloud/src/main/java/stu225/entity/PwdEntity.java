package stu225.entity;

import java.util.Date;

public class PwdEntity {

    private String id;
    private String typ;
    private String pwd;
    private String status;
    private Date uptTime;
    private Date expiredTime;

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (pwd != null && pwd.length() > 18) {
            throw new IllegalArgumentException("密码长度不能超过18位");
        }
        this.pwd = pwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        // 可选：校验状态值是否合法（enabled/disabled）
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