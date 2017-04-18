package com.vlink.campaign.service;

import com.vlink.campaign.model.Email;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by ankush.jadon on 4/17/2017.
 */
public class SendEmail implements Job{
    final static String from = "xyz@gmail.com ";
    final static String password = "password";


    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("exicuting email job");
//        System.out.println(email);
        //Get properties object
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String to = jobDataMap.getString("to");
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            System.out.println(message);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();

        }

    }

}
//class SendMail {
//    public static void main(String[] args) {
//        //from,password,to,subject,message
//        SendEmail sendEmail = new SendEmail();
//        sendEmail.send("ankushsingh859@gmail.com","hello javatpoint","test mail");
//        //change from, password and to
//    }
//}
