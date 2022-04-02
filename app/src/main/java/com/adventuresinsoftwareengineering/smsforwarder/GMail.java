package com.adventuresinsoftwareengineering.smsforwarder;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMail {
    String emailPort;
    String emailHost;
    final String smtpAuth = "true";
    final String starttls = "true";

    String fromEmail;
    String fromPassword;
    String toEmailList;
    String emailSubject;
    String emailBody;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public GMail(String fromEmail, String fromPassword,
                 String toEmailList, String emailSubject, String emailBody) {
        this.emailHost = PreferenceManager.getInstance().getSMTPServer() + "";
        this.emailPort = PreferenceManager.getInstance().getSMTPPort() + "";
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
    }

    public MimeMessage createEmailMessage() throws MessagingException, UnsupportedEncodingException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailList));

        emailMessage.setSubject(emailSubject);
        emailMessage.setText(emailBody);
        return emailMessage;
    }

    public void sendEmail() throws MessagingException {
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }
}