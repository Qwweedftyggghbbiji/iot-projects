package iot.cloud.platform.lock.vo;

public class ResultVO {
    // 状态码：0=成功，1=失败
    private int code;
    // 提示信息
    private String msg;

    // Getter和Setter方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}