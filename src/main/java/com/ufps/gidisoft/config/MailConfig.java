package com.ufps.gidisoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${spring.mail.password}")
    private String passwordFrom;
    @Value("${spring.mail.port}")
    private Long portFrom;
    @Value("${spring.mail.host}")
    private String hostFrom;



    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostFrom);
        mailSender.setPort(Math.toIntExact(portFrom));
        mailSender.setUsername(emailFrom);
        mailSender.setPassword(passwordFrom);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}

