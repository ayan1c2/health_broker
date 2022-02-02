package com.mqtt.core;

/* Eclipse paho MQTT 3.x pub-sub model -  https://www.eclipse.org/paho/clients/java/ */

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;


class SimpleCallback implements MqttCallback {
    
	/* Called when the client lost the connection to the broker */
    public void connectionLost(Throwable cause) {
    	System.out.println("connectionLost..");
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("-------------------------------------------------");
        System.out.println("| Topic:" + topic);
        System.out.println("| Message: " + new String(message.getPayload()));
        System.out.println("-------------------------------------------------");
    }
    
    /* Called when a outgoing publish is complete */
    public void deliveryComplete(IMqttDeliveryToken token) { 
    	System.out.println("deliveryComplete..");
    }
}

/* The included code below is a very basic sample that connects to a server and publishes a message using the MqttClient synchronous API. 
 * More extensive samples demonstrating the use of the Asynchronous API can be found in the org.eclipse.paho.sample.mqttv3app directory of the source.*/

public class MqttPublishSubscribeSamplePaho {
	
	static int count;
	
	public static void publishMessage(String content) {
		String topic = "MQTT_1";
        //String content = "Message from MqttPublishSample";
        int qos = 2;
        String broker = "tcp://broker.hivemq.com:1883"; //public broker: http://www.mqtt-dashboard.com/ 
        //String broker = "tcp://localhost:4369";
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence); // or MqttClient.generateClientId()
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            connOpts.setCleanSession(true);
            
            //Authentication
            //connOpts.setUserName("username");
            //connOpts.setPassword("password".toCharArray());            
                                   
            System.out.println("Connecting to broker: " + broker);
            
            if(!sampleClient.isConnected()) {
            	sampleClient.connect(connOpts);
            	System.out.println("Connected");
            }else {
            	System.out.println("Already connected..");
            }
            //sampleClient.subscribe("#", 1);
            //System.out.println("Connected");
            
            System.out.println("Publish message: " + content);
            
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.setCallback(new SimpleCallback());
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            
            try {
                Thread.sleep(5000);
                sampleClient.disconnect();
            } catch(Exception e) {
                e.printStackTrace();
            }
            
            System.out.println("Disconnected");
            //System.exit(0);
            
        } catch(MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("except " + me);
            me.printStackTrace();
        }
	}
	
	public static void main(String[] args){
				
		while(true) {
			publishMessage("Message_"+new Date()+"_"+count);
			count++;
		}
        
    }
}