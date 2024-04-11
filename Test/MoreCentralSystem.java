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
 * Additional tests for the Central System.
 */
public class MoreCentralSystem {

  private CentralSystem centralSystem;
  private User roberto;
  private User alex;
  private Event ood;
  private Event fundies;
  private Event discrete;
  private final String testDirectoryPath =
          "/Users/arees/Downloads/NUPlanner1 3/OutputTestDirectory";


  @Before
  public void setUp() {

    centralSystem = new CentralSystem();
    roberto = new User("Roberto");
    alex = new User("Alex");
    ood = new Event("OOD", Day.TUESDAY, 1335, Day.TUESDAY, 1515,
            false, "Churchill", "Roberto",
            new ArrayList<>(Arrays.asList("Roberto", "Alex")));
    fundies = new Event("Fundies", Day.WEDNESDAY, 1030, Day.WEDNESDAY, 1135,
            true, "Zoom", "Roberto",
            new ArrayList<>(Arrays.asList("Roberto", "Alex")));
    discrete = new Event("Discrete", Day.WEDNESDAY, 1345, Day.WEDNESDAY,
            1515, false, "Dodge", "Roberto",
            new ArrayList<>(Arrays.asList("Roberto")));
  }

  @Test
  public void MakeARealPlanner() {
    centralSystem.addUser(alex);
    centralSystem.addUser(roberto);
    centralSystem.createEvent("Roberto", ood);
    centralSystem.createEvent("Alex", fundies);

    Assert.assertTrue(alex.getSchedule().getAllEvents().contains(ood));
    Assert.assertTrue(alex.getSchedule().getAllEvents().contains(fundies));
    Assert.assertTrue(roberto.getSchedule().getAllEvents().contains(ood));
    Assert.assertTrue(roberto.getSchedule().getAllEvents().contains(fundies));

    centralSystem.deleteEvent("Alex", ood);

    Assert.assertFalse(alex.getSchedule().getAllEvents().contains(ood));
    Assert.assertTrue(alex.getSchedule().getAllEvents().contains(fundies));
    Assert.assertTrue(roberto.getSchedule().getAllEvents().contains(ood));
    Assert.assertTrue(roberto.getSchedule().getAllEvents().contains(fundies));

    centralSystem.deleteEvent("Roberto", fundies);

    Assert.assertFalse(alex.getSchedule().getAllEvents().contains(ood));
    Assert.assertFalse(alex.getSchedule().getAllEvents().contains(fundies));
    Assert.assertTrue(roberto.getSchedule().getAllEvents().contains(ood));
    Assert.assertFalse(roberto.getSchedule().getAllEvents().contains(fundies));

    centralSystem.deleteEvent("Roberto", ood);
    centralSystem.createEvent("Roberto", ood);
    centralSystem.updateEvent("Roberto", ood, discrete);
    Assert.assertFalse(alex.getSchedule().getAllEvents().contains(ood));
    Assert.assertFalse(alex.getSchedule().getAllEvents().contains(fundies));
    Assert.assertFalse(roberto.getSchedule().getAllEvents().contains(ood));
    Assert.assertFalse(roberto.getSchedule().getAllEvents().contains(fundies));
    Assert.assertTrue(alex.getSchedule().getAllEvents().contains(discrete));
    Assert.assertTrue(roberto.getSchedule().getAllEvents().contains(discrete));
  }




}
