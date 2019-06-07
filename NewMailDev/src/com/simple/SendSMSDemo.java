package com.simple;

import com.twilio.Twilio; 
import com.twilio.converter.Promoter; 
import com.twilio.rest.api.v2010.account.Message; 
import com.twilio.type.PhoneNumber; 
 
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal; 
 
public class SendSMSDemo { 
    // Find your Account Sid and Token at twilio.com/console 
    public static final String ACCOUNT_SID = "AC33bf01eb146bd37250ad87365b8cf3da"; 
    public static final String AUTH_TOKEN = "7f6b43e8ab135387afe9a7ef70dcd9db"; 
 
    public static void main(String[] args) { 
    	SendSMSDemo s=new SendSMSDemo();
    	try {
			s.SendSMS("+917208623770","Your document has been mailed.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    
    public String SendSMS(String to, String msg) throws IOException{
    	
    	//PrintWriter out;
		//try {
			//out= rep.getWriter();
			
			
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN); 
	        
	        Message message = Message.creator( 
	                new com.twilio.type.PhoneNumber(to), 
	                new com.twilio.type.PhoneNumber("+18593286242"), //18593286242 
	                msg)      
	            .create(); 
	 
	        System.out.println(message.getSid());
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/

		return "Success"; 
    }
}
