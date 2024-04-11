import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.User;

/**
 * Test creating, deleting. updating an event as well as text view and Xml creation.
 */
public class TestCentralSystem {
  private CentralSystem centralSystem;
  private User testUser;
  private Event testEvent;

  private List<String> participants;

  private final String testDirectoryPath = "/Users/arees/Downloads/NUPlanner/OutputTestDirectory";

  @Before
  public void setUp() {
    centralSystem = new CentralSystem();
    testUser = new User("Alice");
    participants = new ArrayList<>();
    participants.add("Fred");

    testEvent = new Event("Team Meeting", Day.MONDAY, 900, Day.MONDAY, 1000,
            false, "Conference Room", "Alice",
            new ArrayList<>(Arrays.asList("Alice", "Fred")));
  }


  @Test
  public void addUser() {
    centralSystem.addUser(testUser);
    Assert.assertNotNull("cs3500.NUPlanner.model.User should be added", centralSystem.getUser("Alice"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addUserAlreadyAdded() {
    centralSystem.addUser(testUser);
    centralSystem.addUser(testUser);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeUserDoesntExist() {
    centralSystem.removeUser("Bob");
  }

  @Test
  public void createEventAndCheckSchedule() {
    centralSystem.addUser(testUser);
    centralSystem.createEvent("Alice", testEvent);
    Assert.assertFalse(testUser.getSchedule().getAllEvents().isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createEventUserDoesntExist() {
    centralSystem.createEvent("Charlie", testEvent);
  }

  @Test
  public void updateEventShouldUpdateUserEvent() {
    centralSystem.addUser(testUser);
    centralSystem.createEvent("Alice", testEvent);
    Event updatedEvent = new Event("Team Meeting - Updated", Day.MONDAY, 1030,
            Day.MONDAY, 1130, false, "Conference Room B", "Alice",
            new ArrayList<>(Arrays.asList("Alice", "Fred")));
    centralSystem.updateEvent("Alice", testEvent, updatedEvent);

    Assert.assertTrue(testUser.getSchedule().getAllEvents().contains(updatedEvent) &&
            !testUser.getSchedule().getAllEvents().contains(testEvent));
  }

  @Test
  public void deleteEventShouldRemoveEventFromUser() {
    centralSystem.addUser(testUser);
    centralSystem.createEvent("Alice", testEvent);
    centralSystem.deleteEvent("Alice", testEvent);
    Assert.assertTrue(testUser.getSchedule().getAllEvents().isEmpty());
  }

  @Test
  public void testEventsMultipleUsers() {
    centralSystem.addUser(testUser);
    User testUserFred = new User("Fred");
    centralSystem.addUser(testUserFred);
    centralSystem.createEvent("Alice", testEvent);
    participants.remove("Fred");
    participants.add("Alice");
    participants.add("Fred");

    Assert.assertTrue(testUserFred.getSchedule().getAllEvents().contains(testEvent));
    Assert.assertTrue(testUser.getSchedule().getAllEvents().contains(testEvent));
    /**
     *

    Assert.assertEquals("cs3500.NUPlanner.model.User: Alice\n" +
            "SUNDAY:\n" +
            "MONDAY:\n" +
            "        name: Team Meeting\n" +
            "        time: MONDAY: 0900 -> MONDAY: 1000\n" +
            "        location: Conference Room\n" +
            "        online: false\n" +
            "        invitees: Alice, Fred\n" +
            "TUESDAY:\n" +
            "WEDNESDAY:\n" +
            "THURSDAY:\n" +
            "FRIDAY:\n" +
            "SATURDAY:\n" +
            "cs3500.NUPlanner.model.User: Fred\n" +
            "SUNDAY:\n" +
            "MONDAY:\n" +
            "        name: Team Meeting\n" +
            "        time: MONDAY: 0900 -> MONDAY: 1000\n" +
            "        location: Conference Room\n" +
            "        online: false\n" +
            "        invitees: Alice, Fred\n" +
            "TUESDAY:\n" +
            "WEDNESDAY:\n" +
            "THURSDAY:\n" +
            "FRIDAY:\n" +
            "SATURDAY:\n", centralSystem.getTextualView());

    Event updatedEvent = new Event("Team Meeting - Updated", Day.MONDAY, 1030,
            Day.MONDAY, 1130, false, "Conference Room B", "Alice",
            participants);

    centralSystem.updateEvent("Alice", testEvent, updatedEvent);
    Assert.assertTrue(testUserFred.getSchedule().getAllEvents().contains(updatedEvent));
    Assert.assertTrue(testUser.getSchedule().getAllEvents().contains(updatedEvent));


    centralSystem.deleteEvent("Alice", updatedEvent);

    Assert.assertFalse(testUserFred.getSchedule().getAllEvents().contains(testEvent));
    Assert.assertFalse(testUser.getSchedule().getAllEvents().contains(testEvent));
    Assert.assertFalse(testUserFred.getSchedule().getAllEvents().contains(updatedEvent));
    Assert.assertFalse(testUser.getSchedule().getAllEvents().contains(updatedEvent));

    centralSystem.createEvent("Alice", testEvent);
    Assert.assertTrue(testUserFred.getSchedule().getAllEvents().contains(testEvent));
    centralSystem.deleteEvent("Fred", testEvent);
    Assert.assertTrue(testUser.getSchedule().getAllEvents().contains(testEvent));
    Assert.assertFalse(testUserFred.getSchedule().getAllEvents().contains(testEvent));
*/
  }

  @Test
  public void testMultipleEventsAndOrder() {
    User testUser3 = new User("John");
    centralSystem.addUser(testUser3);

    Event morningMeeting = new Event("Morning Meeting", Day.SATURDAY, 900,
            Day.SATURDAY, 1000, false, "Room 101", "John",
            new ArrayList<>(Arrays.asList("John")));
    Event lunch = new Event("Lunch Break", Day.SATURDAY, 1200, Day.SATURDAY,
            1300, false, "Cafeteria", "John",
            new ArrayList<>(Arrays.asList("John")));
    Event afternoonMeeting = new Event("Afternoon Meeting", Day.SATURDAY, 1400,
            Day.SATURDAY, 1500, true, "", "John",
            new ArrayList<>(Arrays.asList("John", "Charlie")));

    centralSystem.createEvent("John", morningMeeting);
    centralSystem.createEvent("John", lunch);
    centralSystem.createEvent("John", afternoonMeeting);

    String expectedView = "cs3500.NUPlanner.model.User: John\n" +
            "SUNDAY:\n" +
            "MONDAY:\n" +
            "TUESDAY:\n" +
            "WEDNESDAY:\n" +
            "THURSDAY:\n" +
            "FRIDAY:\n" +
            "SATURDAY:\n" +
            "        name: Morning Meeting\n" +
            "        time: SATURDAY: 0900 -> SATURDAY: 1000\n" +
            "        location: Room 101\n" +
            "        online: false\n" +
            "        invitees: John\n" +
            "        name: Lunch Break\n" +
            "        time: SATURDAY: 1200 -> SATURDAY: 1300\n" +
            "        location: Cafeteria\n" +
            "        online: false\n" +
            "        invitees: John\n" +
            "        name: Afternoon Meeting\n" +
            "        time: SATURDAY: 1400 -> SATURDAY: 1500\n" +
            "        location: \n" +
            "        online: true\n" +
            "        invitees: John, Charlie\n";


   // Assert.assertEquals(expectedView, centralSystem.getTextualView());
  }







}
