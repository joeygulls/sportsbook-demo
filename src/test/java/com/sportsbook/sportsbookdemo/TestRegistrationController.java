package com.sportsbook.sportsbookdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsbook.sportsbookdemo.entity.Registration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = SportsbookDemoApplication.class)
public class TestRegistrationController {

    @Autowired
            RegistrationRepository registrationRepository;
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void addRegistrationTest() throws Exception {

        Registration registration = new Registration();
        registration.setId(Long.valueOf(1000));
        registration.setEmail("testuser@gmail.com");
        registration.setPhone("6368675309");
        registration.setSsn("222334444");
        registration.setFirstName("Gary");
        registration.setLastName("Gambler");
        registration.setUsername("thegambler");
        registration.setPassword("foldem");


        mockMvc.perform(post("/sb-api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(registration)))
                .andDo(document("registration-post", preprocessRequest(prettyPrint())))
                .andExpect(status().isOk())
                .andReturn();


        String phone = "6368675309";
        MvcResult result = mockMvc.perform(get("/sb-api/v1/registration/{phone}", phone)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString("")))
                .andDo(document("registration-get", preprocessRequest(prettyPrint())))
                .andExpect(status().isOk())
                .andReturn();
        Registration registered = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        log.info(registered.toString());

        Assertions.assertEquals("6368675309", registered.getPhone());



        MvcResult loginResult = mockMvc.perform(get("/sb-api/v1/login", phone)
                        .param("username", "thegambler")
                        .param("password", "foldem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString("")))
                .andDo(document("login-get", preprocessRequest(prettyPrint())))
                .andExpect(status().isOk())
                .andReturn();
        Registration loginOk = objectMapper.readValue(loginResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertEquals("6368675309", loginOk.getPhone());


        MvcResult loginFail = mockMvc.perform(get("/sb-api/v1/login", phone)
                        .param("username", "badhacker")
                        .param("password", "secret")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString("")))
                .andDo(document("login-get", preprocessRequest(prettyPrint())))
                .andExpect(status().isNotFound())
                .andReturn();


        //database cleanup
        registrationRepository.delete(registered);

    }

}
