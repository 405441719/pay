package com.payinxl.common.mail;

import java.io.File;
import java.util.Vector;

public class MailInfo
{
    // 显示的名称
    private String displayName;
    // 发送邮件的服务器的IP和端口
    private String smtpServer;
    // 邮件发送者的地址
    private String from;
    // 邮件接收者的地址
    private String[] to;
    // 抄送
    private String[] cc;
    // 密送
    private String[] bcc;
    // 登陆邮件发送服务器的用户名和密码
    private String username;
    private String password;
    // 是否需要身份验证
    private boolean auth = false;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // // 邮件附件的文件名
    // private String[] filenames;
    // 用于保存发送附件的文件名的集合
    private Vector<File> files = new Vector<File>();

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getSmtpServer()
    {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer)
    {
        this.smtpServer = smtpServer;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String[] getTo()
    {
        return to;
    }

    public void setTo(String... to)
    {
        this.to = to;
    }

    public String[] getCc()
    {
        return cc;
    }

    public void setCc(String... cc)
    {
        this.cc = cc;
    }

    public String[] getBcc()
    {
        return bcc;
    }

    public void setBcc(String... bcc)
    {
        this.bcc = bcc;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isAuth()
    {
        return auth;
    }

    public void setAuth(boolean auth)
    {
        this.auth = auth;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    // public String[] getFilenames()
    // {
    // return filenames;
    // }
    //
    // public void setFilenames(String[] filenames)
    // {
    // this.filenames = filenames;
    // }

    public Vector<File> getFiles()
    {
        return files;
    }

    public void setFiles(File file)
    {
        this.files.add(file);
    }

}
