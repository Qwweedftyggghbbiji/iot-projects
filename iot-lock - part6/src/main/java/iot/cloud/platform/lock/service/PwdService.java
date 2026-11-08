package iot.cloud.platform.lock.service;

import iot.cloud.platform.lock.vo.ResMsg;

public interface PwdService {
    /**
     * 更新固定密码
     * @param oPwd 原密码
     * @param nPwd 新密码
     * @return 操作结果
     */
    ResMsg resetFixedPwd(String oPwd, String nPwd);

    /**
     * 验证固定密码是否正确（解锁功能使用）
     * @param pwd 待验证密码
     * @return 验证结果
     */
    ResMsg verifyPwd(String pwd); // 改回返回ResMsg
}