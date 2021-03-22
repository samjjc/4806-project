package com.sysc4806app.server;

import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@ImportAutoConfiguration(TestSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void followingWorksProperly() throws Exception {
        User sam = new User("sam");
        userRepo.save(sam);
        User ben = new User("ben");
        userRepo.save(ben);
        this.restTemplate.postForObject(
                "http://localhost:" + port + "/follow/"+sam.getId()+"/"+ben.getId(),
                null,
                String.class);
        assertThat(userRepo.findById(sam.getId()).get().getFollowing()).containsOnly(ben);
    }

}
