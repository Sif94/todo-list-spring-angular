package org.baouz.todolist.task;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.baouz.todolist.email.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskNotificationConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "notification.queue")
    public void processTaskNotification(Task task) throws MessagingException {
        String subject = "Task Due Soon: " + task.getTitle();
        String body = "Your task \"" + task.getTitle() + "\" is due in less than 24 hours.";
        emailService.sendEmail(task.getOwnerEmail(), subject, body);
    }
}
