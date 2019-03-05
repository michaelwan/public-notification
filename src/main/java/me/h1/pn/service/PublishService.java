package me.h1.pn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.h1.pn.exception.ResourceNotFoundException;
import me.h1.pn.model.Location;
import me.h1.pn.model.Notification;
import me.h1.pn.model.Topic;
import me.h1.pn.repository.LocationRepo;
import me.h1.pn.repository.NotificationRepo;
import me.h1.pn.repository.TopicRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublishService {

    private final TopicRepo topicRepo;
    private final LocationRepo locationRepo;
    private final NotificationRepo notificationRepo;
    private final SNSService snsService;

    public Page<Topic> getTopics(Pageable pageable) {
        return topicRepo.findAll(pageable);
    }

    public Notification createNotification(Integer topicId, String locationName, String content) {
        Optional<Topic> topicOptional = topicRepo.findById(topicId);
        if(!topicOptional.isPresent()) {
            throw new ResourceNotFoundException("Topic with id [" + topicId + "] not found!");
        }
        Optional<Location> locationOptional = locationRepo.findByName(locationName);
        if(!locationOptional.isPresent()) {
            Location location = locationRepo.save(new Location(locationName));
            locationOptional = Optional.of(location);
        }
        snsService.publishMessage(topicOptional.get().getArn(), locationName, content);
        return notificationRepo.save(new Notification(topicOptional.get(), locationOptional.get(), content));
    }
}
