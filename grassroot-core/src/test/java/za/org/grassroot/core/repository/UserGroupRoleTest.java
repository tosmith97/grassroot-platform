package za.org.grassroot.core.repository;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import za.org.grassroot.TestContextConfiguration;
import za.org.grassroot.core.GrassRootApplicationProfiles;
import za.org.grassroot.core.domain.BaseRoles;
import za.org.grassroot.core.domain.Group;
import za.org.grassroot.core.domain.User;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Lesetse Kimwaga
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
@Transactional
@ActiveProfiles(GrassRootApplicationProfiles.INMEMORY)
public class UserGroupRoleTest {


    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Ignore
    public void testCreateGroupRoles() throws Exception {
        User user = new User("27729100003");
        user.setFirstName("Java");
        user.setLastName("Pablo");

        user = userRepository.save(user);

        Group group1 = new Group("Red Devils", user);
        Group group2 = new Group("Code Nation", user);

        group1.addMember(user, BaseRoles.ROLE_GROUP_ORGANIZER);
        group2.addMember(user, BaseRoles.ROLE_ORDINARY_MEMBER);

        group1 = groupRepository.save(group1);
        group2 = groupRepository.save(group2);

        assertThat(user.getAuthorities(), hasSize(2));
//        assertThat(user.getAuthorities(), CoreMatchers.hasItem(group1Role1));
//        assertThat(user.getAuthorities(), hasItem(Matchers.<GrantedAuthority>hasProperty("authority", equalTo("GROUP_ROLE_GROUP_ADMINISTRATOR_GROUP_ID_" + group1.getId()))));

    }
}
