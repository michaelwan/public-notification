package me.h1.pn.repository;

import me.h1.pn.model.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class LocationRepoTest {

    private static final String MULGRAVE = "Mulgrave";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private LocationRepo locationRepo;

    private final Location mulgraveLocation = new Location();

    @Before
    public void setUp() throws Exception {
        mulgraveLocation.setName(MULGRAVE);
        testEntityManager.persistAndFlush(mulgraveLocation);
    }

    @After
    public void tearDown() throws Exception {
        testEntityManager.remove(mulgraveLocation);
    }

    @Test
    public void shouldGetByName() {
        Location location = locationRepo.findByName(MULGRAVE);
        assertThat(location, notNullValue());
        assertThat(location.getName(), equalTo(MULGRAVE));
    }
}