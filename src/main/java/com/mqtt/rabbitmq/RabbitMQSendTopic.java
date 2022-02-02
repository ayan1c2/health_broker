package com.mqtt.rabbitmq;

import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSendTopic {

	private static final String EXCHANGE_NAME = "topic_logs";
	private static String routingKey = "MQTT_1.green.first"; //name of the topic

	public static void main(String[] argv) throws Exception {
		
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);//automatically takes it
        factory.setUsername("guest");
        factory.setPassword("guest");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
        	
        	//The producer can only send messages to an exchange instead of queue
            //There are a few exchange types available: direct, topic, headers and fanout
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String message = new Date().toString();

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
	
}
