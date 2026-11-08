package iot.cloud.platform.lock.mapper;

import iot.cloud.platform.lock.entity.PwdEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PwdMapper {
    // 根据密码查询所有匹配的记录
    List<PwdEntity> getPwd(@Param("pwd") String pwd);
    /**
     * 获取数据库中的固定密码
     */
    String getFixedPwd();

    /**
     * 更新固定密码
     * @param newPwd 新密码
     * @return 受影响的行数
     */
    int updateFixedPwd(String newPwd);
    // 临时密码解锁后失效
    @Update("UPDATE `pwd` SET `status` = 'disabled' WHERE `id` = #{id}")
    int disablePwd(@Param("id") Long id);
}