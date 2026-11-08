package iot.cloud.platform.cloud.service;

import iot.cloud.platform.cloud.entity.DeviceMsgEntity;
import iot.cloud.platform.cloud.vo.DeviceMsgVo;

import java.util.List;

public interface DeviceMsgService {
  /**
   * 获取设备消息
   * @param iotId 物联网云平台 IoT ID
   * @param tag 消息类型
   * @return
   */
  List<DeviceMsgEntity> getByTag(String iotId, String tag);

  /**
   * 保存设备
   * @param vo 设备信息
   * @return
   */
  boolean save(DeviceMsgVo vo);
}
