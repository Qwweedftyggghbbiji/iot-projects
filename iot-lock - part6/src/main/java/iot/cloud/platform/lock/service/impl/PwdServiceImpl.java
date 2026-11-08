package iot.cloud.platform.lock.service.impl;

import iot.cloud.platform.lock.mapper.PwdMapper;
import iot.cloud.platform.lock.service.PwdService;
import iot.cloud.platform.lock.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
// 其他原有import不变

@Service
public class PwdServiceImpl implements PwdService {

    @Autowired
    private PwdMapper pwdMapper;

    // 保留你之前的其他方法不变

    /**
     * 校验密码格式：6-18位纯数字
     */
    private boolean isValidPwd(String pwd) {
        if (StringUtils.isBlank(pwd)) {
            return false;
        }
        String tpwd = StringUtils.trim(pwd);
        Pattern pattern = Pattern.compile("[0-9]{6,18}");
        return pattern.matcher(tpwd).matches();
    }

    @Override
    public ResMsg resetFixedPwd(String oPwd, String nPwd) {
        ResMsg resMsg = new ResMsg();


        // 1. 校验新密码格式
        if (!isValidPwd(nPwd)) {
            resMsg.setErrcode("4002");
            resMsg.setErrmsg("密码必须为数字，长度为6-18");
            return resMsg;
        }

        // 2. 校验原密码是否正确
        String dbPwd = pwdMapper.getFixedPwd(); // 这个方法你之前实验应该已经实现
        if (!StringUtils.equals(oPwd, dbPwd)) {
            resMsg.setErrcode("4001");
            resMsg.setErrmsg("更新密码失败，旧密码错误");
            return resMsg;
        }

        // 3. 更新数据库中的固定密码
        int rows = pwdMapper.updateFixedPwd(nPwd);
        if (rows > 0) {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("更新密码成功");
        } else {
            resMsg.setErrcode("4001");
            resMsg.setErrmsg("更新密码失败，数据库异常");
        }
        return resMsg;

    }
    @Override
    public ResMsg verifyPwd(String pwd) {
        ResMsg resMsg = new ResMsg();
        // 1. 校验密码不能为空
        if (StringUtils.isBlank(pwd)) {
            resMsg.setErrcode("1001");
            resMsg.setErrmsg("密码不能为空");
            return resMsg;
        }
        // 2. 从数据库获取当前固定密码
        String dbPwd = pwdMapper.getFixedPwd();
        // 3. 比较密码是否一致
        if (StringUtils.equals(pwd, dbPwd)) {
            resMsg.setErrcode("0");
            resMsg.setErrmsg("解锁成功");
        } else {
            resMsg.setErrcode("1002");
            resMsg.setErrmsg("密码错误");
        }
        return resMsg;
    }
}