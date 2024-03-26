import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.Schedule;

/**
 * Class for testing if our implementation of schedule works correctly.
 */
public class TestSchedule {
  /**
   * Tests that all scheduling events work including adding events deleting events and updating.
   */
  private Schedule schedule;
  private Event event1;
  private Event event2;
  private Event event3;

  @Before
  public void setUp() {
    schedule = new Schedule();
    event1 = new Event("Meeting", Day.MONDAY, 900, Day.MONDAY, 1000,
            false, "Room 101", "Host", new ArrayList<>());
    event2 = new Event("Workshop", Day.MONDAY, 1030, Day.MONDAY, 1130,
            true, "Room 102", "Host", new ArrayList<>());
    event3 = new Event("Review", Day.TUESDAY, 900, Day.TUESDAY, 1000,
            false, "Room 101", "Host", new ArrayList<>());
  }

  @Test
  public void addValidEventWithoutConflict() {
    schedule.addEvent(event1);
    Assert.assertTrue(schedule.getAllEvents().contains(event1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNullEvent() {
    schedule.addEvent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addConflictingEvent() {
    schedule.addEvent(event1);
    Event conflictingEvent = new Event("Conflict Meeting", Day.MONDAY, 930,
            Day.MONDAY, 1030, false, "Room 103", "Host",
            new ArrayList<>());
    schedule.addEvent(conflictingEvent);
  }

  @Test
  public void removeEventThatExists() {
    schedule.addEvent(event1);
    schedule.removeEvent(event1);
    Assert.assertFalse(schedule.getAllEvents().contains(event1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNullEvent() {
    schedule.removeEvent(null);
  }

  @Test
  public void updateValidEvent() {
    schedule.addEvent(event1);
    Event updatedEvent = new Event("Updated Meeting", Day.MONDAY, 900, Day.MONDAY,
            1030, false, "Room 104", "Host", new ArrayList<>());
    schedule.updateEvent(event1, updatedEvent);
    Assert.assertFalse(schedule.getAllEvents().contains(event1));
    Assert.assertTrue(schedule.getAllEvents().contains(updatedEvent));
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateEventWithConflict() {
    schedule.addEvent(event1);
    schedule.addEvent(event2);
    Event conflictingUpdate = new Event("Updated Meeting", Day.MONDAY, 1030,
            Day.MONDAY, 1130, false, "Room 104", "Host",
            new ArrayList<>());
    schedule.updateEvent(event1, conflictingUpdate);
  }

  @Test
  public void getEventsForDay_SpecificDay_ReturnsCorrectEvents() {
    schedule.addEvent(event1);
    schedule.addEvent(event2);
    schedule.addEvent(event3);
    List<IEvent> events = schedule.getEventsForDay(Day.MONDAY);
    Assert.assertEquals(events, schedule.getEventsForDay(Day.MONDAY));
    Assert.assertTrue(events.contains(event1));
    Assert.assertTrue(events.contains(event2));
    Assert.assertFalse(events.contains(event3));
  }

  @Test
  public void hasConflictYes() {
    schedule.addEvent(event1);
    Event conflictingEvent = new Event("Overlap Meeting", Day.MONDAY, 930,
            Day.MONDAY, 1030, false, "Room 105", "Host",
            new ArrayList<>());
    Assert.assertTrue(schedule.hasConflict(conflictingEvent));
  }

  @Test
  public void hasConflictNo() {
    schedule.addEvent(event1);
    Assert.assertFalse(schedule.hasConflict(event3));
  }

  @Test
  public void testEdgeCases() {
    Event testEvent = new Event("Test cs3500.NUPlanner.model.Event", Day.MONDAY, 930,
            Day.TUESDAY, 1030, false, "Room 105", "Host", new ArrayList<>());
    Event testEvent2 = new Event("Test Event2", Day.TUESDAY, 1030,
            Day.MONDAY, 1130, false, "Room 105", "Host", new ArrayList<>());
    schedule.addEvent(testEvent);
    schedule.addEvent(testEvent2);
    Assert.assertTrue(schedule.getAllEvents().contains(testEvent));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMidnightOverlaps() {
    Event testEvent3 = new Event("Test Event3", Day.MONDAY, 1030, Day.THURSDAY,
            0, false, "Room 105", "Host", new ArrayList<>());
    schedule.addEvent(testEvent3);

    Event testEvent4 = new Event("Test Event4", Day.WEDNESDAY, 2330, Day.THURSDAY,
            1130, false, "Room 105", "Host", new ArrayList<>());
    schedule.addEvent(testEvent4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWeekChangeOverlaps() {
    Event testEvent3 = new Event("Test Event3", Day.THURSDAY, 1030, Day.TUESDAY,
            0, false, "Room 105", "Host", new ArrayList<>());
    Event testEvent4 = new Event("Test Event4", Day.FRIDAY, 2330, Day.MONDAY,
            1130, false, "Room 105", "Host", new ArrayList<>());
    schedule.addEvent(testEvent3);
    schedule.addEvent(testEvent4);
  }


}