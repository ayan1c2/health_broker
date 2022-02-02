package com.uuid;

import java.util.UUID;

public class RandomStringUUID {
	
   public static void main(String[] args) {    	
    	String name = "Ayan";
    	String surname = "Chatterjee";
    	
    	UUID uuid = UUID.randomUUID();
    	String id = name.charAt(0) + surname.charAt(0) + "-"+ uuid.toString() + "-" + (System.currentTimeMillis());
    	String nickName = name.charAt(0) + "." + surname.charAt(0) + "-" + (System.currentTimeMillis());
        System.out.println("UUID = " + id); 
        System.out.println("nickName = " + nickName); 
    }
}