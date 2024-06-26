package cs3500.NUPlanner.controller;

import java.util.List;
import java.util.Map;

import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ReadonlyIEvent;

public interface IFeatures {
    void loadScheduleFromXML(String filePaths);
    void writeScheduleToXML(String directoryPath, String targetUserName);
    void createEvent(Map<String, String> eventDetails);
    void modifyEvent(ReadonlyIEvent oldEvent, Map<String, String> eventDetails);
    void removeEvent(ReadonlyIEvent event);
    void scheduleEvent(String name, String location, boolean isOnline, int duration, List<String> participants);
    void saveScheduleToXML(String selectedUser);

}
