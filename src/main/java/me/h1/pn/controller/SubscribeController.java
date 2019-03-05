package me.h1.pn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.h1.pn.controller.bean.SubscribeRequest;
import me.h1.pn.service.SubscribeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribe")
@Slf4j
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("topic")
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscribeRequest subscribeRequest) {
        try {
            subscribeService.subscribe(subscribeRequest.getTopicId(), subscribeRequest.getEmail(), subscribeRequest.getLocations());
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            log.error("Internal error occured when processing subscribe request [{}]", subscribeRequest, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
