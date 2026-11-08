package iot.cloud.platform.lock.mapper;

import stu225.entity.PwdEntity;

import java.util.List;

/**
 * 密码验证
 */
public interface PwdMapper {

    /**
     * 根据密码查询所有数据库匹配密码
     * @param pwd
     * @return
     */
    List<PwdEntity> getPwd(String pwd);
}

