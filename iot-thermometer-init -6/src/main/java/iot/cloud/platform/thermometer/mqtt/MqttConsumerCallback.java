package iot.cloud.platform.thermometer.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import iot.cloud.platform.thermometer.controller.EmojiController;
import iot.cloud.platform.thermometer.entity.EmojiEntity;
import iot.cloud.platform.thermometer.vo.MqttMsg;
import iot.cloud.platform.thermometer.vo.MqttReceiveMsg;
import iot.cloud.platform.thermometer.vo.ResMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * MQTT消息回调处理类
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
    private EmojiController emojiController;

    @Autowired
    private MqttProvider mqttProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 断开重连
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.info("MQTT连接断开",cause);
        int tryTimes=3,count=0;
        while(!client.isConnected()) {
            try {
                client.reconnect();
                count=0;
            } catch (Exception e) {
                count++;
                log.error("重连异常：{}", e.getMessage());
            }
            log.info("尝试重新连接"+count+"次");
            if(count>=tryTimes){
                break;
            }
        }
    }

    /**
     * 消息到达处理
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // 1. 解析收到的消息
        String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
        log.info("收到MQTT消息，主题：" + topic + "，内容：" + payload);

        try {
            // 2. 解析JSON消息
            MqttReceiveMsg<HashMap> msgObj = objectMapper.readValue(payload, MqttReceiveMsg.class);
            String eventId = msgObj.getEventId();
            String eventName = msgObj.getEventName();
            Long eventTime = msgObj.getEventTime();
            HashMap<String, Object> data = msgObj.getData();

            // 3. 根据事件名处理业务逻辑
            ResMsg resMsg = new ResMsg();

            if ("updateEmojiFace".equals(eventName) && data != null) {
                // 修改表情
                String name = (String) data.get("name");
                String face = (String) data.get("face");

                log.info("处理表情更新请求：name={}, face={}", name, face);

                // 调用EmojiController的方法更新表情
                EmojiEntity emoji = new EmojiEntity();
                emoji.setName(name);
                emoji.setFace(face);
                resMsg = emojiController.updateEmojiFace(emoji);

                log.info("表情更新结果：errcode={}, errmsg={}", resMsg.getErrcode(), resMsg.getErrmsg());
            } else {
                // 未知事件类型
                resMsg.setErrcode("4001");
                resMsg.setErrmsg("未知的事件类型或数据为空");
                resMsg.setData(null);
                log.warn("未知事件类型：{}", eventName);
            }

            // 4. 构造回复消息
            MqttMsg<ResMsg> replyMsg = new MqttMsg<>();
            replyMsg.setEventId(eventId);
            replyMsg.setEventName(eventName);
            replyMsg.setEventTime(eventTime);
            replyMsg.setResMsg(resMsg);

            // 5. 发送回复消息
            String replyPayload = objectMapper.writeValueAsString(replyMsg);
            String replyTopic = mqttTopicReplyMap.get(topic);

            if (StringUtils.isNotBlank(replyTopic)) {
                mqttProvider.publish(replyTopic, replyPayload);
                log.info("发送回复消息，主题：" + replyTopic + "，内容：" + replyPayload);
            } else {
                log.warn("未找到回复主题，原始主题：" + topic);
            }

        } catch (Exception e) {
            log.error("消息处理异常：{}", e.getMessage(), e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // 消息发送完成回调
    }

    @Override
    public void connectComplete(boolean b, String s) {
        mqttSubTopics.forEach((k,v) ->{
            try {
                client.subscribe(k,v);
                log.info("订阅主题:"+k);
            } catch (MqttException e) {
                log.error("订阅主题失败：{}", e.getMessage());
            }
        });
    }
}