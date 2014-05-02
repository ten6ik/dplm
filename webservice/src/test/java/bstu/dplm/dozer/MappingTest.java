package bstu.dplm.dozer;

import edu.schema.bstu.dplm.datatypes.v1.Location;
import edu.schema.bstu.dplm.datatypes.v1.LocationProperties;
import org.dozer.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.context.xml")
public class MappingTest {

    @Resource
    Mapper mapper;

    @Test
    public void test()
    {
        Location location = new Location();
        location.setId(2l);
        LocationProperties properties = new LocationProperties();
        properties.setId(1l);
        location.getProperties().add(properties);

        bstu.dplm.model.game.Location jpa = mapper.map(location, bstu.dplm.model.game.Location.class);
        jpa.fixReferences();

        assertThat("Wrong conversion result", jpa.getProperties().iterator().next().getLocation()== null, is(false));
    }
}
