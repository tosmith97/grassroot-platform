package za.org.grassroot.core.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import za.org.grassroot.TestContextConfiguration;
import za.org.grassroot.core.GrassRootApplicationProfiles;
import za.org.grassroot.core.domain.Account;
import za.org.grassroot.core.domain.Group;
import za.org.grassroot.core.domain.PaidGroup;
import za.org.grassroot.core.domain.User;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


/**
 * Created by luke on 2015/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContextConfiguration.class)
@Transactional
@ActiveProfiles(GrassRootApplicationProfiles.INMEMORY)
public class PaidGroupRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PaidGroupRepositoryTest.class);

    @Autowired
    private PaidGroupRepository paidGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    private static final String testPhoneNumber = "0810005555";
    private static final String testGroupName = "testGroup";
    private static final String testAccountName = "testAccount";

    private User testUser;
    private Group testGroup, testGroup2;
    private Account testAccount, testAccount2;

    @Before
    public void setUp() throws Exception {

        testUser = userRepository.save(new User(testPhoneNumber));
        testGroup = groupRepository.save(new Group(testGroupName, testUser));
        testGroup2 = groupRepository.save(new Group(testGroupName + "2", testUser));
        testAccount = accountRepository.save(new Account(testAccountName, true));
        testAccount2 = accountRepository.save(new Account(testAccountName + "2", true));

    }

    @Test
    @Rollback
    public void shouldSaveAndReturnId() {

        assertThat(paidGroupRepository.count(), is(0L));

        PaidGroup paidGroup = new PaidGroup(testGroup, testAccount, testUser);
        paidGroup = paidGroupRepository.save(paidGroup);

        assertNotEquals(null, paidGroup.getId());

        // assertEquals(Long.parseLong("1"), Long.parseLong(paidGroup.getId().toString())); // DB is not rolling back so these are causing errors
    }

    @Test
    @Rollback
    public void shouldSaveWithGroupUserAccount() {

        assertThat(paidGroupRepository.count(), is(0L));

        PaidGroup paidGroup = new PaidGroup(testGroup, testAccount, testUser);
        paidGroupRepository.save(paidGroup);
        assertThat(paidGroupRepository.count(), is(1L));
        PaidGroup paidGroupFromDb = paidGroupRepository.findAll().iterator().next();
        assertNotNull(paidGroupFromDb);
        assertEquals(paidGroupFromDb.getAccount(), testAccount);
        assertEquals(paidGroupFromDb.getAddedByUser(), testUser);
        assertEquals(paidGroupFromDb.getGroup(), testGroup);

    }

    @Test
    @Rollback
    public void shouldRemoveWithGroupUserAccount() {

        assertThat(paidGroupRepository.count(), is(0L));
        PaidGroup paidGroup = new PaidGroup(testGroup, testAccount, testUser);
        paidGroupRepository.save(paidGroup);
        PaidGroup paidGroupFromDb = paidGroupRepository.findAll().iterator().next();
        assertNotNull(paidGroupFromDb);
        paidGroupFromDb.setExpireDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        paidGroupFromDb.setRemovedByUser(testUser);
        paidGroupRepository.save(paidGroupFromDb);
        PaidGroup paidGroupFromDb1 = paidGroupRepository.findAll().iterator().next();
        assertNotNull(paidGroupFromDb1);
        assertEquals(paidGroupFromDb.getId(), paidGroupFromDb1.getId());
        assertEquals(paidGroupFromDb1.getRemovedByUser(), testUser);
        assertTrue(paidGroupFromDb1.getExpireDateTime().getTime() < Calendar.getInstance().getTimeInMillis());
    }

    @Test
    @Rollback
    public void shouldFindPaidGroupsByExpiryDateTime() {

        assertThat(paidGroupRepository.count(), is(0L));

        PaidGroup paidGroup = paidGroupRepository.save(new PaidGroup(testGroup, testAccount, testUser));
        PaidGroup paidGroup2 = paidGroupRepository.save(new PaidGroup(testGroup, testAccount2, testUser));
        List<PaidGroup> firstFind = paidGroupRepository.findByExpireDateTimeGreaterThan(new Date());
        assertNotNull(firstFind);
        assertThat(firstFind.size(), is(2));
        assertTrue(firstFind.contains(paidGroup));
        assertTrue(firstFind.contains(paidGroup2));
        paidGroup2.setExpireDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        paidGroup2.setRemovedByUser(testUser);
        paidGroup2 = paidGroupRepository.save(paidGroup2);
        List<PaidGroup> secondFind = paidGroupRepository.findByExpireDateTimeGreaterThan(new Date());
        assertNotNull(secondFind);
        assertThat(secondFind.size(), is(1));
        assertTrue(secondFind.contains(paidGroup));
        assertFalse(secondFind.contains(paidGroup2));

    }

    @Test
    @Rollback
    public void shouldFindPaidGroupsByAccount() {

        assertThat(paidGroupRepository.count(), is(0L));
        PaidGroup paidGroup = paidGroupRepository.save(new PaidGroup(testGroup, testAccount, testUser));
        PaidGroup paidGroup2 = paidGroupRepository.save(new PaidGroup(testGroup2, testAccount2, testUser));
        assertThat(paidGroupRepository.count(), is(2L));
        List<PaidGroup> listAccount1 = paidGroupRepository.findByAccount(testAccount);
        List<PaidGroup> listAccount2 = paidGroupRepository.findByAccount(testAccount2);
        assertNotNull(listAccount1);
        assertNotNull(listAccount2);
        assertThat(listAccount1.size(), is(1));
        assertThat(listAccount2.size(), is(1));
        assertTrue(listAccount1.contains(paidGroup));
        assertTrue(listAccount2.contains(paidGroup2));

    }

    @Test
    @Rollback
    public void shouldFindPaidGroupByGroup() {
        assertThat(paidGroupRepository.count(), is(0L));
        PaidGroup paidGroup = paidGroupRepository.save(new PaidGroup(testGroup, testAccount, testUser));
        PaidGroup paidGroupFromDb = paidGroupRepository.findByGroupOrderByExpireDateTimeDesc(testGroup).iterator().next();
        assertNotNull(paidGroupFromDb);
        assertThat(paidGroupFromDb, is(paidGroup));
    }

    @Test
    @Rollback
    public void shouldFindCurrentPaidGroupByGroup() {
        // services layer needs to ensure several of these steps don't happen (test there), but testing DB behaviour here
        assertThat(paidGroupRepository.count(), is(0L));
        PaidGroup paidGroup1 = paidGroupRepository.save(new PaidGroup(testGroup, testAccount, testUser));
        PaidGroup paidGroup2 = paidGroupRepository.save(new PaidGroup(testGroup, testAccount2, testUser));
        List<PaidGroup> firstList = paidGroupRepository.findByGroupOrderByExpireDateTimeDesc(testGroup);
        assertThat(firstList.size(), is(2));
        paidGroup1.setExpireDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        paidGroup1.setRemovedByUser(testUser);
        paidGroupRepository.save(paidGroup1);
        List<PaidGroup> secondList = paidGroupRepository.findByGroupOrderByExpireDateTimeDesc(testGroup);
        assertEquals(secondList.get(0).getId(), paidGroup2.getId()); // straight equals gives errors on hash codes
    }

}
