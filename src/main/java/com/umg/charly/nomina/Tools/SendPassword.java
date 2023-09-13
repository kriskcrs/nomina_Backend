package com.umg.charly.nomina.Tools;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Transport;

public class SendPassword {
    private static final String HOST = "smtp.gmail.com";
    private static final String USERNAME = "proyecto.nomina01@gmail.com";
    private static final String USER_PASSWORD = "mczfepnwfckpkwud";  // NO TOCAR, esta es una contraseña segura que se generó desde la configuración del correo, si la cambian; valines...

    public static void sendPasswordByEmail(String idUser, String password) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, USER_PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(idUser));
            message.setSubject("Contraseña de acceso al sistema");
            String emailContent = "Tu contraseña temporal es: " + password;
            message.setContent(emailContent, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MessagingException("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }
}
