package iot.cloud.platform.lock.mapper;

import stu225.entity.ConfigEntity;

public interface ConfigMapper {
    ConfigEntity getConfigByK(String k);
}
