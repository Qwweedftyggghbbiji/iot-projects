package iot.cloud.platform.cloud.service;

import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.vo.DeviceVo;

import java.util.List;

public interface DeviceService {
  List<DeviceEntity> getDeviceListByUserId(String userId);
}
