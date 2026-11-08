package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.DeviceMsgEntity;

import java.util.List;

public interface DeviceMsgMapper {
  List<DeviceMsgEntity> getByTag(String iotId, String tag);

  boolean save(DeviceMsgEntity msg);
}
