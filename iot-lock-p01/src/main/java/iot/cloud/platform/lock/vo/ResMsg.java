package iot.cloud.platform.lock.vo;

public class ResMsg {
    private String errcode="0";
    private String errmsg;
    private Object data;

    public ResMsg(){

    }

    public ResMsg(String errcode, String errmsg){
        this.errcode=errcode;
        this.errmsg=errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object result) {
        this.data = data;
    }
}
