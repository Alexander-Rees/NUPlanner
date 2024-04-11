package cs3500.NUPlanner.model;

import java.util.List;



public interface SchedulingStrategy {
  Event scheduleEvent(CentralSystem model, String name, String location, boolean isOnline,
                      int duration, List<String> participants);
}
