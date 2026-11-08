package iot.cloud.platform.thermometer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iot.cloud.platform.thermometer.vo.MqttMsg;
import org.junit.Test;

import java.util.Date;

public class ParseVoToJson {

  @Test
  public void parseMqttResvMsg(){
    MqttMsg resvMsg=new MqttMsg();
    resvMsg.setEventId("0759");
    resvMsg.setEventName("updateEmojiFace");
    resvMsg.setEventTime(new Date().getTime());
    ObjectMapper objMapper=new ObjectMapper();
    try {
      System.out.println(objMapper.writeValueAsString(resvMsg));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(System.currentTimeMillis());
  }
}
