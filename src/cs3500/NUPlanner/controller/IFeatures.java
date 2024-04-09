package cs3500.NUPlanner.controller;

public interface IFeatures {
    void loadScheduleFromXML(String filePaths);
    void writeScheduleToXML(String directoryPath, String targetUserName);
    void addEvent();
    void modifyEvent(String eventId);
    void removeEvent(String eventId);
    void scheduleEvent();
    void saveScheduleToXML(String selectedUser);
}
