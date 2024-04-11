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

