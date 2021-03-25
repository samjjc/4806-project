package com.sysc4806app.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ImportAutoConfiguration(TestSecurityConfig.class)
@WebMvcTest(value = ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductRepo productRepo;
    @MockBean
    private ReviewRepo reviewRepo;

    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);


    @Test
    public void controllerShouldReturnAddReviewForm() throws Exception {
        // searching for the heading "Add a Review" in the html returned
        mockMvc.perform(get("/product/1/review")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Add a Review")));
    }

    @Test
    public void testAddReview() throws Exception {
        String url = "/product/1/review";

        User user = new User("tester");
        Product prod = new Product("www.joeIsCool.com", "POMPOMS","you already know.", ProductType.CFE, ProductChain.TIM);
        Review review = new Review(5,"good", prod, user);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(review);

        // ensuring that post is redirected to correct url without any other errors
        mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).secure(false)
                .content(requestJson))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/1"));
    }


}