package me.h1.pn.controller.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PublishRequest {

    @JsonProperty(required = true)
    private Integer topicId;
    @JsonProperty(required = true)
    private String locationName;
    @JsonProperty(required = true)
    private String content;
}
