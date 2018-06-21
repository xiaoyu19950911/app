package com.sharding.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppApplicationTests {

    @Test
    public void contextLoads() {
        Long s=214412268912246783L;
        System.out.println(s%10);
        System.out.println(s/10);
        Long s1=s>>1;
        System.out.println(s1);
    }

}
