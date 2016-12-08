package com.codecool.shop.util;

import com.codecool.shop.model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailFacade {

    public EmailFacade () {}

    public void sendWelcome(User user) {

        final String username = "testerzh1234@gmail.com";
        final String password = "chillcoders";
        String subject = "Welcome to our shop!";
        String text = "Dear "+ user.getName() +","
                + "\n\n NYANYANYANYA! THIS IS A NO SPAM!";


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Email sent for user:" + user.getID() + ", " +user.getName()+ ", " +user.getEmail());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}