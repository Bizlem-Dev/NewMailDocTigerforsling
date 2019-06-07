package com.simple;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EMail {

    

    private static final String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidEmail(String address){
        return (address!=null && address.matches(EMAIL_PATTERN));
    }

    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

public static void main(String[] args) {
	
	EMail receiver = new EMail();
		String[] filenames= {"AllTasks.txt","AllTasksOutput.txt"};
//	String[] filenames= {};
		
		receiver.sendEmail("tejal.bizlem@gmail.com", "rishi.khan0909@gmail.com", "subj", "con hi  hello", filenames,"smtp.gmail.com","rishi.khan0909@gmail.com","rishikhan10","");
//		receiver.SendMail("abhishek.bizlem@gmail.com", "adttech26@gmail.com", "", "hello", "hi how r u");
	}
    
    public static String sendEmail(final String recipients, final String from,
            final String subject, final String contents,final String[] attachments,
            final String smtpserver, final String username, final String password,final String appPath) {

        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", smtpserver);
//    	String sd="E:\\MailtangyWork\\docs\\";
        Session session = null;

//        switch (method){
//        case HTTP:
//            if (username!=null) props.setProperty("mail.user", username);
//            if (password!=null) props.setProperty("mail.password", password);
//            session = Session.getDefaultInstance(props);
//            break;
//        case TLS:
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username, password);
                }
            });
//            break;
//        case SSL:
//            props.put("mail.smtp.socketFactory.port", "465");
//            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.port", "465");
//            session = Session.getDefaultInstance(props, new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication(){
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//            break;
//        }

        try {
        	
        	 session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
            MimeMessage message = new MimeMessage(session);

//            message.setFrom(from);
            message.addRecipients(Message.RecipientType.TO, recipients);
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            BodyPart bodypart = new MimeBodyPart();
            bodypart.setContent(contents, "text/html");

            multipart.addBodyPart(bodypart);

            if (attachments!=null){
                for (int co=0; co<attachments.length; co++){
                    bodypart = new MimeBodyPart();
                    File file = new File(appPath+attachments[co]);
                    DataSource datasource = new FileDataSource(file);
                    bodypart.setDataHandler(new DataHandler(datasource));
                    bodypart.setFileName(file.getName());
                    multipart.addBodyPart(bodypart);
                }
            }else {
            	
            }
            
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("send ...");

        } catch(MessagingException e){
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
}