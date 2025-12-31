package com.mycompany.gymtracker.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailService {

    // --- CONFIGURATION ---
    private static final String SENDER_EMAIL = "starktvshows@gmail.com";
    private static final String SENDER_PASSWORD = "bkwo slyu kqby fwdm"; 
    
    // Logic: Multiple recipients separated by commas
    private static final String RECIPIENT_EMAILS = 
            "rpavishkadilhara@gmail.com, " +
            "senuribagya09@gmail.com, " +
            "Kprsathsarani51@gmail.com, " +
            "chamathkanivisadee12@gmail.com";

    /**
     * Logic: Sends a BEAUTIFUL HTML-formatted email to all group members.
     */
    public static void sendEmail(String subject, String title, String body) {
        // 1. Setup Mail Server Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 2. Authenticate
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            // 3. Create Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, "Gym Tracker System")); // Adds a Sender Name
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAILS));
            message.setSubject("🔔 " + subject); // Adds a bell icon to the subject line

            // 4. BEAUTIFUL HTML Email Design
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            // Note: Inline CSS is required for Gmail/Outlook compatibility
            String htmlContent = 
                "<div style='font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; background-color: #121212; padding: 40px 0;'>" +
                "  <div style='max-width: 600px; margin: 0 auto; background-color: #1e1e1e; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.5);'>" +
                
                // HEADER
                "    <div style='background-color: #000000; padding: 30px; text-align: center; border-bottom: 3px solid #FFD700;'>" +
                "      <h1 style='color: #FFD700; margin: 0; font-size: 28px; letter-spacing: 2px; text-transform: uppercase;'>Gym Tracker</h1>" +
                "      <p style='color: #888888; font-size: 12px; margin: 5px 0 0 0; text-transform: uppercase;'>Professional Management System</p>" +
                "    </div>" +
                
                // BODY CONTENT
                "    <div style='padding: 40px 30px; color: #e0e0e0;'>" +
                "      <h2 style='color: #ffffff; font-size: 22px; margin-top: 0; border-left: 4px solid #FFD700; padding-left: 15px;'>" + title + "</h2>" +
                "      <p style='font-size: 16px; line-height: 1.6; color: #cccccc; margin-top: 20px;'>" + body + "</p>" +
                
                // STATUS BOX (Optional visual flair)
                "      <div style='margin-top: 30px; background-color: #2c2c2c; padding: 15px; border-radius: 5px; border-left: 4px solid #28a745;'>" +
                "        <p style='margin: 0; color: #a1a1a1; font-size: 13px;'><strong>Status:</strong> System Operational</p>" +
                "      </div>" +
                "    </div>" +
                
                // FOOTER
                "    <div style='background-color: #181818; padding: 20px; text-align: center; border-top: 1px solid #333333;'>" +
                "      <p style='color: #666666; font-size: 12px; margin: 0;'>Automated Alert • Do Not Reply</p>" +
                "      <p style='color: #666666; font-size: 12px; margin: 5px 0 0 0;'>Generated on: " + time + "</p>" +
                "    </div>" +
                "  </div>" +
                "</div>";

            // 5. Construct MIME Body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            // 6. Send
            Transport.send(message);
            System.out.println("✅ Beautiful Email sent successfully to group!");

        } catch (Exception e) {
            System.err.println("Email Service Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}