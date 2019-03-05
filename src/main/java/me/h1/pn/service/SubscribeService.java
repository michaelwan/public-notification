package me.h1.pn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.h1.pn.exception.ResourceNotFoundException;
import me.h1.pn.model.Topic;
import me.h1.pn.repository.TopicRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscribeService {

    private final TopicRepo topicRepo;
    private final SNSService snsService;

    public void subscribe(Integer topicId, String email, String... locations) throws JsonProcessingException {
        Optional<Topic> topicOptional = topicRepo.findById(topicId);
        if(! topicOptional.isPresent()) {
            throw new ResourceNotFoundException("Topic with id [" + topicId + "] not found when trying to subscribe to it!");
        }
        snsService.createSubscription(topicOptional.get().getArn(), email, locations);
    }
}
