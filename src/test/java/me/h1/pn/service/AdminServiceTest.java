package me.h1.pn.service;

import me.h1.pn.model.Topic;
import me.h1.pn.repository.TopicRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    @Mock
    private TopicRepo topicRepoMock;
    @Mock
    private SNSService snsService;

    private AdminService adminService;

    @Before
    public void setup() {
        adminService = new AdminService(topicRepoMock, snsService);
    }

    @Test
    public void createTopic() {
        Topic topic = new Topic();
        adminService.createTopic(topic);
        verify(topicRepoMock).save(topic);
    }

    @Test
    public void deleteTopic() {
        Topic topic = new Topic();
        topic.setId(1);
        when(topicRepoMock.findById(eq(1))).thenReturn(Optional.of(topic));
        adminService.deleteTopic(topic.getId());
        verify(topicRepoMock).findById(eq(1));
        verify(topicRepoMock).delete(eq(topic));
    }
}