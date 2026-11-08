package iot.cloud.platform.lock.service;

import org.springframework.stereotype.Service;
import iot.cloud.platform.lock.vo.ResMsg;
@Service
public interface PwdService {

    /**
     * 如果密码正确，则返回true，否则返回false
     * @param password 密码
     * @return
     */
     boolean isValidPassword(String password);
     boolean isValidPwd(String pwd);
    /**
     * 更新固定密码
     * @param oPwd 原密码
     * @param nPwd 新密码
     * @return
     */
    ResMsg resetFixedPwd(String oPwd,String nPwd);



}