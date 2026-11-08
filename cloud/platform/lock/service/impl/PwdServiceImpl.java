package iot.cloud.platform.lock.service.impl;

import iot.cloud.platform.lock.service.PwdService;
import stu225.entity.PwdEntity;
import stu225.mockdb.MockDB;
import iot.cloud.platform.lock.vo.ResMsg;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class PwdServiceImpl implements PwdService {

    /**
     * 原有的校验密码方法（保持不变）
     */
    @Override
    public boolean isValidPassword(String password) {
        List<PwdEntity> pwdList = MockDB.PASSWORD_LIST;
        for (PwdEntity pe : pwdList) {
            if (!"enabled".equals(pe.getStatus())) continue;
            Date expiredTime = pe.getExpiredTime();
            if (expiredTime != null && expiredTime.before(new Date())) continue;
            if (password != null && password.equals(pe.getPwd())) return true;
        }
        return false;
    }

    /**
     * 【新增】重置/修改固定密码方法
     * @param oldPwd 用户输入的旧密码
     * @param newPwd 用户输入的新密码
     * @return ResMsg 结果消息
     */
    @Override
    public ResMsg resetFixedPwd(String oldPwd, String newPwd) {
        ResMsg resMsg = new ResMsg();

        // 1. 校验新密码格式：必须是数字且长度为6-18位
        // 对应需求(1)
        if (!isValidPwd(newPwd)) {
            resMsg.setErrcode("4002");
            resMsg.setErrmsg("密码必须为数字，长度为6-18");
            return resMsg;
        }

        // 2. 校验旧密码是否正确
        // 这里复用 isValidPassword 方法去数据库(MockDB)里比对旧密码
        boolean isOldPwdValid = this.isValidPassword(oldPwd);

        if (!isOldPwdValid) {
            // 对应需求(3)：旧密码错误导致更新失败
            resMsg.setErrcode("4001");
            resMsg.setErrmsg("更新密码失败，旧密码错误");
            return resMsg;
        }

        // 3. 执行更新操作
        // 遍历列表找到那个 "enabled" 的密码实体，并更新它
        List<PwdEntity> pwdList = MockDB.PASSWORD_LIST;
        for (PwdEntity pe : pwdList) {
            // 假设我们要更新的是当前启用的那个固定密码
            if ("enabled".equals(pe.getStatus())) {
                pe.setPwd(newPwd);
                // 注意：如果是临时密码逻辑，可能还需要重置过期时间等，这里仅做简单替换
                break;
            }
        }

        // 对应需求(2)：更新成功
        resMsg.setErrcode("0");
        resMsg.setErrmsg("更新密码成功");
        return resMsg;
    }

    /**
     * 辅助方法：校验密码格式（题目提示的代码）
     */
    @Override
    public boolean isValidPwd(String pwd) {
        if (pwd == null) return false;
        String tpwd = org.apache.commons.lang3.StringUtils.trim(pwd); // 或者使用标准库的 trim
        Pattern pattern = Pattern.compile("[0-9]{6,18}");
        return pattern.matcher(tpwd).matches();
    }
}