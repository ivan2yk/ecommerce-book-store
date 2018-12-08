package com.acme.ecommerce.bookstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = {"test", "data"})
public class EcommerceBookStoreApplicationTests {

    @Test
    public void contextLoads() {
    }

}
