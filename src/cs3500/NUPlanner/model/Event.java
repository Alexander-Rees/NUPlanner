
package cs3500.NUPlanner.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.NUPlanner.model.Day;

/**
 * Represents an event with a specific start and end time, location, and participants.
 * Class Invariant: startTime must be less than or equal to endTime for events
 * within the same day. Both startTime and endTime must represent valid times
 * within a day (0000 to 2359). The participants list must not contain null or empty strings.
 */
public class Event implements IEvent {
  private String name;
  private Day startDay;
  private int startTime;
  private Day endDay;
  private int endTime;
  private boolean online;
  private String location;
  private String host;
  private List<String> participants;

  /**
   * Constructs a new cs3500.NUPlanner.model.Event.
   *
   * @param name         the name of the event
   * @param startDay     the day the event starts
   * @param startTime    the time the event starts (in HHMM format)
   * @param endDay       the day the event ends
   * @param endTime      the time the event ends (in HHMM format)
   * @param online       whether the event is online
   * @param location     the location of the event
   * @param host         the host of the event
   * @param participants a list of participants attending the event (First name is host)
   * @throws IllegalArgumentException if the time format is invalid or there is overlap
   */
  public Event(String name, Day startDay, int startTime, Day endDay, int endTime,
               boolean online, String location, String host, List<String> participants) {
    if ((startTime > endTime && startDay == endDay) || startTime < 0 || startTime > 2359 ||
            endTime < 0 || endTime > 2359) {
      throw new IllegalArgumentException("Invalid inputs");
    }
    this.name = name;
    this.startDay = startDay;
    this.startTime = startTime;
    this.endDay = endDay;
    this.endTime = endTime;
    this.online = online;
    this.location = location;
    this.host = host;
    this.participants = new ArrayList<>(participants);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;

  }

  @Override
  public Day startDay() {
    return startDay;
  }

  @Override
  public void setStartDay(Day startDay) {
    this.startDay = startDay;

  }

  @Override
  public int startTime() {
    return startTime;
  }

  @Override
  public void setStartTime(int startTime) {
    if (startTime > 2359 || startTime < 0 ||
            (endTime() < startTime && startDay() == endDay())) {
      throw new IllegalArgumentException("Invalid time");
    }
    this.startTime = startTime;

  }

  @Override
  public Day endDay() {
    return endDay;
  }

  @Override
  public void setEndDay(Day endDay) {
    this.endDay = endDay;

  }

  @Override
  public int endTime() {
    return endTime;
  }

  @Override
  public void setEndTime(int endTime) {
    if (endTime > 2359 || endTime < 0 ||
            (endTime < startTime() && startDay() == endDay())) {
      throw new IllegalArgumentException("Invalid time");
    }
    this.endTime = endTime;

  }

  @Override
  public boolean online() {
    return online;
  }

  @Override
  public void setOnline(boolean online) {
    this.online = online;

  }

  @Override
  public String location() {
    return location;
  }

  @Override
  public void setLocation(String location) {
    this.location = location;

  }


  @Override
  public List<String> participants() {
    return new ArrayList<>(participants);
  }

  @Override
  public void setParticipants(List<String> participants) {
    this.participants = new ArrayList<>(participants);
  }

  @Override
  public void addParticipant(String userId) {
    this.participants.add(userId);
  }

  @Override
  public void removeParticipant(String userId) {
    this.participants.remove(userId);
  }

  @Override
  public String getHost() {
    return host;
  }

  @Override
  public void setHost(String host) {
    this.host = host;
  }
}
