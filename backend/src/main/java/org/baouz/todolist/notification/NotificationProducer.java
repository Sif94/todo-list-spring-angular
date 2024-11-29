package org.baouz.todolist.notification;

import org.baouz.todolist.task.Task;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(Task task) {
        rabbitTemplate.convertAndSend("notification.exchange", "notification.key", task);
        System.out.println("Notification sent: " + task);
    }
}
