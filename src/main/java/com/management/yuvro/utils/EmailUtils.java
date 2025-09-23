package com.management.yuvro.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
@Slf4j
public class EmailUtils {

    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;
    private final String fromEmail;

    public EmailUtils(JavaMailSender javaMailSender, Configuration freemarkerConfig,
                      @Value("${spring.mail.username}") String fromEmail) {
        log.info("EmailUtils initialized with fromEmail: {}", fromEmail);
        this.fromEmail = fromEmail;
        this.freemarkerConfig = freemarkerConfig;
        this.javaMailSender = javaMailSender;
    }

    // Send welcome email using FreeMarker template
    public void sendWelcomeEmail(String toEmail, String subject, Map<String, String> model) {
        try {
            log.info("Preparing welcome email to: {}", toEmail);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);

            log.info("Loading email template");
            Template template = freemarkerConfig.getTemplate("welcome-email.ftl");

            StringWriter writer = new StringWriter();
            template.process(model, writer);
            String html = writer.toString();

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(html, true); // HTML content
            helper.setFrom(fromEmail);

            log.info("Sending welcome email to: {}", toEmail);
            javaMailSender.send(message);
            log.info("Welcome email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
