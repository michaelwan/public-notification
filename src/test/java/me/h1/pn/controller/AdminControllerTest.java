package me.h1.pn.controller;

import me.h1.pn.model.Topic;
import me.h1.pn.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    public static final String TOPIC = "topic";
    @Mock
    private AdminService adminService;

    private AdminController adminController;

    @Before
    public void setup() {
        adminController = new AdminController(adminService);
    }

    @Test
    public void shouldCreateTopic() {
        Topic topic = Topic.builder().name(TOPIC).arn("arn").build();
        when(adminService.createTopic(TOPIC)).thenReturn(topic);
        adminController.createTopic(TOPIC);
        verify(adminService).createTopic(eq(TOPIC));
    }

    @Test
    public void shouldDeleteTopic() {
        adminController.deleteTopic(1);
        verify(adminService).deleteTopic(eq(1));
    }
}