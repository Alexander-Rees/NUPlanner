package cs3500.NUPlanner.model;

public interface ReadonlyIUser {

  /**
   * Returns the name of the user.
   *
   * @return the name of the user.
   */
  String getName();

  /**
   * Gets the schedule associated with the user.
   *
   * @return the schedule of the user.
   */
  ReadonlyISchedule getSchedule();
}
