package me.h1.pn.controller;

import lombok.RequiredArgsConstructor;
import me.h1.pn.model.Topic;
import me.h1.pn.service.AdminService;
import me.h1.pn.service.PublishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class AdminController {

    private final AdminService adminService;
    private final PublishService publishService;

    @GetMapping("/topics")
    public Page<Topic> getTopics(Pageable pageable) {
        return publishService.getTopics(pageable);
    }

    @PostMapping("/topic")
    public Topic createTopic(@Valid @RequestBody Topic topic) {
        return adminService.createTopic(topic);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer topicId) {
        adminService.deleteTopic(topicId);
        return ResponseEntity.ok().build();
    }
}
