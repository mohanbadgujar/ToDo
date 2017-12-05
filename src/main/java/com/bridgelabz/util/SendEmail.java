package com.bridgelabz.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class SendEmail {

	private JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String emailId) {
		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		/* String status = null; */
		try {
			mimeHelper = new MimeMessageHelper(message, true);
			mimeHelper.setTo(emailId);

			mimeHelper.setFrom("Administrator");
			mimeHelper.setSubject("Password Reset");
			mimeHelper.setText(
					"<html><body>hi,<br/><a href='http://localhost:8080/LoginFormSpringJdbcAnnotation/newPassword/"
							+ emailId + "/'> Click here</a> to reset password</body></html>",
					true);
			mailSender.send(message);
			/* status = "Confirmation email is sent to your address (" + emailId + ")"; */

		} catch (MessagingException e) {

			/*
			 * status =
			 * "There was an error in email sending. Please check your email address: " +
			 * emailId;
			 */
			System.out.println("Error Sending email " + e.getMessage());
		}
	}

	@Async
	public String sendRegistrationMail(String emailId, String url) {
		System.out.println("with async " + new Thread().getName());
		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;

		url = "<html><body><a href='" + url + "'>Click here to activate your account!!!</a></body></html>";

		try {

			mimeHelper = new MimeMessageHelper(message, true);
			mimeHelper.setTo(emailId);

			mimeHelper.setFrom("Administrator");
			mimeHelper.setSubject("Send registration success message");
			mimeHelper.setText(url, true);
			mailSender.send(message);
			return "Confirmation email is sent to your address (" + emailId + ")";

		} catch (MessagingException e) {
			System.out.println("Error Sending email " + e.getMessage());
			return "There was an error in email sending. Please check your email address: " + emailId;

		}
	}

	@Async
	public String sendForgotMail(String emailId, String url) {
		System.out.println("with async " + new Thread().getName());
		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;

		try {

			mimeHelper = new MimeMessageHelper(message, true);
			mimeHelper.setTo(emailId);

			mimeHelper.setFrom("Administrator");
			mimeHelper.setSubject("Send registration success message");
			mimeHelper.setText(url, true);
			mailSender.send(message);
			return "Confirmation email is sent to your address (" + emailId + ")";

		} catch (MessagingException e) {
			System.out.println("Error Sending email " + e.getMessage());
			return "There was an error in email sending. Please check your email address: " + emailId;

		}
	}

}