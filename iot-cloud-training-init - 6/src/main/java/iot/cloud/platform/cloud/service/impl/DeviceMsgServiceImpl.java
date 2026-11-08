package iot.cloud.platform.cloud.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iot.cloud.platform.cloud.entity.DeviceMsgEntity;
import iot.cloud.platform.cloud.mapper.DeviceMsgMapper;
import iot.cloud.platform.cloud.service.DeviceMsgService;
import iot.cloud.platform.cloud.vo.DeviceMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeviceMsgServiceImpl implements DeviceMsgService {
  @Autowired
  private DeviceMsgMapper deviceMsgMapper;
  @Override
  public List<DeviceMsgEntity> getByTag(String iotId, String tag) {
    return deviceMsgMapper.getByTag(iotId,tag);
  }

  @Override
  public boolean save(DeviceMsgVo vo) {
    DeviceMsgEntity msg=new DeviceMsgEntity();
    try {
      msg.setMsg(new ObjectMapper().writeValueAsString(vo.getMsg()));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    msg.setIotId(vo.getIotId());
    msg.setCreateTime(new Date());
    msg.setTag(vo.getTag());
    return deviceMsgMapper.save(msg);
  }
}
