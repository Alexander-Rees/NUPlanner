
package cs3500.NUPlanner.model;

import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;

/**
 * Represents a user with a name and a schedule.
 */
public class User implements IUser {
  private String name;
  private ISchedule schedule;

  /**
   * Constructs a new cs3500.NUPlanner.model.User with the given name.
   * Initializes the user's schedule as an empty cs3500.NUPlanner.model.Schedule.
   *
   * @param name the name of the user.
   */
  public User(String name) {
    this.name = name;
    this.schedule = new Schedule();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;

  }

  @Override
  public ISchedule getSchedule() {
    return schedule;
  }

  @Override
  public void setSchedule(ISchedule schedule) {
    this.schedule = schedule;

  }

  @Override
  public ISchedule getModifiableSchedule() {
    return this.schedule;
  }
}
