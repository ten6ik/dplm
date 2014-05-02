package bstu.dplm.model;

import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.context.xml")
public class HibernateMappingTest
{
    @Resource
    SessionFactory sessionFactory;

    @Test
    @Transactional
    public void testMapping()
    {
        Map classMetadata = sessionFactory.getAllClassMetadata();

        boolean hasErrors = false;
        StringBuilder errors = new StringBuilder();
        String className = "";

        for (Iterator i = classMetadata.values().iterator(); i.hasNext();)
        {
            try
            {
                final EntityPersister persister = (EntityPersister) i.next();
                className = persister.getClassMetadata().getEntityName();
                sessionFactory.getCurrentSession().createQuery("from " + className + " c").list();
            }
            catch (Exception e)
            {
                hasErrors = true;
                errors.append("Entity " + className + " : "
                        + (e.getCause() == null ? e.toString() : e.getCause().toString()) + "\n");
            }
        }

        assertThat(errors.toString(), hasErrors, is(false));
    }

}
