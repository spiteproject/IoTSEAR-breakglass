package be.hcbgsystem.notification.deliverers;

import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;

public class EmailDeliverer implements NotificationDeliverer {
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "healthcare.breakglass@gmail.com";
    private static final String PASSWORD = "x0yD@AMPRQ17F%";
    private static final String EMAIL_FROM = "healthcare.breakglass@gmail.com";
    private static final String EMAIL_SUBJECT = "Break Glass notification";

    @Override
    public void deliverNotification(EmergencyContact contact, String message) {
        ArrayList<EmergencyContact> dummy = new ArrayList<>();
        dummy.add(contact);
        deliverNotification(dummy, message);
    }

    @Override
    public void deliverNotification(ArrayList<EmergencyContact> contacts, String message) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            ArrayList<Address> addresses = new ArrayList<>();
            StringBuilder addressList = new StringBuilder();

            for (EmergencyContact c : contacts) {
                addressList.append(c.getEmail());
                addressList.append(",");
            }

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressList.toString(), false));
            msg.setSubject(EMAIL_SUBJECT);
            msg.setText(message);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());

            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmailDeliverer d = new EmailDeliverer();
        d.deliverNotification(new EmergencyContact(1, "Dries", "Van Bael", "dries.vanbael@live.com", "+32471838275", "patient"), "hello!");
    }
}
