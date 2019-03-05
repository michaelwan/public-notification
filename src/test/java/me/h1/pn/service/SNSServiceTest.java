package me.h1.pn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.h1.pn.service.bean.FilterPolicy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SNSServiceTest {

    public static final String ARN_AWS_TEST = "arn:aws:test";
    public static final String MULGRAVE = "Mulgrave";
    public static final String TEST_MESSAGE = "test message";
    public static final String LOCATION = "location";
    public static final String TEST_TEST_COM = "test@test.com";
    public static final String SPRINGVALE = "Springvale";
    public static final String EMAIL = "email";
    public static final String FILTER_POLICY = "FilterPolicy";
    public static final String STRING = "String";
    @Mock
    private SnsClient snsClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    private SNSService snsService;

    @Before
    public void setup() {
        snsService = new SNSService(snsClient);
    }

    @Test
    public void shouldCreateTopic() {
        CreateTopicResponse createTopicResponse = CreateTopicResponse.builder().topicArn(ARN_AWS_TEST).build();
        when(snsClient.createTopic(any(CreateTopicRequest.class))).thenReturn(createTopicResponse);
        String arnOfCreatedTopic = snsService.createTopic("test");
        assertThat(arnOfCreatedTopic, equalTo(ARN_AWS_TEST));
        verify(snsClient).createTopic(any(CreateTopicRequest.class));
    }

    @Test
    public void shouldDeleteTopic() {
        snsService.deleteTopic(ARN_AWS_TEST);
        verify(snsClient).deleteTopic(any(DeleteTopicRequest.class));
    }

    @Test
    public void shouldPublishMessage() {
        MessageAttributeValue messageAttributeValue = MessageAttributeValue.builder().dataType(STRING).stringValue(MULGRAVE).build();
        Map<String, MessageAttributeValue> attributeValueMap = new HashMap<>();
        attributeValueMap.put(LOCATION, messageAttributeValue);
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(ARN_AWS_TEST)
                .messageAttributes(attributeValueMap)
                .message(TEST_MESSAGE)
                .build();
        snsService.publishMessage(ARN_AWS_TEST, MULGRAVE, TEST_MESSAGE);
        verify(snsClient).publish(eq(publishRequest));
    }

    @Test
    public void shouldCreateSubscription() throws JsonProcessingException {
        FilterPolicy filterPolicy = new FilterPolicy(new String[]{MULGRAVE, SPRINGVALE});
        Map<String, String> attributes = new HashMap<>();
        attributes.put(FILTER_POLICY, objectMapper.writeValueAsString(filterPolicy));
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(ARN_AWS_TEST)
                .protocol(EMAIL)
                .endpoint(TEST_TEST_COM)
                .attributes(attributes)
                .build();
        snsService.createSubscription(ARN_AWS_TEST, TEST_TEST_COM, MULGRAVE, SPRINGVALE);
        verify(snsClient).subscribe(eq(subscribeRequest));
    }

    @Test
    public void shouldCreateSubscriptionWithNoLocation() throws JsonProcessingException {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(ARN_AWS_TEST)
                .protocol(EMAIL)
                .endpoint(TEST_TEST_COM)
                .build();
        snsService.createSubscription(ARN_AWS_TEST, TEST_TEST_COM);
        verify(snsClient).subscribe(eq(subscribeRequest));
    }

}