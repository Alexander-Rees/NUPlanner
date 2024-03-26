import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.Event;

/**
 * Tests event construction, ensures errors are correctly thrown.
 * Test event methods.
 */
public class TestEvent {
  private Event event;
  private Event goodEvent1;

  @Before
  public void setUp() {
    event = new Event("Test cs3500.NUPlanner.model.Event", Day.MONDAY, 1200, Day.MONDAY, 1300,
            false, "Test Location", "Host", new ArrayList<>());
    goodEvent1 = new Event("GoodEvent1", Day.TUESDAY, 1615, Day.FRIDAY,
            0005, true, "Place", "Host", new ArrayList<>());
  }

  @Test
  public void testEventInintializing() {
    Assert.assertEquals("Test cs3500.NUPlanner.model.Event", event.name());
    Assert.assertEquals(Day.MONDAY, event.startDay());
    Assert.assertEquals(1200, event.startTime());
    Assert.assertEquals(Day.MONDAY, event.endDay());
    Assert.assertEquals(1300, event.endTime());
    Assert.assertFalse(event.online());
    Assert.assertEquals("Test Location", event.location());
  }

  @Test
  public void testEventInintializingEarlyDifDay() {
    Assert.assertEquals("GoodEvent1", goodEvent1.name());
    Assert.assertEquals(Day.TUESDAY, goodEvent1.startDay());
    Assert.assertEquals(1615, goodEvent1.startTime());
    Assert.assertEquals(Day.FRIDAY, goodEvent1.endDay());
    Assert.assertEquals(0005, goodEvent1.endTime());
    Assert.assertTrue(goodEvent1.online());
    Assert.assertEquals("Place", goodEvent1.location());
  }

  @Test
  public void testSettingInvalidStartTime() {
    Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
      event.setStartTime(2400);
    });
    Assert.assertEquals("Invalid time", exception.getMessage());
  }

  @Test
  public void testSettingInvalidEndTime() {
    Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
      event.setEndTime(-1);
    });
    Assert.assertEquals("Invalid time", exception.getMessage());
  }

  @Test
  public void testEventEndBeforeStart() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new Event("Faulty cs3500.NUPlanner.model.Event", Day.TUESDAY, 1500, Day.TUESDAY, 1400,
              true, "Faulty Location", "Host", new ArrayList<>());
    });
  }

  @Test
  public void testAddParticipant() {
    event.addParticipant("JohnDoe");
    Assert.assertTrue(event.participants().contains("JohnDoe"));
  }

  @Test
  public void testRemoveParticipant() {
    event.addParticipant("JaneDoe");
    Assert.assertTrue(event.participants().contains("JaneDoe"));
    event.removeParticipant("JaneDoe");
    Assert.assertFalse(event.participants().contains("JaneDoe"));
  }

}
