package iot.cloud.platform.lock.config;

import iot.cloud.platform.lock.utils.ExcptUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Slf4j
@Data
public class MqttConfig {

    @Value("${mqtt.host}")
    private String host;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.topic.post}")
    private String topic_post;

    @Value("${mqtt.topic.post_reply}")
    private String topic_post_reply;

    @Value("${mqtt.topic.receive}")
    private String topic_receive;

    @Value("${mqtt.topic.receive_reply}")
    private String topic_receive_reply;

    @Value("${mqtt.qos}")
    private int qos;

    @Bean
    public MqttClient getMqttClient(){
        MqttClient client=null;
        try {
            final String id= clientId;
            client = new MqttClient(host, id, new MemoryPersistence());
            log.info("创建mqtt客户端ID:"+id);
        } catch (Exception e) {
            log.error("创建mqtt客户端异常："+ ExcptUtil.filterStack(e));
        }
        return client;
    }

    @Bean
    public MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        // 是否清除session
        options.setCleanSession(false);
        return options;
    }

    /**
     * 初始化已订阅主题
     * @return
     */
    @Bean("mqttSubTopics")
    public Map<String,Integer> getMqttSubTopics(){
        Map<String,Integer> mqttSubTopics=new ConcurrentHashMap<>();
        mqttSubTopics.put(topic_post_reply,qos);
        mqttSubTopics.put(topic_receive,qos);
        return mqttSubTopics;
    }

    /**
     * 初始化已订阅主题对应的回复主题
     * @return
     */
    @Bean("mqttTopicReplyMap")
    public Map<String,String> getMqttTopicReplyMap(){
        Map<String,String> topicReplyMap=new ConcurrentHashMap<>();
        topicReplyMap.put(topic_post_reply,topic_post);
        topicReplyMap.put(topic_receive,topic_receive_reply);
        return topicReplyMap;
    }
}