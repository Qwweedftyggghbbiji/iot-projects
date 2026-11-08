package iot.cloud.platform.lock.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import iot.cloud.platform.lock.utils.DateUtil;
import iot.cloud.platform.lock.utils.ExcptUtil;
import iot.cloud.platform.lock.vo.MqttMsg;
import iot.cloud.platform.lock.vo.ResMsg;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import iot.cloud.platform.lock.service.PwdService;


import java.util.HashMap;
import java.util.Map;

/**
 * mqtt回调处理类
 */

@Component
@Slf4j
public class MqttConsumerCallback implements MqttCallbackExtended {

    @Autowired
    private MqttClient client;
    @Autowired
    private MqttConnectOptions options;
    @Autowired
    @Qualifier("mqttSubTopics")
    private Map<String,Integer> mqttSubTopics;

    @Autowired
    @Qualifier("mqttTopicReplyMap")
    private Map<String,String> mqttTopicReplyMap;

    @Autowired
    private PwdService pwdService;
    /**
     * 断开重连
     */

    private static final String DB_OLD_PWD = "123456";
    @Override
    public void connectionLost(Throwable cause) {
        log.info("MQTT连接断开",cause);
        try {
            if (null != client && !client.isConnected()) {
                client.reconnect();
                log.info("尝试重新连接");
            } else {
                client.connect(options);
                log.info("尝试建立新连接");
            }
        } catch (Exception e) {
            log.error(ExcptUtil.filterStack(e));
        }
    }

    /**
     * 消息处理
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String msg = new String(message.getPayload());
            log.info("收到主题[" + topic + "]消息 ->" + msg);
            ObjectMapper objMapper=new ObjectMapper();
            MqttMsg<HashMap> resvMsg=objMapper.readValue(msg, MqttMsg.class);
            ResMsg returnVal=null;
            // 1. 提取新密码
            String newPwd = "";
            if (resvMsg.getData() != null && resvMsg.getData().containsKey("nPwd")) {
                newPwd = String.valueOf(resvMsg.getData().get("nPwd"));
            }

            boolean isNewPwdValid = pwdService.isValidPwd(newPwd);


// 2. 提取旧密码 (需要先提取出来，后面要用)
            String oldPwd = "";
            if (resvMsg.getData() != null && resvMsg.getData().containsKey("oPwd")) {
                oldPwd = String.valueOf(resvMsg.getData().get("oPwd"));
            }


            if (isNewPwdValid) {
                // 4. 步骤 B: 校验旧密码 (模拟与数据库固定密码比对)
                if (DB_OLD_PWD.equals(oldPwd)) {
                    // 5. 步骤 C: 旧密码正确，执行更新操作
                    // 此时调用 resetFixedPwd，传入两个参数：旧密码和新密码
                    // 假设该方法返回 ResMsg 或者 void，这里我们手动构造成功结果
                    pwdService.resetFixedPwd(oldPwd, newPwd);

                    returnVal = new ResMsg();
                    returnVal.setErrcode("0");
                    returnVal.setErrmsg("更新密码成功");
                    log.info("密码更新成功");
                } else {
                    // 6. 步骤 D: 旧密码错误
                    returnVal = new ResMsg();
                    returnVal.setErrcode("4001");
                    returnVal.setErrmsg("更新密码失败，旧密码错误");
                    log.warn("旧密码错误，更新失败");
                }
            } else {
                // 7. 新密码格式不正确
                returnVal = new ResMsg();
                returnVal.setErrcode("4002");
                returnVal.setErrmsg("新密码格式不符合要求");
            }


            MqttMsg<ResMsg> replyMsg=new MqttMsg();
            if (resvMsg != null) {
                replyMsg.setEventId(resvMsg.getEventId());
                replyMsg.setEventName(resvMsg.getEventName());
                replyMsg.setEventTime(resvMsg.getEventTime());
            }
            replyMsg.setData(returnVal);
            String receiveReplyMsg="{}";
            try {
                //把replyMsg对象转换为 JSON 消息
                receiveReplyMsg=objMapper.writeValueAsString(replyMsg);
                log.info("回复MQTT消息："+receiveReplyMsg);
                String replyTopic=mqttTopicReplyMap.get(topic);
                if(replyTopic!=null){
                    client.publish(replyTopic,receiveReplyMsg.getBytes(StandardCharsets.UTF_8),1,true);
                }
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(),e);
            }
        } catch (Exception e) {
            log.error(ExcptUtil.filterStack(e));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    @Override
    public void connectComplete(boolean b, String s) {
        mqttSubTopics.forEach((k,v) ->{
            try {
                client.subscribe(k,v);
                log.info("订阅主题:"+k);
            } catch (MqttException e) {
                log.error(ExcptUtil.filterStack(e));
            }
        });
    }
}
