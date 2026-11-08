package stu225.service;

public interface PwdService {

    /**
     * 如果密码正确，则返回true，否则返回false
     * @param password 密码
     * @return
     */
    boolean isValidPassword(String password);
}