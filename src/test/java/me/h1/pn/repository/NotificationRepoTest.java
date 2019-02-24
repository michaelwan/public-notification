package me.h1.pn.repository;

import me.h1.pn.model.Location;
import me.h1.pn.model.Notificatoin;
import me.h1.pn.model.Topic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class NotificationRepoTest {

    private static final String TRAFFIC = "traffic";
    private static final String DARLING = "Darling";
    private static final String CONTENT = "3 cars accident on Tooronga Rd near High street.";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private NotificationRepo notificationRepo;

    private final Topic trafficTopic = new Topic(TRAFFIC);
    private final Location darlingLocation = new Location(DARLING);
    private final Notificatoin notificatoin1 = new Notificatoin(trafficTopic, darlingLocation, CONTENT);

    @Before
    public void setUp() throws Exception {
        testEntityManager.persistAndFlush(trafficTopic);
        testEntityManager.persistAndFlush(darlingLocation);
        testEntityManager.persistAndFlush(notificatoin1);
    }

    @After
    public void tearDown() throws Exception {
        testEntityManager.remove(notificatoin1);
        testEntityManager.remove(trafficTopic);
        testEntityManager.remove(darlingLocation);
    }

    @Test
    public void shouldGetNotificationByTopic() {
        List<Notificatoin> notificatoins = notificationRepo.findByTopic(trafficTopic);
        assertThat(notificatoins, notNullValue());
        assertThat(notificatoins.size(), equalTo(1));
        Notificatoin notificatoin = notificatoins.get(0);
        assertThat(notificatoin.getTopic().getName(), equalTo(TRAFFIC));
        assertThat(notificatoin.getLocation().getName(), equalTo(DARLING));
        assertThat(notificatoin.getContent(), equalTo(CONTENT));
    }
}