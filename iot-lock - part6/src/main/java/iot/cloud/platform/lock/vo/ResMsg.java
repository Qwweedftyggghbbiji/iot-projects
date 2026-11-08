package iot.cloud.platform.lock.vo;

import lombok.Data;

@Data
public class ResMsg {
    private String errcode;
    private String errmsg;
    private String msg; // 新增这个字段，专门给前端页面用
    private Object data;

    // 重写setErrmsg方法，自动同步给msg字段
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
        this.msg = errmsg;
    }

    // 如果不用Lombok，手动添加所有getter和setter
    public String getErrcode() { return errcode; }
    public void setErrcode(String errcode) { this.errcode = errcode; }
    public String getErrmsg() { return errmsg; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}