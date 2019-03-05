package me.h1.pn.controller.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubscribeRequest {
    @JsonProperty(required = true)
    private Integer topicId;
    @JsonProperty(required = true)
    private String email;
    @JsonProperty(required = false)
    private String[] locations;
}
