package com.sysc4806app.server;

import com.sysc4806app.model.Review;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
    @MockBean
    private UserRepo userRepo;

    private static String url;

    @BeforeAll
    static void beforeAll() {
        url = "/product/1/review";
    }

    @Test
    public void controllerShouldReturnAddReviewForm() throws Exception {
        // searching for the heading "Add a Review" in the html returned
        mockMvc.perform(get("/product/1/review")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Add a Review")));
    }
    @WithMockUser(username = "joe")
    @Test
    public void testAddReview() throws Exception {
        // ensuring that post is redirected to correct url without any other errors
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .secure(false)
                .param("rating", "5")
                .param("text", "very good")
                .sessionAttr("review", new Review()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/1"));
    }

    @Test
    public void testAddReviewNoUser() throws Exception {
        // ensuring that post is redirected to correct url without any other errors
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .secure(false)
                .param("rating", "5")
                .param("text", "very good")
                .sessionAttr("review", new Review()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/1/review?nologin"));
    }

    @Test
    public void testFailAddReview() throws Exception {
        // ensuring that post fails because of form validation
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .secure(false)
                .param("rating", "42")
                .param("text", "")
                .sessionAttr("review", new Review()))
                .andExpect(status().isOk()) // field errors are considered ok
                .andExpect(model().attributeHasFieldErrors("review", "rating", "text"))
                .andExpect(view().name("addNewReviewForm"));
    }


}