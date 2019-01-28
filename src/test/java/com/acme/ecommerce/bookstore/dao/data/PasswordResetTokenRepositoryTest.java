package com.acme.ecommerce.bookstore.dao.data;

import com.acme.ecommerce.bookstore.dao.PasswordResetTokenDAO;
import com.acme.ecommerce.bookstore.dao.UserAccountDAO;
import com.acme.ecommerce.bookstore.entities.UserAccount;
import com.acme.ecommerce.bookstore.entities.security.PasswordResetToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by Ivan on 5/12/2018.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(value = {"test", "data"})
public class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenDAO passwordResetTokenDAO;

    @Autowired
    private UserAccountDAO userAccountDAO;

    private static final String TOKEN_1 = "123";
    private static final String TOKEN_2 = "987";
    private UserAccount userAccount;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp() throws Exception {
        userAccount = UserAccount.builder()
                .userName("ivan2yk")
                .password("pass").build();
        this.userAccountDAO.save(userAccount);

        PasswordResetToken passwordResetToken = new PasswordResetToken(TOKEN_1, userAccount, LocalDateTime.of(2018, 12, 1, 13, 0));
        this.passwordResetTokenDAO.save(passwordResetToken);
        passwordResetToken = new PasswordResetToken(TOKEN_2, userAccount, LocalDateTime.of(2018, 12, 10, 13, 0));
        this.passwordResetTokenDAO.save(passwordResetToken);
    }

    @After
    public void tearDown() throws Exception {
        this.passwordResetTokenDAO.deleteAll();
        this.userAccountDAO.deleteAll();
    }

    @Test
    public void whenFindByToken_thenOk() throws Exception {
        Optional<PasswordResetToken> passwordResetTokenOptional = this.passwordResetTokenDAO.findByToken(TOKEN_1);

        assertTrue(passwordResetTokenOptional.isPresent());
        assertEquals(TOKEN_1, passwordResetTokenOptional.get().getToken());
    }

    @Test
    @Ignore
    public void whenFindByUserAccount_thenOk() throws Exception {
        PasswordResetToken passwordToken = this.passwordResetTokenDAO.findByUserAccount(userAccount);

        assertNotNull(passwordToken);
        assertEquals(TOKEN_1, passwordToken.getToken());
    }

    @Test
    public void findAllByExpiryDateTimeLessThan() throws Exception {
        Stream<PasswordResetToken> expiryDateTimeLessThan = this.passwordResetTokenDAO.findAllByExpiryDateTimeLessThan(LocalDateTime.of(2018, 12, 05, 0, 0));

        assertEquals(1, expiryDateTimeLessThan.count());
    }

    @Test
    public void deleteAllExpiredSince() throws Exception {

    }

}