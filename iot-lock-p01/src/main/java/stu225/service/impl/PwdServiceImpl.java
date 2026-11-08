package stu225.service.impl;

import stu225.mockdb.MockDB;
import stu225.service.PwdService;
import stu225.entity.PwdEntity; // 假设 PwdEntity 在 stu999 包下

import java.util.Date;
import java.util.List;

public class PwdServiceImpl implements PwdService {

    /**
     * 如果密码正确，则返回true，否则返回false
     *
     * @param password 密码
     * @return
     */
    @Override
    public boolean isValidPassword(String password) {
        List<PwdEntity> pwdList = MockDB.PASSWORD_LIST;

        for (PwdEntity pe : pwdList) {
            // 1. 检查密码状态是否为"启用"
            if (!"enabled".equals(pe.getStatus())) {
                continue; // 禁用的密码，跳过
            }

            // 2. 检查是否过期（如果有过期时间）
            Date expiredTime = pe.getExpiredTime();
            if (expiredTime != null) {
                // 临时密码：检查是否未过期
                if (expiredTime.before(new Date())) {
                    continue; // 已过期，跳过
                }
            }

            // 3. 检查密码是否匹配
            if (password != null && password.equals(pe.getPwd())) {
                return true; // 找到匹配的有效密码
            }
        }

        return false; // 未找到有效密码
    }
}