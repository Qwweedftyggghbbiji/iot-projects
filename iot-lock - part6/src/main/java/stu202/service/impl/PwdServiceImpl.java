package stu202.service.impl;

import stu202.entity.PwdEntity;
import stu202.mockdb.MockDB;
import stu202.service.PwdService;
import java.util.Date;
import java.util.List;

public class PwdServiceImpl implements PwdService {
    @Override
    public boolean isValidPassword(String password) {
        boolean result = false;
        List<PwdEntity> pwdList = MockDB.PASSWORD_LIST;
        for (PwdEntity pe : pwdList) {
            // 1. 判断密码是否启用
            if ("enabled".equals(pe.getStatus())) {
                // 2. 判断密码是否匹配
                if (password.equals(pe.getPwd())) {
                    // 3. 判断是否过期
                    if (pe.getExpiredTime() != null) {
                        if (new Date().before(pe.getExpiredTime())) {
                            result = true;
                        }
                    } else {
                        result = true;
                    }
                }
            }
            if (result) return result;
        }
        return result;
    }
}