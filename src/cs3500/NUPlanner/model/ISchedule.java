package cs3500.NUPlanner.model;

import java.util.List;

import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.IEvent;

/**
 * Represents the schedule of a user.
 */
public interface ISchedule extends ReadonlyISchedule {

  /**
   * Adds an event to the schedule if there is no conflict.
   *
   * @param event The event to be added.
   * @throws IllegalArgumentException If the event is null or conflicts with existing events.
   */
  void addEvent(IEvent event);

  /**
   * Removes an event from the schedule.
   *
   * @param event The event to be removed.
   * @throws IllegalArgumentException If the event is null.
   */
  void removeEvent(IEvent event);

  /**
   * Updates an existing event with a new event.
   *
   * @param oldEvent The existing event to be replaced.
   * @param newEvent The new event to replace the old event.
   * @throws IllegalArgumentException If either event is null or the old event does not exist.
   */
  void updateEvent(IEvent oldEvent, IEvent newEvent);



}
