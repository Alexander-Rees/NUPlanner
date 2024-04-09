package cs3500.NUPlanner.model;

import java.time.Duration;
import java.util.List;

public interface SchedulingStrategy {
  Event scheduleEvent(Duration duration, List<User> participants);
}
