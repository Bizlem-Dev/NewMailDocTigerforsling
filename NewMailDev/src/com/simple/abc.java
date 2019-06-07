package com.simple;

import java.io.IOException;

public class abc {
public static  void main(String [] argss) {
	// TODO Auto-generated method stub
try {
	String	status= new SendAttachmentInEmail().sendMail("vivek@bizlem.com", "demo", "body", "pallavi@bizlem.com", "Pallu@123", null);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
}
