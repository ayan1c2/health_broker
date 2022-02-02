package com.uuid;

public class MyTest {
	
	int calculate(int age, int xx, int threshold) {
		if (age > threshold)
			return (xx+threshold);
		else
			return (xx-threshold);
	}
	
	public static void main(String[] args) {
		
		MyTest test = new MyTest();
		
		System.out.println(test.calculate(52, 100, 18));
		System.out.println(test.calculate(17, 100, 18));
	}

}
