package cs3500.NUPlanner.model;

import java.util.List;


public class AnyTimeSchedulingStrategy implements SchedulingStrategy {
  @Override
  public Event scheduleEvent(CentralSystem model, String name, String location, boolean isOnline, int duration, List<String> participants) {
    for (int startMins = 0; startMins < 10080; startMins++) {
      if (isTimeSlotAvailableForAll(startMins, duration, participants, model)) {
        int endMins = startMins + duration;

        Day startDay = Day.values()[(startMins / 1440) % 7];
        int startHour = (startMins % 1440) / 60;
        int startMinute = (startMins % 1440) % 60;
        int startTime = startHour * 100 + startMinute;

        Day endDay = Day.values()[(endMins / 1440) % 7];
        int endHour = (endMins % 1440) / 60;
        int endMinute = (endMins % 1440) % 60;
        int endTime = endHour * 100 + endMinute;

        return new Event(name, startDay, startTime, endDay, endTime, isOnline, location, participants.get(0), participants);
      }
    }
    throw new IllegalArgumentException("No available time slot could be found for the event.");
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
