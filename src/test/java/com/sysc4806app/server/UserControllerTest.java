package com.sysc4806app.server;

import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
        User sam = new User("anonymousUser", "pass1");
        userRepo.save(sam);
        User ben = new User("ben", "pass1");
        userRepo.save(ben);
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        this.restTemplate.postForObject(
                "http://localhost:" + port + "/follow/"+ben.getName(),
                null,
                String.class);
        assertThat(userRepo.findById(sam.getId()).get().getFollowing()).containsOnly(ben);
        userRepo.deleteAll();
    }

    @Test
    public void accessUserNotFound() throws Exception {
        userRepo.deleteAll();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/123",
                String.class)).contains("Error 404: The user you seek does not exist.");
    }
    @WithMockUser("joe")
    @Test
    public void accessUserLoggedIn() throws Exception {
        User joe = new User("joe", "123");
        userRepo.save(joe);
        User ben = new User("ben", "pass1");
        userRepo.save(ben);

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(joe.getName());
        SecurityContextHolder.setContext(securityContext);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/"+joe.getName(),
                String.class))
                .doesNotContain("follow")
                .doesNotContain("unfollow")
                .contains(joe.getName())
                .contains("Following:")
                .contains("Reviews:");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/"+ben.getName(),
                String.class))
                .contains("Follow");
        joe.followUser(ben);
        userRepo.save(joe);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/"+ben.getName(),
                String.class))
                .contains("Unfollow");

        userRepo.deleteAll();

    }

}
