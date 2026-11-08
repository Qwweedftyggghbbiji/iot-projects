package iot.cloud.platform.thermometer.config;

import iot.cloud.platform.thermometer.service.ConfigService;
import iot.cloud.platform.thermometer.utils.ExcptUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ConfigService configService;
    /**
     *  qos
     *  MQTT协议中有三种消息发布服务质量:
     *  QOS 0： “至多一次”，消息发布完全依赖底层 TCP/IP 网络。会发生消息丢失或重复。这一级别可用于如下情况，环境传感器数据，丢失一次读记录无所谓，因为不久后还会有第二次发送。
     *  QOS 1： “至少一次”，确保消息到达，但消息重复可能会发生。
     *  QOS2： “只有一次”，确保消息到达一次。这一级别可用于如下情况，在计费系统中，消息重复或丢失会导致不正确的结果，资源开销大
     *
     */
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
        if(StringUtils.isNotBlank(username)) {
            options.setUserName(username);
        }
        if(StringUtils.isNotBlank(password)) {
            options.setPassword(password.toCharArray());
        }
        // 是否清除session
        options.setCleanSession(false);
        // 设置连接超时时间
        options.setConnectionTimeout(5);
        // 设置自动重连
        options.setAutomaticReconnect(true);
        // 设置心跳时间
        options.setKeepAliveInterval(30);
        return options;
    }

    /**
     * 初始化已订阅主题
     * @return
     */
    @Bean("mqttSubTopics")
    public Map<String,Integer> getMqttSubTopics(){
        Map<String,Integer> mqttSubTopics=new ConcurrentHashMap<>();
        String[] topics=new String[]{topic_post_reply,topic_receive,configService.getV(Const.CONFIG_K_DEVTOPICPOSTREPLY),configService.getV(Const.CONFIG_K_DEVTOPICRECEIVE)};
        for(String topic:topics){
            if(StringUtils.isNotBlank(topic)){
                log.info("加入订阅主题->"+topic+" (qos="+qos+")");
                mqttSubTopics.put(topic,qos);
            }
        }
        return mqttSubTopics;
    }

    /**
     * 初始化已订阅主题对应的回复主题
     * @return
     */
    @Bean("mqttTopicReplyMap")
    public Map<String,String> getMqttTopicReplyMap(){
        Map<String,String> topicReplyMap=new ConcurrentHashMap<>();
        topicReplyMap.put(topic_receive,topic_receive_reply);
        String tRecv=configService.getV(Const.CONFIG_K_DEVTOPICRECEIVE);
        String tRecvReply=configService.getV(Const.CONFIG_K_DEVTOPICRECEIVEREPLY);
        if(StringUtils.isNotBlank(tRecv)&&StringUtils.isNotBlank(tRecvReply)){
            topicReplyMap.put(tRecv,tRecvReply);
        }
        topicReplyMap.forEach((k,v)->{
            log.info("接收主题->"+k+" 响应主题->"+v);
        });
        return topicReplyMap;
    }
}
