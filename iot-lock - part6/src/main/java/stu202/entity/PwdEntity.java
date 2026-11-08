package stu202.entity;

import java.util.Date;

public class PwdEntity {
    private String id;
    private String typ;
    private String pwd;
    private String status;
    private Date uptTime;
    private Date expiredTime;

    // 所有属性的Getter和Setter方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTyp() { return typ; }
    public void setTyp(String typ) { this.typ = typ; }
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getUptTime() { return uptTime; }
    public void setUptTime(Date uptTime) { this.uptTime = uptTime; }
    public Date getExpiredTime() { return expiredTime; }
    public void setExpiredTime(Date expiredTime) { this.expiredTime = expiredTime; }
}