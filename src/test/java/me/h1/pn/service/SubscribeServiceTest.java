package me.h1.pn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.h1.pn.exception.ResourceNotFoundException;
import me.h1.pn.model.Topic;
import me.h1.pn.repository.TopicRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscribeServiceTest {

    public static final int TOPIC_ID = 1;
    public static final String TEST_TEST_COM = "test@test.com";
    public static final String ASHWOOD = "Ashwood";
    public static final String RICHMOND = "Richmond";
    public static final String ARN_AWS_SNS_TEST = "arn:aws:sns:::test";
    public static final String FIRE = "fire";
    @Mock
    private TopicRepo topicRepo;
    @Mock
    private SNSService snsService;

    private SubscribeService subscribeService;

    @Before
    public void setup() {
        subscribeService = new SubscribeService(topicRepo, snsService);
    }

    @Test
    public void shouldSubscribe() throws JsonProcessingException {
        when(topicRepo.findById(TOPIC_ID)).thenReturn(Optional.of(Topic.builder().arn(ARN_AWS_SNS_TEST).name(FIRE).build()));
        subscribeService.subscribe(TOPIC_ID, TEST_TEST_COM, ASHWOOD, RICHMOND);
        verify(topicRepo).findById(eq(TOPIC_ID));
        verify(snsService).createSubscription(ARN_AWS_SNS_TEST, TEST_TEST_COM, ASHWOOD, RICHMOND);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldNotSubscirbeWhenTopicNotFound() throws JsonProcessingException {
        when(topicRepo.findById(TOPIC_ID)).thenReturn(Optional.empty());
        subscribeService.subscribe(TOPIC_ID, TEST_TEST_COM, ASHWOOD, RICHMOND);
        verify(topicRepo).findById(eq(TOPIC_ID));
        verifyZeroInteractions(snsService);
    }
}