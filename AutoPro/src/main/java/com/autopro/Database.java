/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.autopro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author Lenovo
 */
public class Database {

    public static Integer fileReader(File file) {
        int tienLai = 0;
        try {
            BufferedReader brTienLai = new BufferedReader(new FileReader(file));
            String strTienLai = brTienLai.readLine();
            if (strTienLai != null && !strTienLai.trim().isEmpty()) {
                tienLai = Integer.parseInt(strTienLai);
            }
            brTienLai.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return tienLai;
    }

    public static void fileWriter(Integer number, File file) {
        try {
            BufferedWriter bwTienLai = new BufferedWriter(new FileWriter(file));
            bwTienLai.write(String.valueOf(number));
            bwTienLai.flush();
            bwTienLai.close();
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void fileClear(File file) {
        try ( FileWriter writer = new FileWriter(file, false)) {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(String title, String text) {
        // Cấu hình các thuộc tính SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo phiên làm việc với thông tin đăng nhập
        Session session = Session.getDefaultInstance(props).getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("testtest86n@gmail.com", "mvqxbguzhczgntoj");
            }
        });

        try {
            // Tạo đối tượng Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("testtest86n@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("mrngapro866@gmail.com"));
            message.setSubject(title);
            message.setText(text);

            // Gửi email
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
