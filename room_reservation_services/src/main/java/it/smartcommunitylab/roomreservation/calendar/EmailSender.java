package it.smartcommunitylab.roomreservation.calendar;

import java.io.IOException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailSender
{
	private static EmailSender me = null;

    private static JavaMailSenderImpl mailSender = null;

    public static EmailSender getEmailSender() throws IOException {
    	if (me == null) {
    		me = new EmailSender();
    	}
    	return me;
    }

    public EmailSender() throws IOException {
    	mailSender = new org.springframework.mail.javamail.JavaMailSenderImpl();
    	mailSender.setJavaMailProperties(CalendarServiceProperties.getProperties());
    	mailSender.setPassword(CalendarServiceProperties.getProperties().getProperty("mail.smtp.password"));
    	mailSender.setUsername(CalendarServiceProperties.getProperties().getProperty("mail.smtp.username"));
    }

    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String from, String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
