package za.org.grassroot.services.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import za.org.grassroot.GrassRootServicesConfig;
import za.org.grassroot.core.GrassRootApplicationProfiles;
import za.org.grassroot.core.domain.Event;
import za.org.grassroot.core.domain.Group;
import za.org.grassroot.core.domain.User;
import za.org.grassroot.core.repository.EventLogRepository;
import za.org.grassroot.core.repository.EventRepository;
import za.org.grassroot.core.repository.GroupRepository;
import za.org.grassroot.core.repository.UserRepository;
import za.org.grassroot.services.EventManagementService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Lesetse Kimwaga
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GrassRootServicesConfig.class,TestContextConfig.class})
@Transactional
@ActiveProfiles(GrassRootApplicationProfiles.INMEMORY)
public class EventManagementServiceTest {

   // @Rule
   // public OutputCapture capture = new OutputCapture();
    
    private Logger log = LoggerFactory.getLogger(EventManagementServiceTest.class);

    @Autowired
    private EventManagementService eventManagementService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventLogRepository eventLogRepository;

    @Test
    public void shouldSaveEventWithNameUserAndGroup() {

/*        User userProfile = userManagementService.createUserProfile(new User("111222333", "aap1"));
        Group group = groupManagementService.createNewGroup(userProfile, Arrays.asList("111222444", "111222555"), false);
        Event event = eventManagementService.createEvent("Drink till you drop", userProfile, group);
        assertEquals("Drink till you drop", event.getGroupName());
        assertEquals(userProfile.getId(), event.getCreatedByUser().getId());
        assertEquals(group.getId(), event.getGroup().getId());*/

    }

    /* @Test
    public void shouldSaveEventWithMinimumDataAndTriggerNotifications() {
        log.info("shouldSaveEventWithMinimumDataAndTriggerNotifications...starting...");
        User userProfile = userManagementService.createUserProfile(new User("111222555", "aap1"));
        Group group = groupManagementService.createNewGroup(userProfile, Arrays.asList("111222666", "111222777"), false);
        Event event = eventManagementService.createEvent("Tell me about it", userProfile, group);
        event = eventManagementService.setLocation(event.getId(), "Lekker place");
        event = eventManagementService.setDateTimeString(event.getId(),"31st 7pm");
        log.info("shouldSaveEventWithMinimumDataAndTriggerNotifications...done..." + event.toString());

    }

    //N.B. this method is not triggering queues so not actually working therefore no asserts
    @Test
    public void shouldTriggerAddAndChangeNotifications() {
        log.info("shouldTriggerAddAndChangeNotifications...starting...");
        User userProfile = userManagementService.createUserProfile(new User("111222556", "aap1"));
        Group group = groupManagementService.createNewGroup(userProfile, Arrays.asList("111222667", "111222778"), false);
        Event event = eventManagementService.createEvent("Tell me about it 2", userProfile, group);
        event = eventManagementService.setLocation(event.getId(), "Lekker place 2");
        event = eventManagementService.setDateTimeString(event.getId(),"31st 7pm");
        event = eventManagementService.setLocation(event.getId(),"New lekker place");
        log.info("shouldTriggerAddAndChangeNotifications...done..." + event.toString());

    }

    //N.B. this method is not triggering queues so not actually working therefore no asserts
    @Test
    public void shouldTriggerAddAndCancelNotifications() {
        log.info("shouldTriggerAddAndCancelNotifications...starting...");
        User userProfile = userManagementService.createUserProfile(new User("111222556", "aap1"));
        Group group = groupManagementService.createNewGroup(userProfile, Arrays.asList("111222667", "111222778"), false);
        Event event = eventManagementService.createEvent("Tell me about it 2", userProfile, group);
        event = eventManagementService.setLocation(event.getId(), "Lekker place 2");
        event = eventManagementService.setDateTimeString(event.getId(), "31 7pm");
        event = eventManagementService.cancelEvent(event.getId());
        log.info("shouldTriggerAddAndCancelNotifications...done..." + event.toString());

    }

    @Test
    public void shouldReturnOutstandingRSVPEventForSecondLevelUserAndParentGroupEvent() {
        User user = userRepository.save(new User("0825555511"));
        Group grouplevel1 = groupRepository.save(new Group("rsvp level1",user));
        User userl1 = userRepository.save(new User("0825555512"));
        grouplevel1.addMember(userl1);
        grouplevel1 = groupRepository.save(grouplevel1);
        Group grouplevel2 = groupRepository.save(new Group("rsvp level2",user));
        grouplevel2.setParent(grouplevel1);
        grouplevel2 = groupRepository.save(grouplevel2);
        User userl2 = userRepository.save(new User("0825555521"));
        grouplevel2.addMember(userl2);
        grouplevel2 = groupRepository.save(grouplevel2);
        Event event = eventRepository.save(new Event("test rsvp required",user,grouplevel1,true,true));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,60);
        event.setEventStartDateTime(new Timestamp(calendar.getTime().getTime()));
        event = eventRepository.save(event);
        List<Event> outstanding =  eventManagementService.getOutstandingRSVPForUser(userl2);
        assertNotNull(outstanding);
        assertEquals(1,outstanding.size());
        assertEquals(event.getId(),outstanding.get(0).getId());
    }*/

    @Test
    public void shouldNotReturnOutstandingRSVPEventForSecondLevelUserAndParentGroupEvent() {
        User user = userRepository.save(new User("0825555511"));
        Group grouplevel1 = groupRepository.save(new Group("rsvp level1",user));
        User userl1 = userRepository.save(new User("0825555512"));
        grouplevel1.addMember(userl1);
        grouplevel1 = groupRepository.save(grouplevel1);
        Group grouplevel2 = groupRepository.save(new Group("rsvp level2",user));
        grouplevel2.setParent(grouplevel1);
        grouplevel2 = groupRepository.save(grouplevel2);
        User userl2 = userRepository.save(new User("0825555521"));
        grouplevel2.addMember(userl2);
        grouplevel2 = groupRepository.save(grouplevel2);
        List<Event> outstanding =  eventManagementService.getOutstandingRSVPForUser(userl2);
        assertNotNull(outstanding);
        assertEquals(0,outstanding.size());
    }

    //todo aakil test more scenarios , ie single level, multiple events etc

    @Test
    public void shouldCreateVoteFromEntity() {
        /* assertThat(eventLogRepository.count(), is(0L));
        User user = userManagementService.loadOrSaveUser("0710001234");
        Group group1 = groupManagementService.createNewGroup(user, Arrays.asList("0701112345"), false);
        Event event = new Event();
        event.setEventType(EventType.Vote);
        event.setRsvpRequired(true);
        event.setGroup(group1);
        event.setName("Does voting work?");
        event.setEventStartDateTime(new Timestamp(DateTimeUtil.addMinutesToDate(new Date(), 5).getTime()));
        event = eventManagementService.createVote(event);
        assertNotSame(0, event.getId());
        assertThat(event.getGroup(), is(group1));*/
        // assertTrue(eventManagementService.countFutureEvents(user)); // fails because getUpcomingEventsQuery is PSQL-dependent

        // todo: make method in send message save to cache -- so that we can check -- this fails because of async and various other issues
        // assertNotEquals(eventLogRepository.count(), 0L);
    }

}
