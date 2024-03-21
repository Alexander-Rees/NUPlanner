package cs3500.NUPlanner.model;

import java.util.List;

import cs3500.NUPlanner.model.Day;

/**
 * Represents an event.
 */
public interface IEvent extends ReadonlyIEvent {



  /**
   * Sets the name of the event.
   *
   * @param name the new name of the event
   */
  void setName(String name);


  /**
   * Sets the start day of the event.
   *
   * @param startDay the new start day
   */
  void setStartDay(Day startDay);


  /**
   * Sets the start time of the event.
   *
   * @param startTime the new start time in HHMM format
   * @throws IllegalArgumentException if the provided time is invalid
   */
  void setStartTime(int startTime);


  /**
   * Sets the end day of the event.
   *
   * @param endDay the new end day
   */
  void setEndDay(Day endDay);



  /**
   * Sets the end time of the event.
   *
   * @param endTime the new end time in HHMM format
   * @throws IllegalArgumentException if the provided time is invalid
   */
  void setEndTime(int endTime);



  /**
   * Sets the online status of the event.
   *
   * @param online the new online status
   */
  void setOnline(boolean online);


  /**
   * Sets the location of the event.
   *
   * @param location the new location
   */
  void setLocation(String location);


  /**
   * Sets the list of participants for the event.
   *
   * @param participants the new list of participants
   */
  void setParticipants(List<String> participants);

  /**
   * Adds a participant to the event.
   *
   * @param userId the name of the participant to add
   */
  void addParticipant(String userId);

  /**
   * Removes a participant from the event.
   *
   * @param userId the name of the participant to remove
   */
  void removeParticipant(String userId);


  /**
   * Sets the host of the event.
   *
   * @param host the new host
   */
  void setHost(String host);
}
