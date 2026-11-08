package stu225.entity;

import java.util.Date;

/**
 * 类 PwdEntity 用于装载密码信息
 */
public class PwdEntity {

    // 行编号，数字自增
    private String id;

    // 密码类型，固定-fixed，临时-temp
    private String typ;

    // 密码，长度不超过18位
    private String pwd;

    // 状态，启用-enabled，禁用-disabled
    private String status;

    // 更新时间
    private Date uptTime;

    // 过期时间
    private Date expiredTime;

    // --- Getter 和 Setter 方法 ---

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