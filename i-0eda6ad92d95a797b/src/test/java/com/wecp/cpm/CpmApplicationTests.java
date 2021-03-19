package com.wecp.cpm;

import com.wecp.cpm.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CpmApplicationTests {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";

    @Autowired
    private MockMvc mockMvc;

    /**
     * Create entity for tests. Make sure to give different ids to each entity for
     * primary key reasons.
     */
    User createEntity() {
        User user = new User();
        user.setUsername(DEFAULT_NAME);
        return user;
    }

    @Test
    void createUserTest() throws Exception {
        // create user
        User user = createEntity();
        MvcResult resultActions = mockMvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(user))).andExpect(status().isOk()).andReturn();

        User createdUser = (User) TestUtil.convertJsonToObject(resultActions.getResponse().getContentAsString(), User.class);
        // Get the user and validate
        mockMvc.perform(get("/api/v1/user/{id}", createdUser.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(DEFAULT_NAME));
    }

    @Test
    void deleteUserTest() throws Exception {
        // create user
        User user = createEntity();
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(user))).andExpect(status().isOk()).andReturn();
        User createdUser = (User) TestUtil.convertJsonToObject(mvcResult.getResponse().getContentAsString(), User.class);

        // delete user
        mockMvc.perform(delete("/api/v1/user/{id}", createdUser.getId())).andExpect(status().isOk());

        // Get the user and validate delete
        mockMvc.perform(get("/api/v1/user/{id}", createdUser.getId())).andExpect(status().isNoContent());
    }

    @Test
    void getAllUserTest() throws Exception {
        try {
            // create user
            User user = createEntity();
            MvcResult mvcResult = mockMvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user))).andExpect(status().isOk()).andReturn();
            User createdUser = (User) TestUtil.convertJsonToObject(mvcResult.getResponse().getContentAsString(), User.class);
            User user2 = createEntity();
            MvcResult mvcResult1 = mockMvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(user2))).andExpect(status().isOk()).andReturn();
            User createdUser2 = (User) TestUtil.convertJsonToObject(mvcResult1.getResponse().getContentAsString(), User.class);

            mockMvc.perform(get("/api/v1/user")).andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].id").value(hasItem(createdUser.getId().intValue())))
                    .andExpect(jsonPath("$.[*].id").value(hasItem(createdUser2.getId().intValue())))
                    .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_NAME)));
        } catch (AssertionError e) {
            throw new AssertionError("Expecting the test to return a list with 2 user but the list is empty", e);
        }
    }
}
