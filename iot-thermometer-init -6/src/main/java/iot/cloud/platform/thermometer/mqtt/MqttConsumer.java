package iot.cloud.platform.thermometer.mqtt;

import iot.cloud.platform.thermometer.service.ConfigService;
import iot.cloud.platform.thermometer.utils.ExcptUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MqttConsumer implements ApplicationRunner {
    @Autowired
    private MqttClient client;

    @Autowired
    private MqttConnectOptions options;

    @Autowired
    private MqttConsumerCallback callback;

    @Autowired
    @Qualifier("mqttSubTopics")
    private Map<String,Integer> mqttSubTopics;

    private final Map<String,String> topicReplyMap=new HashMap<>();

    @Autowired
    private ConfigService configService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("初始化并启动mqtt......");
        init();
    }
 
    /**
     * 连接mqtt服务器
     */
    private void init() {
        try {
            client.setCallback(callback);
            client.connect(options);
        } catch (Exception e) {
            log.info("mqtt连接异常：" + ExcptUtil.filterStack(e));
        }
    }

    private String[] getSubTopics(){
        return mqttSubTopics.keySet().stream().toArray(String[]::new);
    }


 
    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
            mqttSubTopics.put(topic,qos);
            log.info("订阅主题:" + topic);
        } catch (MqttException e) {
            log.error(ExcptUtil.filterStack(e));
        }
    }
 
    /**
     * 发布，非持久化
     *
     *  qos根据文档设置为1
     *
     * @param topic
     * @param msg
     */
    public void publish(String topic, String msg) {
        publish(1, false, topic, msg);
    }
 
    /**
     * 发布
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            log.info("topic：" + topic + " 不存在");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
 
            if (!token.isComplete()) {
                log.info("消息发送成功");
            }
        } catch (MqttPersistenceException e) {
            log.error(ExcptUtil.filterStack(e));
        } catch (MqttException e) {
            log.error(ExcptUtil.filterStack(e));
        }
    }
}