package me.h1.pn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.h1.pn.model.Topic;
import me.h1.pn.repository.TopicRepo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.logstash.logback.marker.Markers.appendEntries;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final TopicRepo topicRepo;
    private final SNSService snsService;

    public Topic createTopic(Topic topic) {

        Map<String, String> markers = new HashMap<>();
        markers.put("event", "create topic");
        try {
            markers.put("result", "success");
            String arn = snsService.createTopic(topic.getName());
            topic.setArn(arn);
            return topicRepo.save(topic);
        } catch (RuntimeException e) {
            markers.put("result", "failure");
            markers.put("message", ExceptionUtils.getRootCauseMessage(e));
            throw e;
        } finally {
            log.info(appendEntries(markers), "completed.");
        }
    }

    public void deleteTopic(Integer topicId) {
        Map<String, String> markers = new HashMap<>();
        markers.put("event", "delete topic");
        markers.put("data", "topic id: " + topicId);
        Optional<Topic> topic = topicRepo.findById(topicId);
        if(topic.isPresent()) {
            snsService.deleteTopic(topic.get().getArn());
            topicRepo.delete(topic.get());
            markers.put("result", "success");
        } else {
            markers.put("result", "ignored");
        }
        log.info(appendEntries(markers), "completed");
    }

}
