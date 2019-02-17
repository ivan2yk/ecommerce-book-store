package com.acme.ecommerce.bookstore.services;

import com.acme.ecommerce.bookstore.entities.UserAccount;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Ivan on 17/02/2019.
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "data"})
public class EmailBuilderServiceTest {

    @Autowired
    private EmailBuilderService emailBuilderService;

    private UserAccount userAccount = UserAccount.builder()
            .userName("ivan2yk")
            .firstName("Ivan")
            .email("leivagarcia18@gmail.com")
            .build();

    @Test
    public void constructResetTokenEmail() throws Exception {
        emailBuilderService.constructResetTokenEmail(userAccount, "asjdjw213sasdasd***", "https://taringa.net", "128731237");
    }

    @Test
    public void constructResetPasswordTokenEmail() throws Exception {
        emailBuilderService.constructResetPasswordTokenEmail(userAccount, "youbadass123123&%&&", "https://taring.net", "123123123123");
    }

}