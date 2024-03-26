import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import cs3500.NUPlanner.controller.XmlHandler;
import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.ReadonlyIEvent;

class TestXmlHandler {


  @Test
  void testReadSchedule() throws Exception {
    XmlHandler handler = new XmlHandler();
    String filePath = "/Users/arees/Downloads/NUPlanner/Test/prof.xml";

    Map<String, Object> result = handler.readSchedule(filePath);

    Assert.assertTrue(result.containsKey("userName"));
    Assert.assertTrue(result.containsKey("schedule"));

    String userName = (String) result.get("userName");
    Assert.assertEquals("Prof. Lucia", userName);

    ISchedule schedule = (ISchedule) result.get("schedule");

    List<ReadonlyIEvent> events = schedule.getAllEvents();
    Assert.assertEquals(3, events.size());

    ReadonlyIEvent firstEvent = events.get(0);
    Assert.assertEquals("CS3500 Morning Lecture", firstEvent.name());
    Assert.assertEquals(Day.TUESDAY, firstEvent.startDay());
    Assert.assertEquals(950, firstEvent.startTime());
    Assert.assertEquals(Day.TUESDAY, firstEvent.endDay());
    Assert.assertEquals(1130, firstEvent.endTime());
    Assert.assertFalse(firstEvent.online());
    Assert.assertEquals("Churchill Hall 101", firstEvent.location());

    Assert.assertEquals(3, firstEvent.participants().size());
    Assert.assertTrue(firstEvent.participants().contains("Prof. Lucia"));
    Assert.assertTrue(firstEvent.participants().contains("Student Anon"));
    Assert.assertTrue(firstEvent.participants().contains("Chat"));

    String host = firstEvent.participants().get(0);
    Assert.assertEquals("Prof. Lucia", host);

  }


  @Test
  void testReadFromNonExistentFile() {
    XmlHandler handler = new XmlHandler();
    String filePath = "/path/that/does/not/exist.xml";

    Exception exception = Assert.assertThrows(IOException.class, () -> {
      handler.readSchedule(filePath);
    });

    String expectedMessage = "No such file or directory";
    String actualMessage = exception.getMessage();

    Assert.assertTrue(actualMessage.contains(expectedMessage));
  }


}

