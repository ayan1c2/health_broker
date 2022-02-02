//https://www.tutorialspoint.com/passay/passay_quick_guide.htm

package com.passay;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class PasswordGeneration {
	   public static void main(String[] args) {
	      CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
	      CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
	      CharacterRule special = new CharacterRule(EnglishCharacterData.Special);

	      PasswordGenerator passwordGenerator = new PasswordGenerator();
	      String password = passwordGenerator.generatePassword(10, alphabets, digits, special);
	      System.out.println(password);
	   }
	}
