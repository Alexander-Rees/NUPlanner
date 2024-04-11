package cs3500.NUPlanner.model;

import java.util.ArrayList;
import java.util.List;

public interface ReadonlyISchedule {


  /**
   * Retrieves a list of events for a specific day.
   *
   * @param day The day for which events are requested.
   * @return A list of events for the specified day.
   */
  List<ReadonlyIEvent> getEventsForDay(Day day);
  /**
   * Checks if adding a new event would cause a conflict with existing events.
   *
   * @param event The  event to check for conflicts.
   * @return true if there is a conflict, false otherwise.
   * @throws IllegalArgumentException If the event is null.
   */
  boolean hasConflict(IEvent event);

  /**
   * Retrieves all events in the schedule.
   *
   * @return A list of all events.
   */
  ArrayList<ReadonlyIEvent> getAllEvents();

  boolean isTimeSlotAvailable(Day startDay, int startTime, Day endDay, int endTime);
}
