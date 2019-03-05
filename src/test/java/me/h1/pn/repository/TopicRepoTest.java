package me.h1.pn.repository;

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

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class TopicRepoTest {

    public static final String FIRE = "fire";
    public static final String ARN_FIRE = "arn:fire";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TopicRepo topicRepo;

    private final Topic fireTopic = new Topic();

    @Before
    public void setup() {
        fireTopic.setName(FIRE);
        fireTopic.setArn(ARN_FIRE);
        testEntityManager.persistAndFlush(fireTopic);
    }

    @After
    public void cleanup() {
        testEntityManager.remove(fireTopic);
    }

    @Test
    public void shouldGetFireTopic() {
        Optional<Topic> topic = topicRepo.findByName(FIRE);
        assertThat(topic, notNullValue());
        assertThat(topic.isPresent(), equalTo(true));
        assertThat(topic.get().getName(), equalTo(FIRE));
        assertThat(topic.get().getArn(), equalTo(ARN_FIRE));
    }
}