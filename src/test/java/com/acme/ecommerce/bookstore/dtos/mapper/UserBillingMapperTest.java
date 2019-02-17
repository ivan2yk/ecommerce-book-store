package com.acme.ecommerce.bookstore.dtos.mapper;

import com.acme.ecommerce.bookstore.dtos.UserBillingDTO;
import com.acme.ecommerce.bookstore.entities.UserBilling;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ivan on 12/02/2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test", "data"})
public class UserBillingMapperTest {

    @Autowired
    private UserBillingMapper userBillingMapper;

    @Test
    public void entityToDtoTest() throws Exception {
        UserBilling userBilling = new UserBilling();
        userBilling.setId(10L);
        userBilling.setUserBillingCity("Lima");
        userBilling.setUserBillingCountry("PE");
        userBilling.setUserBillingZipcode("Lima 13");

        UserBillingDTO userBillingDTO = userBillingMapper.toDto(userBilling);

        assertNotNull(userBillingDTO);
        assertEquals(userBilling.getId(), userBillingDTO.getId());
        assertEquals(userBilling.getUserBillingCity(), userBillingDTO.getUserBillingCity());
    }

    @Test
    public void dtoToEntityTest() throws Exception {
        UserBillingDTO userBillingDTO = new UserBillingDTO();
        userBillingDTO.setId(10L);
        userBillingDTO.setUserBillingCity("Barranca");
        userBillingDTO.setUserBillingCountry("VE");
        userBillingDTO.setUserBillingZipcode("Ven 10");

        UserBilling userBilling = userBillingMapper.toEntity(userBillingDTO);

        assertNotNull(userBilling);
        assertEquals(userBillingDTO.getUserBillingCity(), userBilling.getUserBillingCity());
    }

}