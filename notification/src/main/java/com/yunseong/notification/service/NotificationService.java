package com.yunseong.notification.service;

import com.yunseong.notification.config.MailSenderConfiguration;
import com.yunseong.notification.domain.Notification;
import com.yunseong.notification.domain.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;
    private final MailSenderConfiguration mailSenderConfiguration;

    @Transactional
    public void createNotification(String username, String subject, String content) {
        this.notificationRepository.save(new Notification(username, subject, content));
    }

    @Async("mail")
    public void sendMail(String username, String subject, String content) {
        try {
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(mailSenderConfiguration.getUsername() + "@naver.com");
            helper.setTo(username);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public Page<Notification> findByUsername(String username, Pageable pageable) {
        return this.notificationRepository.findByUsername(username, pageable);
    }

    @Transactional
    public Notification findById(long id, String username) {
        Notification notification = this.notificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + "번호를 가진 Notification 엔티티가 존재하지 않습니다."));
        if(!notification.getUsername().equals(username)) throw new NotMatchedUsernameException("해당 메일은 존재하지 않습니다.");
        if(!notification.isRead()) {
            notification.read();
        }
        return notification;
    }
}
