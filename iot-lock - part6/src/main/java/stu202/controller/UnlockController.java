package stu202.controller;

import stu202.service.PwdService;
import stu202.service.impl.PwdServiceImpl;
import java.util.regex.Pattern;

public class UnlockController {
    private PwdService pwdServiceImpl=new PwdServiceImpl();

    public String unlock(String pwd){
        if(pwd==null) return "请输入有效密码";
        Pattern pattern = Pattern.compile("[0-9]*");
        if(pattern.matcher(pwd).matches()) {
            if(pwd.length()<6||pwd.length()>18) return "密码要求6-18位";
            return pwdServiceImpl.isValidPassword(pwd) ? "密码正确" : "密码错误";
        }else{
            return "密码只能是数字";
        }
    }
}