import cs3500.NUPlanner.model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestableModel implements ICentralSystem {
  private final Appendable log;

  public TestableModel(Appendable log) {
    this.log = log;
  }

  private void logAction(String message) {
    try {
      log.append(message).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("Failed to log action", e);
    }
  }

  @Override
  public void addUser(IUser user) {
    logAction("addUser called with user: " + user.getName());
  }

  @Override
  public void removeUser(String name) {
    logAction("removeUser called for user: " + name);
  }

  @Override
  public void createEvent(String name, IEvent event) {
    logAction("createEvent called for user: " + name + " with event: " + event.name());
  }

  @Override
  public void updateEvent(String name, ReadonlyIEvent oldEvent, IEvent newEvent) {
    logAction("updateEvent called for user: " + name + " to replace event: " + oldEvent.name() +
            " with event: " + newEvent.name());
  }

  @Override
  public void deleteEvent(String name, ReadonlyIEvent event) {
    logAction("deleteEvent called for user: " + name + " to delete event: " + event.name());
  }

  @Override
  public ReadonlyIUser getUser(String name) {
    logAction("getUser called for user: " + name);
    return null;
  }

  @Override
  public List<ReadonlyIUser> getAllUsers() {
    logAction("getAllUsers called");
    return new ArrayList<>();
  }
}
