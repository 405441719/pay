package com.payinxl.common.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

// jishouadmin@163.com cardpay
public class SimpleMailSender
{
    /**
     * 发送邮件
     * 
     * @param mailInfo 待发送的邮件的信息
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
	public static final String SERVER_ADDRESS="smtp.163.com";
	public static final String EMAIL_FROM="18651850720@163.com";
	public static final String EMAIL_DISPLAYNAME="云卡付";
	public static final String EMAIL_USERNAME="18651850720";
	public static final String EMAIL_PASSWORD="ykf1q2w3e";
    public static void sendMail(MailInfo mailInfo) throws UnsupportedEncodingException, MessagingException
    {
        Session session = null;
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailInfo.getSmtpServer());
        // 服务器需要身份认证
        if (mailInfo.isAuth())
        {
            props.put("mail.smtp.auth", "true");
            SmtpAuth smtpAuth = new SmtpAuth(mailInfo.getUsername(), mailInfo.getPassword());
            session = Session.getDefaultInstance(props, smtpAuth);
        }
        else
        {
            props.put("mail.smtp.auth", "false");
            session = Session.getDefaultInstance(props, null);
        }
        session.setDebug(false);
        Message msg = new MimeMessage(session);
        Address fromAddress = new InternetAddress(mailInfo.getFrom(), mailInfo.getDisplayName());
        msg.setFrom(fromAddress);
        if (mailInfo.getTo() != null && mailInfo.getTo().length > 0)
        {
            InternetAddress[] toAddress = new InternetAddress[mailInfo.getTo().length];
            for (int i = 0; i < mailInfo.getTo().length; i++)
            {
                toAddress[i] = new InternetAddress(mailInfo.getTo()[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, toAddress);
        }

        if (mailInfo.getCc() != null && mailInfo.getCc().length > 0)
        {
            InternetAddress[] ccAddress = new InternetAddress[mailInfo.getCc().length];
            for (int i = 0; i < mailInfo.getCc().length; i++)
            {
                ccAddress[i] = new InternetAddress(mailInfo.getCc()[i]);
            }
            msg.setRecipients(Message.RecipientType.CC, ccAddress);
        }

        if (mailInfo.getBcc() != null && mailInfo.getBcc().length > 0)
        {
            InternetAddress[] bccAddress = new InternetAddress[mailInfo.getBcc().length];
            for (int i = 0; i < mailInfo.getBcc().length; i++)
            {
                bccAddress[i] = new InternetAddress(mailInfo.getBcc()[i]);
            }
            msg.setRecipients(Message.RecipientType.BCC, bccAddress);
        }

        msg.setSubject(mailInfo.getSubject());
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailInfo.getContent(), "text/html;charset=gb2312");
        multipart.addBodyPart(mimeBodyPart);
        // 有附件
        if (!mailInfo.getFiles().isEmpty())
        {
            Enumeration<File> en = mailInfo.getFiles().elements();
            while (en.hasMoreElements())
            {
                mimeBodyPart = new MimeBodyPart();
                String filename = en.nextElement().toString();
                FileDataSource fileDataSource = new FileDataSource(filename);
                mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
                mimeBodyPart.setFileName(fileDataSource.getName());
                multipart.addBodyPart(mimeBodyPart);
            }
        }
        msg.setContent(multipart);
        msg.setSentDate(new Date());
        msg.saveChanges();
        Transport trans = session.getTransport("smtp");
        trans.connect(mailInfo.getSmtpServer(), mailInfo.getUsername(), mailInfo.getPassword());
        trans.sendMessage(msg, msg.getAllRecipients());
        trans.close();
    }

    static class SmtpAuth extends Authenticator
    {
        private String username, password;

        public SmtpAuth(String username, String password)
        {
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(username, password);
        }
    }
    
    public static void sendEmailMsg(String toEmail, String title, String msg) throws UnsupportedEncodingException, MessagingException{
    	  MailInfo mailInfo = new MailInfo();
          mailInfo.setAuth(true);
          mailInfo.setTo(toEmail);
          mailInfo.setContent(msg);
          mailInfo.setDisplayName(EMAIL_DISPLAYNAME);
          mailInfo.setFrom(EMAIL_FROM);
          mailInfo.setUsername(EMAIL_USERNAME);
          mailInfo.setPassword(EMAIL_PASSWORD);
          mailInfo.setSmtpServer(SERVER_ADDRESS);
          mailInfo.setSubject(title);
          SimpleMailSender.sendMail(mailInfo);
    }
    public static void main(String[] args) throws MessagingException, IOException
    {
//        File file = new File("G:\\maven_local_lib\\javax\\mail\\mail\\1.5.0-b01\\_maven.repositories");
        MailInfo mailInfo = new MailInfo();
        mailInfo.setAuth(true);
        mailInfo.setTo("405441719@qq.com");
        // mailInfo.setCc("cumtysf@sina.com");
        // mailInfo.setBcc("cumtysf@163.com");
/*        FileInputStream fis = new FileInputStream("C:\\Documents and Settings\\Administrator\\桌面\\3yuefen.html");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch = 0;
        while ((ch = fis.read()) != -1)
        {
            baos.write(ch);
        }*/
//        String content = baos.toString("gbk");
//        mailInfo.setContent("邮件测试");
//        mailInfo.setDisplayName("收卡");
////        mailInfo.setFiles(file);
//        mailInfo.setFrom("jishouadmin@163.com");
//        mailInfo.setUsername("jishouadmin");
//        mailInfo.setPassword("cardpay");
//        mailInfo.setSmtpServer("smtp.163.com");
//        mailInfo.setSubject("测试邮件003号");
        SimpleMailSender.sendEmailMsg("405441719@qq.com","ykf","ykf");
    }
}
