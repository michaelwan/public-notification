package me.h1.pn.repository;

import me.h1.pn.model.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicRepoTest {

    public static final String FIRE = "fire";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TopicRepo topicRepo;

    @Before
    public void setup() {
        Topic fireTopic = new Topic();
        fireTopic.setName(FIRE);
        testEntityManager.persist(fireTopic);
    }

    @Test
    public void shouldGetFireTopic() {
        Topic topic = topicRepo.findByName(FIRE);
        assertThat(topic, notNullValue());
        assertThat(topic.getName(), equals(FIRE));
    }
}