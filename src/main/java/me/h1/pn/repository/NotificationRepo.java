package me.h1.pn.repository;

import me.h1.pn.model.Notificatoin;
import me.h1.pn.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notificatoin, Integer> {
    List<Notificatoin> findByTopic(Topic topic);
}
