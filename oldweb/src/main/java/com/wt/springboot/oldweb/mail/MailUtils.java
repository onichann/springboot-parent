package com.wt.springboot.oldweb.mail;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailUtils {  
    private static String host;  
    private static String username;  
    private static String password;  
    private static String from;  
    private static String nick;  
  
    static {  
        try {  
            // Test Data  
            host = "smtp.163.com";  
            username = "wt01278@163.com";  
            password = "wt520520";  
            from = "wt01278@163.com";  
            nick = "admin";  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
  
    /** 
     *  
     *  
     * @param to 
     *
     * @param subject 
     *             
     * @param body 
     *             
     * @param filepath 
     *
     * @return 
     * @throws MessagingException 
     * @throws AddressException 
     * @throws UnsupportedEncodingException 
     */  
    public static boolean sendMail(String to, String subject, String body,  
            List<String> filepath) throws AddressException, MessagingException,  
            UnsupportedEncodingException {  

        if (body == null) {  
            body = "";  
        }  
        if (subject == null) {  
            subject = "";  
        }  
        // Properties  
        Properties props = System.getProperties();  
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        //Session session = Session.getInstance(props, null);
        MimeMessage msg = new MimeMessage(session);
        nick = MimeUtility.encodeText(nick);  
        //msg.setFrom(new InternetAddress(nick + "<" + from + ">"));  
        msg.setFrom(new InternetAddress(from,"111","UTF-8"));  
        if (to != null && to.trim().length() > 0) {
            String[] arr = to.split(",");  
            int receiverCount = arr.length;  
            if (receiverCount > 0) {  
                InternetAddress[] address = new InternetAddress[receiverCount];  
                for (int i = 0; i < receiverCount; i++) {  
                    address[i] = new InternetAddress(arr[i]);  
                }  
                msg.addRecipients(Message.RecipientType.TO, address);  
                msg.setSubject(subject);  
                // BodyPartMultipart  
                Multipart mp = new MimeMultipart();  
                if (filepath != null && filepath.size() > 0) {
                    for (String filename : filepath) {  
                        MimeBodyPart mbp = new MimeBodyPart();  
                        FileDataSource fds = new FileDataSource(filename);
                        // BodyPart  
                        mbp.setDataHandler(new DataHandler(fds));  
                        // BodyPart  
                        mbp.setFileName(fds.getName());  
                        mp.addBodyPart(mbp);  
                    }  
                    MimeBodyPart mbp = new MimeBodyPart();  
                    mbp.setText(body);  
                    mp.addBodyPart(mbp);  
                    filepath.clear();
                    // Multipart  
                    msg.setContent(mp);  
                } else {  
                    msg.setText(body);
                }  
                msg.setSentDate(new Date());
                msg.saveChanges();  
                Transport transport = session.getTransport("smtp");
                transport.connect(host, username, password);  
                transport.sendMessage(msg,  
                        msg.getRecipients(Message.RecipientType.TO));  
                transport.close();  
                return true;  
            } else {  
                System.out.println("None receiver!");  
                return false;  
            }  
        } else {  
            System.out.println("None receiver!");  
            return false;  
        }  
    }  
  
    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
        List<String> filepath = new ArrayList<>();  
        //filepath.add("E:\\1.jpg");  
//        filepath.add("E:\\apache-tomcat-8.5.32\\RUNNING.txt");
        sendMail("941349177@qq.com", "嘿嘿，你一定猜不到我能用这种姿势划水！！！", "邮件内容：要来一起游泳吗？",
                filepath);


    }  
}  