/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: SendEmail.java
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilts.anywhere.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send Email set Properties get the mail Session generate message Connect to
 * the SMTP server and send message
 *
 * @author ssanapureddy
 */
public class SendEmail {
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public String generateAndSendEmail(String uName, String email, String password) throws MessagingException {
        String result;
        if (uName == null || email == null) {
            result = "Fail";
            return result;
        } else {
//           String from = "test.ilts.noreply@gmail.com";
//           String pass = "India@@123";
            System.out.println("**************** Mail Server Properties***************");
            // String host = "smtp.gmail.com";

            mailServerProperties = System.getProperties();

            mailServerProperties.put("mail.smtp.host", "corp01-exch03.corp.ilts.com");
            mailServerProperties.put("mail.smtp.port", "25");
            mailServerProperties.put("mail.smtp.starttls.enable", "false");
//          mailServerProperties.put("mail.smtp.auth", "false");
//          mailServerProperties.put("mail.smtp.auth", "false");
//          mailServerProperties.put("mail.smtp.starttls.enable", "false");
//          mailServerProperties.put("mail.smtp.host", "webmail.ilts.com");
//          mailServerProperties.put("mail.smtp.port", "465");
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            generateMailMessage.setSubject("Request for Password");
            generateMailMessage.setFrom("noreply@ilts.com");
            String emailBody = "Hi "+uName+"<br><br>******** Welcome to mail system by Data Trak Anywhere ******** <br><br>"
                    + " <b>Note: Please do not respond to this email.</b><br><br>This is your password : <b style='color:blue;'>" 
                    + password+"</b><br> In case you need to change the password,"
                    + " please contact our Customer Support Representative.<br><br><br>Thanks,<br>Data Trak Team";
            generateMailMessage.setContent(emailBody, "text/html; charset=utf-8");

            System.out.println("*****************Get Session and send email************* ");
            Transport transport = getMailSession.getTransport("smtp");

            transport.connect();
//            transport.connect("ssanapureddy@ilts.com", "Prasanna@3483");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            result = "Success";
            System.out.println("*****************Send Email result **** result " + result);
            transport.close();

            return result;
        }
    }

//   	**************** This method set up is with using spring frame work mail set up*********************
    
//    private MailSender mailSender;
//	
//	public void setMailSender(MailSender mailSender) {
//		this.mailSender = mailSender;
//	}
//	
//	public void sendMail(String from, String to, String subject, String msg) {
//                System.out.println("______________________Inside Send email _______________________");
//
//		SimpleMailMessage message = new SimpleMailMessage();
//		
//		message.setFrom(from);
//		message.setTo(to);
//		message.setSubject(subject);
//		message.setText(msg);
//		mailSender.send(message);	
//	}
//         
//        public String sendEmail(String pwd){
//       
//   return "";  
//   } 
}
