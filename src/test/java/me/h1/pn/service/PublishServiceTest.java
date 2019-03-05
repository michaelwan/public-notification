package me.h1.pn.service;

import me.h1.pn.exception.ResourceNotFoundException;
import me.h1.pn.model.Location;
import me.h1.pn.model.Notification;
import me.h1.pn.model.Topic;
import me.h1.pn.repository.LocationRepo;
import me.h1.pn.repository.NotificationRepo;
import me.h1.pn.repository.TopicRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class PublishServiceTest {

    public static final String TEST_LOCATOIN = "testLocatoin";
    public static final String TEST_CONTENT = "testContent";
    public static final String ARN_AWS_TEST = "arn:aws:test";
    public static final String FIRE = "fire";
    public static final String MULGRAVE = "Mulgrave";
    public static final String TEST_NOTIFICATION = "Test Notification!";
    @Mock
    private TopicRepo topicRepoMock;
    @Mock
    private LocationRepo locationRepoMock;
    @Mock
    private NotificationRepo notificationRepoMock;
    @Mock
    private Pageable pageable;
    @Mock
    private SNSService snsService;

    private PublishService publishService;

    private Topic topic;
    private Location location;
    private Notification notification;

    @Before
    public void setup() {
        publishService = new PublishService(topicRepoMock, locationRepoMock, notificationRepoMock, snsService);
        topic = new Topic();
        topic.setId(1);
        topic.setName(FIRE);
        topic.setArn(ARN_AWS_TEST);
        location = new Location();
        location.setId(1);
        location.setName(MULGRAVE);
        notification = new Notification();
        notification.setId(1);
        notification.setTopic(topic);
        notification.setLocation(location);
        notification.setContent(TEST_NOTIFICATION);

    }

    @Test
    public void shouldGetTopics() {
        publishService.getTopics(pageable);
        verify(topicRepoMock).findAll(eq(pageable));
    }

    @Test
    public void shouldCreateNotificationWithExistingLocation() {
        when(topicRepoMock.findById(1)).thenReturn(Optional.of(topic));
        when(locationRepoMock.findByName(TEST_LOCATOIN)).thenReturn(Optional.of(location));
        when(notificationRepoMock.save(any())).thenReturn(notification);
        Notification notificationResult = publishService.createNotification(1, TEST_LOCATOIN, TEST_CONTENT);
        assertThat(notificationResult, equalTo(notification));
        verify(topicRepoMock).findById(1);
        verify(locationRepoMock).findByName(TEST_LOCATOIN);
        verify(locationRepoMock, times(0)).save(any());
        verify(notificationRepoMock).save(any());
        verify(snsService).publishMessage(ARN_AWS_TEST, TEST_LOCATOIN, TEST_CONTENT);
    }

    @Test
    public void shouldCreateLocationAndNotificationWhenLocationNotExist() {
        when(topicRepoMock.findById(1)).thenReturn(Optional.of(topic));
        when(locationRepoMock.findByName(TEST_LOCATOIN)).thenReturn(Optional.empty());
        when(locationRepoMock.save(any())).thenReturn(location);
        when(notificationRepoMock.save(any())).thenReturn(notification);
        Notification notificationResult = publishService.createNotification(1, TEST_LOCATOIN, TEST_CONTENT);
        assertThat(notificationResult, equalTo(notification));
        verify(topicRepoMock).findById(1);
        verify(locationRepoMock).findByName(TEST_LOCATOIN);
        verify(locationRepoMock).save(any());
        verify(notificationRepoMock).save(any());
        verify(snsService).publishMessage(ARN_AWS_TEST, TEST_LOCATOIN, TEST_CONTENT);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenTopicNotExist() {
        when(topicRepoMock.findById(1)).thenReturn(Optional.empty());
        Notification notificationResult = publishService.createNotification(1, TEST_LOCATOIN, TEST_CONTENT);
        verify(topicRepoMock).findById(1);
        verifyZeroInteractions(locationRepoMock);
        verifyZeroInteractions(notificationRepoMock);
        verifyZeroInteractions(snsService);
    }
}