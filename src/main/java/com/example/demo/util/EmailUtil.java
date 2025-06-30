package com.example.demo.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Component
public class EmailUtil {
  private final JavaMailSender javaMailSender;

  public void sendMail(String from, String to, String title, String text) {
    MimeMessage message = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(title);
      helper.setText(text, true);
      javaMailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
