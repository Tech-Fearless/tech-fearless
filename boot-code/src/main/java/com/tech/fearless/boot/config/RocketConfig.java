package com.tech.fearless.boot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RocketConfig {


    @Value("${spring.rocketmq.nameServer}")
    public String nameServer;

    @Value("${spring.rocketmq.topic}")
    public String topic;

    @Value("${spring.rocketmq.consumerGroup}")
    public String consumerGroup;

    @Value("${spring.rocketmq.producerGroup}")
    public String producerGroup;

    @Value("${spring.rocketmq.tag}")
    public String tag;

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
