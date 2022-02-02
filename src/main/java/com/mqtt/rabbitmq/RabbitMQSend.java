package com.mqtt.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQSend {
	
	private final static String QUEUE_NAME = "hello";
	
	  public static void main(String[] argv) throws Exception {
		  
		  ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("localhost");
		  factory.setPort(5672);//automatically takes it
		  try (Connection connection = factory.newConnection();
		      Channel channel = connection.createChannel()) {
			  
			  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			  String message = "Hello World!";
			  channel.basicPublish("", QUEUE_NAME, null, message.getBytes()); //null exchange
			  System.out.println(" [x] Sent '" + message + "'");

		  }catch(Exception e) {
			  e.printStackTrace();
		  }
	      
	  }
}


