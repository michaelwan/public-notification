package me.h1.pn.service;

import me.h1.pn.model.Topic;
import me.h1.pn.repository.TopicRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    public static final String TOPIC = "topic";
    public static final String ARN = "arn";
    @Mock
    private TopicRepo topicRepoMock;
    @Mock
    private SNSService snsServiceMock;

    private AdminService adminService;

    @Before
    public void setup() {
        adminService = new AdminService(topicRepoMock, snsServiceMock);
    }

    @Test
    public void createTopic() {
        adminService.createTopic(TOPIC);
        verify(topicRepoMock).save(argThat((Topic topic) -> topic.getName() == TOPIC));
        verify(snsServiceMock).createTopic(eq(TOPIC));
    }

    @Test
    public void deleteTopic() {
        Topic topic = new Topic();
        topic.setId(1);
        topic.setArn(ARN);
        when(topicRepoMock.findById(eq(1))).thenReturn(Optional.of(topic));
        adminService.deleteTopic(topic.getId());
        verify(topicRepoMock).findById(eq(1));
        verify(topicRepoMock).delete(eq(topic));
        verify(snsServiceMock).deleteTopic(eq(ARN));
    }
}