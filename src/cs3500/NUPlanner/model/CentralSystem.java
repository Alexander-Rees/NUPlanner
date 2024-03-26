
package cs3500.NUPlanner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.NUPlanner.model.ICentralSystem;


/**
 * cs3500.NUPlanner.model.CentralSystem manages users and their schedules.
 * It allows for the creation, update, and deletion of events in user schedules
 * and supports importing and exporting user schedules via XML.
 * Also creates a text view of the planner.
 */

public class CentralSystem implements ICentralSystem {
  private Map<String, IUser> users;

  /**
   * Constructs a new cs3500.NUPlanner.model.CentralSystem with an empty list of users.
   */
  public CentralSystem() {
    this.users = new HashMap<>();
  }


  @Override
  public void addUser(IUser user) {
    if (user == null || users.containsKey(user.getName())) {
      throw new IllegalArgumentException("cs3500.NUPlanner.model.User is null or already exists");
    }
    users.put(user.getName(), user);
  }

  @Override
  public void removeUser(String name) {
    if (!users.containsKey(name)) {
      throw new IllegalArgumentException("cs3500.NUPlanner.model.User does not exist.");
    }
    users.remove(name);
  }

  @Override
  public IUser getUser(String name) {
    return users.get(name);
  }

  @Override
  public List<ReadonlyIUser> getAllUsers() {
    return new ArrayList<>(users.values());
  }

  @Override
  public void createEvent(String name, IEvent event) {
    IUser user = users.get(name);
    if (user == null) {
      throw new IllegalArgumentException("cs3500.NUPlanner.model.User does not exist.");
    }


    for (String participantName : event.participants()) {
      IUser participant = users.get(participantName);
      if (participant != null) {
        participant.getModifiableSchedule().addEvent(event);
      }
    }
  }

  @Override
  public void updateEvent(String name, IEvent oldEvent, IEvent newEvent) {
    IUser user = users.get(name);
    if (user == null) {
      throw new IllegalArgumentException("cs3500.NUPlanner.model.User does not exist.");
    }


    for (String participantName : oldEvent.participants()) {
      IUser participant = users.get(participantName);
      if (participant != null) {
        participant.getModifiableSchedule().updateEvent(oldEvent, newEvent);
      }
    }
  }

  @Override
  public void deleteEvent(String name, IEvent event) {
    IUser host = users.get(name);
    if (host == null) {
      throw new IllegalArgumentException("Host does not exist.");
    }
    if (event.getHost() == name) {
      for (String participantName : event.participants()) {
        IUser participant = users.get(participantName);
        if (participant != null) {
          participant.getModifiableSchedule().removeEvent(event);
        }
      }
    }
    host.getModifiableSchedule().removeEvent(event);
  }






}
