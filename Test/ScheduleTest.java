import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.NUPlanner.model.AnyTimeSchedulingStrategy;
import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.Schedule;
import cs3500.NUPlanner.model.User;
import cs3500.NUPlanner.model.WorkHoursSchedulingStrategy;

import static org.junit.Assert.*;

public class ScheduleTest {
  private Schedule schedule;
  private CentralSystem model;
  private AnyTimeSchedulingStrategy strat;
  private WorkHoursSchedulingStrategy strat2;


  @Before
  public void setUp() {
    schedule = new Schedule();
    strat = new AnyTimeSchedulingStrategy();
    model = new CentralSystem();
    strat2 = new WorkHoursSchedulingStrategy();
  }

  @Test
  public void testIsTimeSlotAvailable_NoEventsScheduled_ReturnsTrue() {
    assertTrue(schedule.isTimeSlotAvailable(Day.MONDAY, 600, Day.MONDAY, 700));
  }

  @Test
  public void testIsTimeSlotAvailable_EventOverlapsStart_ReturnsFalse() {
    schedule.addEvent(new Event("Existing Event", Day.MONDAY, 550, Day.MONDAY, 605, false, "Office", "Alice", new ArrayList<>()));
    assertFalse(schedule.isTimeSlotAvailable(Day.MONDAY, 600, Day.MONDAY, 640)); // Overlaps start
  }

  @Test
  public void testIsTimeSlotAvailable_EventAdjacentAtEnd_ReturnsTrue() {
    schedule.addEvent(new Event("Existing Event", Day.MONDAY, 540, Day.MONDAY, 600, false, "Office", "Alice", new ArrayList<>()));
    assertTrue(schedule.isTimeSlotAvailable(Day.MONDAY, 600, Day.MONDAY, 645)); // Starts right after an existing event ends
  }
  @Test
  public void testAnytimeStrat() {
    IUser alice = new User("Alice");
    model.addUser(alice);
    Event testEvent = strat.scheduleEvent(model, "Test", "Here", false, 90, List.of("Alice"));
    Event goodEvent = new Event("Test", Day.SUNDAY, 0, Day.SUNDAY, 130, false, "Here","Alice",  List.of("Alice"));
    Assert.assertEquals(goodEvent.endDay(), testEvent.endDay());
    Assert.assertEquals(goodEvent.endTime(), testEvent.endTime());

  }

  @Test
  public void testAnytimemultipleparticipants() {
    IUser alice = new User("Alice");
    IUser joe = new User("Joe");
    model.addUser(alice);
    model.addUser(joe);
    Event testEvent2 = new Event("Joes", Day.SUNDAY, 0, Day.SUNDAY, 130, false, "There", "Joe", List.of("Joe"));
    model.createEvent("Joe", testEvent2);
    Event testEvent = strat.scheduleEvent(model, "Test", "Here", false, 90, List.of("Alice", "Joe"));
    Event goodEvent = new Event("Test", Day.SUNDAY, 130, Day.SUNDAY, 300, false, "Here","Alice",  List.of("Alice", "Joe"));
    Assert.assertEquals(testEvent.endTime(), goodEvent.endTime());
  }

  @Test
  public void testWorkHoursStrat() {
    IUser alice = new User("Alice");
    model.addUser(alice);
    Event testEvent = strat2.scheduleEvent(model, "Test", "Here", false, 90, List.of("Alice"));
    Event goodEvent = new Event("Test", Day.MONDAY, 900, Day.MONDAY, 1030, false, "Here","Alice",  List.of("Alice"));
    Assert.assertEquals(goodEvent.endDay(), testEvent.endDay());
    Assert.assertEquals(goodEvent.endTime(), testEvent.endTime());
  }

  @Test
  public void testWorkHoursMultipleParticipants() {
    IUser alice = new User("Alice");
    IUser joe = new User("Joe");
    model.addUser(alice);
    model.addUser(joe);
    Event testEvent3 = new Event("Joes", Day.MONDAY, 945, Day.MONDAY, 1130, false, "There", "Joe", List.of("Joe"));
    model.createEvent("Joe", testEvent3);
    Event testEvent4 = strat2.scheduleEvent(model, "Test", "Here", false, 90, List.of("Alice", "Joe"));
    Event goodEvent = new Event("Test", Day.MONDAY, 1130, Day.MONDAY, 1300, false, "Here","Alice",  List.of("Alice", "Joe"));
    Assert.assertEquals(testEvent4.endDay(), goodEvent.endDay());
    Assert.assertEquals(testEvent4.endTime(), goodEvent.endTime());
  }
}
