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
    public void productPageShouldProperlyShowEmptyMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/productlist",
                String.class)).contains("No Products Available ");
    }

    @Test
    public void productPageShouldProperlyShowProducts() throws Exception {

        productRepo.save(new Product("www.soup.com", "tacos", "yum tum", ProductType.BGR, ProductChain.TIM));
        productRepo.save(new Product("www.soup.com", "salsa", "yum tum", ProductType.BGR, ProductChain.TIM));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/productlist",
                String.class))
                .doesNotContain("No Products Available ")
                .contains("www.soup.com")
                .contains("tacos")
                .contains("yum tum")
                .contains("Burgers")
                .contains("Tim Horton")
                .contains("salsa");
        productRepo.deleteAll();
    }


}
