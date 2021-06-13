package in.gvatreya.communications.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContactControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getContactForThatDoesNotExist() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contacts/i_dont_exist"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist())
        ;
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/sql/data-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/sql/data-teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void getContact() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contacts/test_uuid_1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").value("test_uuid_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test_name_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test_name_1@test-email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telegramHandle").value("test_name_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.twitterId").value("@testName1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("+919988776655"))
        ;
    }

}