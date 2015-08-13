package za.org.grassroot.webapp.controller.webapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import za.org.grassroot.core.repository.UserRepository;
import za.org.grassroot.webapp.GrassRootWebApplicationConfig;
import za.org.grassroot.core.domain.User;
import za.org.grassroot.services.UserManagementService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Lesetse Kimwaga
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {GrassRootWebApplicationConfig.class})
@WebIntegrationTest(randomPort = true)
public class UserProfileControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserManagementService userManagementService;

    @Test
    public void testName() throws Exception {

        User userProfile = new User();
        userProfile.setDisplayName("Grass Root");
        userProfile.setPhoneNumber("1201994");

        userProfile = userManagementService.createUserProfile(userProfile);

        assertThat(userProfile.getDisplayName(), equalTo("Grass Root"));
    }
}
