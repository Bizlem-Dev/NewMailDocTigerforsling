package com.simple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
//import javax.jcr.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;

public class SendAttachmentInEmail {

	 public static void main(String[] args) {
		 
		SendAttachmentInEmail s= new SendAttachmentInEmail();
		 
		 try {
//			s.sendMail("tejal.jabade@bizlem.com", "test11", "This is Test mail<p>Hello $$Name$$,</p><p>How are you?</p><p><strong>This is test mail sent from DocTiger.</strong></p><p><u>hiiiiiiiiii</u></p><p>$$ID$$</p><p>Thanks</p><p> </p>", "doctigertest@gmail.com", "doctiger@123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 String fName="";
	 public String sendMail(String to, String subject, String body, String attachFilePath, String fromId, String fromPass,HttpServletResponse rep) throws IOException{
		PrintWriter out;
		out= rep.getWriter();
		System.out.println("to: "+to);
		InputStream fileins= null;
//		out.println("in sendmail");
			
		String fileName= "";
		fileName= attachFilePath.substring(attachFilePath.lastIndexOf("/"));
		System.out.println("fileName: "+fileName);
		
		File filePath = null;
		Properties props = new Properties();
		 
		props.setProperty("mail.transport.protocol", "smtp");     
		props.setProperty("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", "465");  
		props.put("mail.debug", "true");  
		props.put("mail.smtp.socketFactory.port", "465");  
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		props.put("mail.smtp.socketFactory.fallback", "false");  
		    
		Authenticator authenticator = new Authenticator () {
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(fromId,fromPass);//userid and password for "from" email address 
		    }
		};
		  // Get the Session object.
		Session session = Session.getInstance(props, authenticator);
		/*  out.println("Authentcator: "+authenticator);
		  out.println("Props: "+props);
		  out.println("Session: "+session);
  */
		  try {
		     // Create a default MimeMessage object.
		    Message message = new MimeMessage(session);

		     // Set From: header field of the header.
		    message.setFrom(new InternetAddress(fromId));

		     // Set To: header field of the header.
		    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

		     // Set Subject: header field
		    message.setSubject(subject);

		     // Create the message part
		    BodyPart messageBodyPart = new MimeBodyPart();
		    messageBodyPart.setContent(body,"text/html");
		     // Now set the actual message
//		    messageBodyPart.setText(body);

		     // Create a multipar message
		    Multipart multipart = new MimeMultipart();

		     // Set text message part
		    multipart.addBodyPart(messageBodyPart);

		     // Part two is attachment
		    messageBodyPart = new MimeBodyPart();  
		   
		    attachFilePath = attachFilePath.replace(" ", "%20");
		    URL url;
		    InputStream ins = null;
		    StringBuilder requestString = new StringBuilder(attachFilePath);

		    url = new URL(requestString.toString());
	  		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	  		conn.setRequestMethod("GET");
	  		conn.setRequestProperty("Accept","application/text");
	  		ins = conn.getInputStream();
	  		
	  		filePath= whenConvertingToFile_thenCorrect(ins, fileName);
	  			  			
		        //out.println("FilePath: "+filePath);
	  		DataSource source = new FileDataSource(filePath);
	  			//out.println("Source: "+source.getName());
		    messageBodyPart.setDataHandler(new DataHandler(source));
		    messageBodyPart.setFileName(filePath.getName());
		   // messageBodyPartNew.attachFile(new File(fName));
		    
		    //out.println("messageBodyPart: "+messageBodyPart);
		    multipart.addBodyPart(messageBodyPart);
		    //out.println("multipart: "+multipart);
		     
		    // Send the complete message parts
		    message.setContent(multipart);
		    //out.println("message1: "+message);
		    
		    // Send message
		    try{
		    	Transport.send(message);
		    	conn.disconnect();
		     }catch (Exception e) {
				// TODO: handle exception
		    	System.out.println("errorsend: "+e.getMessage());
			}
		    
		  } catch (Exception e) {
		     //throw new RuntimeException(e);
			  System.out.println("error: "+e.toString());
			  e.printStackTrace();
		  }finally {
			if (fileins != null) {
				try {
					fileins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			filePath.delete();
			
			}
		return "Mail Sent Successfully";
	}
	 public String sendMail(String to, String subject, String body, String fromId, String fromPass, HttpServletResponse rep) throws IOException{
		PrintWriter out;
		out= rep.getWriter();
		
		Properties props = new Properties();
		 
		props.setProperty("mail.transport.protocol", "smtp");     
		props.setProperty("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", "465");  
		props.put("mail.debug", "true");  
		props.put("mail.smtp.socketFactory.port", "465");  
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		props.put("mail.smtp.socketFactory.fallback", "false");  
		    
		Authenticator authenticator = new Authenticator () {
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(fromId,fromPass);//userid and password for "from" email address 
		    }
		};
		  // Get the Session object.
		Session session = Session.getInstance(props, authenticator);
		
		try {
		     // Create a default MimeMessage object.
		    Message message = new MimeMessage(session);

		     // Set From: header field of the header.
		    message.setFrom(new InternetAddress(fromId));

		     // Set To: header field of the header.
		    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

		     // Set Subject: header field
		    message.setSubject(subject);

		     // Now set the actual message
		    message.setContent(body,"text/html");
//		    message.setText(body);

		    	Transport.send(message);
		  } catch (Exception e) {
			 out.println("error: "+e.toString());
			  e.printStackTrace();
		  }
		return "Mail Sent Successfully";
	}
	 
	  	public File whenConvertingToFile_thenCorrect(InputStream is, String fileName) throws IOException {
  		File targetFile = new File("/home/ubuntu/apache-tomcat-8.5.31/webapps/docs/"+fileName);
   	    
  		OutputStream os = new FileOutputStream(targetFile);
          
  		byte[] buffer = new byte[1024];
          int bytesRead;
          //read from is to buffer
          while((bytesRead = is.read(buffer)) !=-1){
              os.write(buffer, 0, bytesRead);
          }
          is.close();
          //flush OutputStream to write any buffered data to file
          os.flush();
          os.close();
  		return targetFile;
  	}

}