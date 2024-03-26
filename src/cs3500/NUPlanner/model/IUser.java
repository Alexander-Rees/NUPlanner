
package cs3500.NUPlanner.model;

import cs3500.NUPlanner.model.ISchedule;

/**
 * Represents a user with a name and a schedule.
 */
public interface IUser extends ReadonlyIUser {

  /**
   * Sets the name of the user.
   *
   * @param name the new name of the user.
   */
  void setName(String name);


  /**
   * Sets or updates the schedule for the user.
   *
   * @param schedule the new schedule to be associated with the user.
   */
  void setSchedule(ISchedule schedule);

  /**
   * Gets the modifiable schedule associated with the user.
   *
   * @return the modifiable schedule of the user.
   */
  ISchedule getModifiableSchedule();
}
