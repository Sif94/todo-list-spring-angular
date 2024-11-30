package org.baouz.todolist.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;



    @Async
    public void sendEmail(String to, String subject, String body) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );


        helper.setFrom("sifeddine@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        //String template = templateEngine.process(templateName, context);

        helper.setText(body, true);

        mailSender.send(mimeMessage);
    }
}
