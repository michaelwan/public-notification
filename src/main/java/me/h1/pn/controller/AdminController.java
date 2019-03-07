package me.h1.pn.controller;

import lombok.RequiredArgsConstructor;
import me.h1.pn.model.Topic;
import me.h1.pn.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class AdminController {

    private final AdminService adminService;
    @PostMapping("/topic")
    public Topic createTopic(@Valid @RequestBody String topicName) {
        return adminService.createTopic(topicName);
    }

    @DeleteMapping("/topic/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Integer topicId) {
        adminService.deleteTopic(topicId);
        return ResponseEntity.ok().build();
    }
}
