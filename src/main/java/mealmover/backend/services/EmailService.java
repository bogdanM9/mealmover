package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.frontend.url}")
    private String frontendUrl;

    private final JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        this.mailSender.send(message);
    }

    @Async
    public void sendActivateAccountEmail(String to, String token) {
        logger.info("Sending activate account email to {}", to);
        String subject = "[%s] Account activation".formatted(this.appName);
        String content = """
            In order to activate your account, you need to click on next link: %s/activate?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(to, subject, content);
    }

    public void sendForgotPasswordEmail(String email, String token) {
        logger.info("Sending activate account email to {}", email);
        String subject = "[%s] Account activation".formatted(this.appName);
        String content = """
            In order to reset your password, you need to click on next link: %s/reset-password?token=%s
        """.formatted(this.frontendUrl, token);
        this.sendEmail(email, subject, content);
    }

    public void sendChangeEmailEmail(String email, String newEmail, String token) {
        logger.info("Sending change email email to {}", email);
        String subject = "[%s] Email change".formatted(this.appName);
        String content = """
            In order to change your email, you need to click on next link: %s/change-email?token=%s&newEmail=%s
        """.formatted(this.frontendUrl, token, newEmail);
        this.sendEmail(email, subject, content);
    }
}
