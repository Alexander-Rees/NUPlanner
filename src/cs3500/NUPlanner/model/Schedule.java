
package cs3500.NUPlanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents the schedule of a user in the planner system.
 * The schedule is a collection of events that a user has.
 */
public class Schedule implements ISchedule {
  private final List<IEvent> events;

  /**
   * Constructs a new cs3500.NUPlanner.model.Schedule.
   */
  public Schedule() {
    this.events = new ArrayList<>();
  }


  @Override
  public void addEvent(IEvent event) {
    if (event == null || hasConflict(event)) {
      throw new IllegalArgumentException("event can't be null or conflict");
    }
    events.add(event);
  }


  @Override
  public void removeEvent(IEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("event can't be null");
    }
    events.remove(event);
  }


  @Override
  public void updateEvent(IEvent oldEvent, IEvent newEvent) {
    if (oldEvent == null || newEvent == null || !events.contains(oldEvent)) {
      throw new IllegalArgumentException("Invalid events");
    }
    events.remove(oldEvent);

    if (hasConflict(newEvent)) {
      throw new IllegalArgumentException("event can't have conflict");
    }
    int index = events.indexOf(oldEvent);
    if (index == -1) {
      events.add(newEvent);
    } else {
      events.add(index, newEvent);
    }
  }


  @Override
  public List<ReadonlyIEvent> getEventsForDay(Day day) {
    if (day == null) {
      return new ArrayList<>();
    }
    return events.stream()
            .filter(event -> event.startDay() == day || event.endDay() == day)
            .collect(Collectors.toList());
  }


  @Override
  public boolean hasConflict(IEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("event can't be null");
    }
    return events.stream().anyMatch(existingEvent -> isOverlapping(existingEvent, event));
  }


  @Override
  public ArrayList<ReadonlyIEvent> getAllEvents() {
    return new ArrayList<>(events);
  }

  /**
   * Determines if two events overlap by converting the time and day into minutes and comparing.
   *
   * @param event1 The first event.
   * @param event2 The second event.
   * @return true if the events overlap, false otherwise.
   */
  private boolean isOverlapping(IEvent event1, IEvent event2) {
    int start1 = convertToMinutes(event1.startDay(), event1.startTime());
    int end1 = convertToMinutes(event1.endDay(), (event1.endTime() == 0) ? 1440 : event1.endTime());
    int start2 = convertToMinutes(event2.startDay(), event2.startTime());
    int end2 = convertToMinutes(event2.endDay(), (event2.endTime() == 0) ? 1440 : event2.endTime());

    if (event1.endDay().ordinal() < event1.startDay().ordinal()) {
      end1 += 7 * 24 * 60;
    }
    if (event2.endDay().ordinal() < event2.startDay().ordinal()) {
      end2 += 7 * 24 * 60;
    }
    boolean overlap = (start1 < end2) && (end1 > start2);

    return overlap;
  }

  /**
   * Converts a day and time into minutes for comparing.
   *
   * @param day  The day of the event.
   * @param time The time of the event in HHMM format.
   * @return The number of minutes from the start of the week.
   */
  private int convertToMinutes(Day day, int time) {
    return day.ordinal() * 24 * 60 + time / 100 * 60 + time % 100;
  }

}
