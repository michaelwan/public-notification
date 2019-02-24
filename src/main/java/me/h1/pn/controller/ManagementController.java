package me.h1.pn.controller;

import lombok.RequiredArgsConstructor;
import me.h1.pn.exception.ResourceNotFoundException;
import me.h1.pn.model.Topic;
import me.h1.pn.repository.LocationRepo;
import me.h1.pn.repository.TopicRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {

    private final TopicRepo topicRepo;
    private final LocationRepo locationRepo;

    @GetMapping("/topics")
    public Page<Topic> getTopics(Pageable pageable) {
        return topicRepo.findAll(pageable);
    }


    @PostMapping("/topic")
    public Topic createTopic(@Valid @RequestBody Topic topic) {
        return topicRepo.save(topic);
    }

    @PutMapping("/topic/{topicId}")
    public Topic updateTopic(@PathVariable Integer topicId,
                                   @Valid @RequestBody Topic topicRequest) {
        return topicRepo.findById(topicId)
                .map(topic -> {
                    topic.setName(topicRequest.getName());
                    return topicRepo.save(topic);
                }).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + topicId));
    }


    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer topicId) {
        return topicRepo.findById(topicId)
                .map(topic -> {
                    topicRepo.delete(topic);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id " + topicId));
    }
}
