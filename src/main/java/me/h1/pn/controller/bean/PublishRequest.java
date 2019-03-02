package me.h1.pn.controller.bean;

import lombok.Data;

@Data
public class PublishRequest {

    private Integer topicId;
    private String locationName;
    private String content;
}
