/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Information;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Hieu
 */
public class Email {

    public static void sendConfirmationEmail(String customerEmail, int orderId, String statusMessage) {
        // Sender's email ID
        String from = "onlineshopsystem95@gmail.com";
        String host = "smtp.gmail.com";
        String password = "bgmhithlxyctqepb";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Use 465 for SSL or 587 for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password); // Add your credentials
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));

            // Set Subject: header field
            message.setSubject("Order Status Update: Order #" + orderId);

            // Craft the email body content
            String content = "Dear Customer,\n\n"
                    + "We are pleased to inform you that the status of your order #" + orderId + " has been updated.\n"
                    + "Current Status: " + statusMessage + "\n\n"
                    + "Thank you for shopping with us!\n"
                    + "Best regards,\n"
                    + "Online Shop System";

            // Set the actual message
            message.setText(content);

            // Send the message
            Transport.send(message);
            System.out.println("Confirmation email sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
