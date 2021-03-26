package com.sysc4806app.server;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.ProductChain;
import com.sysc4806app.model.ProductType;
import com.sysc4806app.repos.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ImportAutoConfiguration(TestSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepo productRepo;

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
    public void testEmptyFormValidation() {
        Product emptyProduct = new Product();
        HttpEntity<String> request = new HttpEntity<>(emptyProduct.toString());
        assertThat(restTemplate.postForObject("http://localhost:" + port + "/product", request, String.class))
                .contains("must not be blank")
                .contains("you must select a Product Type")
                .contains("you must select a Product Chain");
    }

}
