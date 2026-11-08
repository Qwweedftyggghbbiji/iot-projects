package stu202.mockdb;

import stu202.entity.PwdEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockDB {
    public static List<PwdEntity> PASSWORD_LIST=new ArrayList<>();
    static{
        // 有效固定密码
        PwdEntity pe1=new PwdEntity();
        pe1.setPwd("123456");
        pe1.setTyp("fixed");
        pe1.setExpiredTime(new Date(new Date().getTime()+1000*60*30));
        pe1.setStatus("enabled");

        // 禁用临时密码
        PwdEntity pe2=new PwdEntity();
        pe2.setPwd("000000");
        pe2.setTyp("temp");
        pe2.setExpiredTime(new Date(new Date().getTime()+1000*60*30));
        pe2.setStatus("disabled");

        // 过期临时密码
        PwdEntity pe3=new PwdEntity();
        pe3.setPwd("666666");
        pe3.setTyp("temp");
        pe3.setExpiredTime(new Date(new Date().getTime()-1000*60*30));
        pe3.setStatus("enabled");

        PASSWORD_LIST.add(pe1);
        PASSWORD_LIST.add(pe2);
        PASSWORD_LIST.add(pe3);
    }
}