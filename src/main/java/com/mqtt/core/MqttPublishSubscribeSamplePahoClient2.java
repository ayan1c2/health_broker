package com.mqtt.core;


/* Eclipse paho MQTT 3.x pub-sub model -  https://www.eclipse.org/paho/clients/java/ */

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

/* The included code below is a very basic sample that connects to a server and publishes a message using the MqttClient synchronous API. 
 * More extensive samples demonstrating the use of the Asynchronous API can be found in the org.eclipse.paho.sample.mqttv3app directory of the source.*/

public class MqttPublishSubscribeSamplePahoClient2 {
	
	public static void main(String[] args) throws MqttException {
		String topic = "MQTT_1";
        //String content = "Message from MqttPublishSample";
        int qos = 2;
        String broker = "tcp://broker.hivemq.com:1883"; //public broker: http://www.mqtt-dashboard.com/        
        //String clientId = "clientId-HlGI9tjmGy";
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient sampleClient = null;

        try {
            sampleClient = new MqttClient(broker, MqttClient.generateClientId() , persistence); // or MqttClient.generateClientId()       	
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
            
            //System.out.println("Publish message: " + content);
            
            sampleClient.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker 
                	System.out.println("connectionLost...");
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println(topic + ": " +  new String(message.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete 
                	System.out.println("deliveryComplete...");
                }
            });
                    
            if(!sampleClient.isConnected()) {
            	sampleClient.connect(connOpts);
            	System.out.println("Connected");
            }else {
            	System.out.println("Already connected..");
            } 
            sampleClient.subscribe(topic, qos);
            
        } catch(MqttException me){
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("except " + me);
            me.printStackTrace();
        }         
	}
}
