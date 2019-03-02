package me.h1.pn.controller;

import lombok.RequiredArgsConstructor;
import me.h1.pn.controller.bean.PublishRequest;
import me.h1.pn.model.Notification;
import me.h1.pn.service.PublishService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publish")
public class PublishController {

    private final PublishService publishService;

    @PostMapping("/notification")
    public Notification createNotification(@Valid @RequestBody PublishRequest publishRequest) {
        return publishService.createNotification(publishRequest.getTopicId(), publishRequest.getLocationName(), publishRequest.getContent());
    }
}
