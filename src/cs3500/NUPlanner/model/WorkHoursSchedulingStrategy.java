package cs3500.NUPlanner.model;

import java.util.List;

public class WorkHoursSchedulingStrategy implements SchedulingStrategy {


  @Override
  public Event scheduleEvent(CentralSystem model, String name, String location, boolean isOnline, int duration, List<String> participants) {
    for (int dayIndex = 1; dayIndex < 6; dayIndex++) {
      for (int startMins = 540; startMins <= 1020 - duration; startMins++) {
        int totalStartMins = dayIndex * 1440 + startMins;

        if (isTimeSlotAvailableForAll(totalStartMins, duration, participants, model)) {
          Day startDay = Day.values()[dayIndex];
          int startHour = startMins / 60;
          int startMinute = startMins % 60;
          int startTime = startHour * 100 + startMinute;

          int endMins = totalStartMins + duration;
          Day endDay = Day.values()[dayIndex];
          int endHour = (endMins % 1440) / 60;
          int endMinute = (endMins % 1440) % 60;
          int endTime = endHour * 100 + endMinute;

          return new Event(name, startDay, startTime, endDay, endTime, isOnline, location, participants.get(0), participants);
        }
      }
    }
    throw new IllegalArgumentException("No available time slot could be found for the event within work hours.");
  }
  public boolean isTimeSlotAvailableForAll(int startMins, int duration, List<String> participants, CentralSystem model) {
    Day startDay = Day.values()[(startMins / 1440) % 7];
    int startHour = ((startMins % 1440) / 60);
    int startMinute = ((startMins % 1440) % 60);
    int startTime = startHour * 100 + startMinute;


    int endMins = startMins + duration;
    Day endDay = Day.values()[(endMins / 1440) % 7];
    int endHour = ((endMins % 1440) / 60);
    int endMinute = ((endMins % 1440) % 60);
    int endTime = endHour * 100 + endMinute;

    for (String participantName : participants) {
      IUser participant = model.getUser(participantName);
      if (participant == null || !participant.getSchedule().isTimeSlotAvailable(startDay, startTime, endDay, endTime)) {
        return false;
      }
    }
    return true;
  }
}