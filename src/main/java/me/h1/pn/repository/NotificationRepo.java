package me.h1.pn.repository;

import me.h1.pn.model.Notification;
import me.h1.pn.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    List<Notification> findByTopic(Topic topic);
}
