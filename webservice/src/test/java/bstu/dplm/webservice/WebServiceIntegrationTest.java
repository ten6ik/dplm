package bstu.dplm.webservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.context.xml")
public class WebServiceIntegrationTest
{
    @Test
    public void run() throws IOException
    {
        System.out.println("Started..");
        System.in.read();
    }
}
