package me.h1.pn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.h1.pn.service.bean.FilterPolicy;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SNSService {

    public static final String EMAIL = "email";
    public static final String FILTER_POLICY = "FilterPolicy";
    public static final String STRING_DATA_TYPE = "String";
    private final SnsClient snsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static final String LOCATION = "location";

    public String createTopic(String topic) {
        CreateTopicRequest createTopicRequest = CreateTopicRequest.builder().name(topic).build();
        CreateTopicResponse response = snsClient.createTopic(createTopicRequest);
        log.debug("Created SNS topic [{}]", response);
        return response.topicArn();
    }

    public void deleteTopic(String arn) {
        DeleteTopicRequest deleteTopicRequest = DeleteTopicRequest.builder().topicArn(arn).build();
        DeleteTopicResponse response = snsClient.deleteTopic(deleteTopicRequest);
        log.debug("Delete SNS topic [{}]", response);
    }

    public void publishMessage(String topicArn, String location, String content) {
        Map<String, MessageAttributeValue> attributeValueMap = new HashMap<>();
        attributeValueMap.put(LOCATION, MessageAttributeValue.builder()
                .dataType(STRING_DATA_TYPE)
                .stringValue(location)
                .build());
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .message(content)
                .messageAttributes(attributeValueMap)
                .build();
        PublishResponse response = snsClient.publish(publishRequest);
        log.debug("Publish SNS message response [{}]", response);
    }

    public void createSubscription(String topicArn, String email, String... locations) throws JsonProcessingException {
        SubscribeRequest.Builder builder = SubscribeRequest.builder();
        if(! ArrayUtils.isEmpty(locations)) {
            Map<String, String> attributes = new HashMap<>();
            attributes.put(FILTER_POLICY, buildFilterPolicy(locations));
            builder = builder.attributes(attributes);
        }
        SubscribeRequest subscribeRequest = builder
                .topicArn(topicArn)
                .protocol(EMAIL)
                .endpoint(email)
                .build();
        SubscribeResponse response = snsClient.subscribe(subscribeRequest);
        log.debug("Create Subscription response [{}]", response);
    }

    private String buildFilterPolicy(String... locations) throws JsonProcessingException {
        FilterPolicy filterPolicy = new FilterPolicy(locations);
        return objectMapper.writeValueAsString(filterPolicy);
    }

}
