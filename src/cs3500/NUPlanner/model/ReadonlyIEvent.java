
package cs3500.NUPlanner.model;

import java.util.List;

public interface ReadonlyIEvent {

  /**
   * Returns the name of the event.
   *
   * @return the event name
   */
  String name();

  /**
   * Returns the start day of the event.
   *
   * @return the start day
   */
  Day startDay();

  /**
   * Returns the start time of the event.
   *
   * @return the start time in HHMM format
   */
  int startTime();

  /**
   * Returns the end day of the event.
   *
   * @return the end day
   */
  Day endDay();

  /**
   * Returns the end time of the event.
   *
   * @return the end time in HHMM format
   */
  int endTime();

  /**
   * Returns whether the event is online.
   *
   * @return true if the event is online, false otherwise
   */
  boolean online();

  /**
   * Returns the location of the event.
   *
   * @return the location
   */
  String location();


  /**
   * Returns the list of participants for the event.
   *
   * @return the list of participants
   */
  List<String> participants();

  /**
   * Returns the host of the event.
   *
   * @return the host
   */
  String getHost();
}
