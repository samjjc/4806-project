package com.sysc4806app.server;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.ResourceAccessException;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ImportAutoConfiguration(TestSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void productPageShouldProperlyShowEmptyMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/productlist",
                String.class)).contains("No Products Available ");
    }

    @Test
    public void productPageShouldProperlyShowProducts() {

        productRepo.save(new Product("http://www.soup.com", "tacos", "yum tum yum tum", ProductType.BGR, ProductChain.TIM));
        productRepo.save(new Product("http://www.soup.com", "salsa", "yum tum yum tum", ProductType.BGR, ProductChain.TIM));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/productlist",
                String.class))
                .doesNotContain("No Products Available ")
                .contains("http://www.soup.com")
                .contains("tacos")
                .contains("yum tum")
                .contains("Burgers")
                .contains("Tim Horton")
                .contains("salsa");
        productRepo.deleteAll();
    }

    @Test
    public void productPageShouldShowReviewByUser() {
        Product product = productRepo.save(new Product("http://www.soup.com", "tacos", "yum tum yum tum", ProductType.BGR, ProductChain.TIM));
        User user=userRepo.save(new User("joe","123"));
        reviewRepo.save(new Review(5,"sucked",product,user));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/product/"+product.getId(),
                String.class))
                .doesNotContain("Have you tried the tacos?")
                .contains("http://www.soup.com")
                .contains("yum tum yum tum")
                .contains("joe")
                .contains("sucked");
        reviewRepo.deleteAll();
        productRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void testEmptyFormValidation() {
        Product emptyProduct = new Product();
        HttpEntity<String> request = new HttpEntity<>(emptyProduct.toString());
        assertThat(restTemplate.withBasicAuth("testAdmin", "testPass")
                .postForObject("http://localhost:" + port + "/product", request, String.class))
                .contains("must not be blank")
                .contains("you must select a Product Type")
                .contains("you must select a Product Chain");
    }

    @Test
    public void testSorting() {
        productRepo.save(new Product("http://www.soup.com", "tacos", "yum tum yum tum", ProductType.BGR, ProductChain.TIM));
        Product product = productRepo.save(new Product("http://www.soup.com", "salsa", "yum tum yum tum", ProductType.BGR, ProductChain.TIM));
        User user = userRepo.save(new User("name", "pass"));
        reviewRepo.save(new Review(5, "good", product, user));

        Pattern tacosFirst = Pattern.compile(".*tacos.*salsa.*", Pattern.DOTALL);
        Pattern salsaFirst = Pattern.compile(".*salsa.*tacos.*", Pattern.DOTALL);

        String response = restTemplate.getForObject("http://localhost:" + port +
                "/productlist?sort=averageRating,asc", String.class);
        assertThat(tacosFirst.matcher(response).matches()).isTrue();

        response = restTemplate.getForObject("http://localhost:" + port +
                "/productlist?sort=averageRating,desc", String.class);
        assertThat(salsaFirst.matcher(response).matches()).isTrue();

        reviewRepo.deleteAll();
        productRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void testFiltering() {
        productRepo.save(new Product("http://www.soup.com", "tacos", "yum tum yum tum", ProductType.CFE, ProductChain.TIM));
        productRepo.save(new Product("http://www.soup.com", "salsa", "yum tum yum tum", ProductType.BGR, ProductChain.AW));
        productRepo.save(new Product("http://www.soup.com", "goodfood", "yum tum yum tum", ProductType.BGR, ProductChain.AW));
        assertThat(restTemplate.getForObject("http://localhost:" + port +
                "/productlist?type=coffee&chain=timmies",String.class))
                .contains("tacos")
                .doesNotContain("goodfood")
                .doesNotContain("salsa");
        assertThat(restTemplate.getForObject("http://localhost:" + port +
                "/productlist?type=burgers&chain=aw",String.class))
                .contains("salsa")
                .contains("goodfood")
                .doesNotContain("tacos");
        assertThat(restTemplate.getForObject("http://localhost:" + port +
                "/productlist?name=a",String.class))
                .contains("salsa")
                .contains("tacos")
                .doesNotContain("goodfood");
        assertThat(restTemplate.getForObject("http://localhost:" + port +
                "/productlist?name=SALSA",String.class))
                .contains("salsa")
                .doesNotContain("tacos")
                .doesNotContain("goodfood");
        assertThat(restTemplate.getForObject("http://localhost:" + port +
                "/productlist?name=o&type=burgers&chain=aw",String.class))
                .contains("goodfood")
                .doesNotContain("tacos")
                .doesNotContain("salsa");
        productRepo.deleteAll();
    }

    @Test
    public void testFailRoleBasedAuthorization() {
        Product emptyProduct = new Product();
        HttpEntity<String> request = new HttpEntity<>(emptyProduct.toString());

        Exception exception = assertThrows(ResourceAccessException.class, () ->
                restTemplate.postForObject("http://localhost:" + port + "/product", request, String.class));

        String expectedMessage = "I/O error on POST request";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
