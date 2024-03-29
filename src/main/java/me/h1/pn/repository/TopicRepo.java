package me.h1.pn.repository;

import me.h1.pn.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Integer> {

    Optional<Topic> findByName(String name);
}
