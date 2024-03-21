package cs3500.NUPlanner.view;

import java.util.List;
import java.util.Map;

import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;

public class View {

  public String generateView(ISchedule schedule) {
    StringBuilder view = new StringBuilder();
    for (Day day : Day.values()) {
      List<IEvent> dailyEvents = schedule.getEventsForDay(day);
      view.append(day.toString()).append(":\n");
      for (IEvent event : dailyEvents) {
        view.append("        Name: ").append(event.name()).append("\n");
        view.append("        Time: ").append(event.startDay()).append(": ")
                .append(String.format("%04d", event.startTime())).append(" -> ")
                .append(event.endDay()).append(": ")
                .append(String.format("%04d", event.endTime())).append("\n");
        view.append("        Location: ").append(event.location()).append("\n");
        view.append("        Online: ").append(event.online() ? "true" : "false").append("\n");
        view.append("        Invitees: ").append(String.join(", ", event.participants())).append("\n");
      }
    }
    return view.toString();
  }

}
