package me.h1.pn.controller;

import lombok.RequiredArgsConstructor;
import me.h1.pn.controller.bean.PublishRequest;
import me.h1.pn.model.Notification;
import me.h1.pn.model.Topic;
import me.h1.pn.service.PublishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publish")
public class PublishController {

    private final PublishService publishService;

    @GetMapping("/topics")
    public Page<Topic> getTopics(Pageable pageable) {
        return publishService.getTopics(pageable);
    }

    @PostMapping("/notification")
    public Notification createNotification(@Valid @RequestBody PublishRequest publishRequest) {
        return publishService.createNotification(publishRequest.getTopicId(), publishRequest.getLocationName(), publishRequest.getContent());
    }
}
