package cs3500.NUPlanner.model;

import java.util.List;

/**
 * cs3500.NUPlanner.model.CentralSystem manages users and their schedules.
 * It allows for the creation, update, and deletion of events in user schedules
 * and supports importing and exporting user schedules via XML.
 * Also creates a text view of the planner.
 */
public interface ICentralSystem extends ReadonlyICentralSystem {

  /**
   * Adds a user to the system.
   *
   * @param user The user being added. Can't be null and must not already exist.
   * @throws IllegalArgumentException If the user is null or already exists.
   */
  void addUser(IUser user);

  /**
   * Removes a user from the system.
   *
   * @param name The name of the user to remove.
   * @throws IllegalArgumentException If the user does not exist in the system.
   */
  void removeUser(String name);


  /**
   * Creates an event and adds it to the specified user's schedule.
   * Also adds the event to all participant's schedules.
   *
   * @param name  The name of the user creating the event.
   * @param event The event to add. Must not be null.
   * @throws IllegalArgumentException If the user does not exist or the event is null.
   */
  void createEvent(String name, IEvent event);

  /**
   * Updates an existing event in the user's schedule.
   * Also updates the event in all participant's schedules.
   *
   * @param name     The name of the user updating the event.
   * @param oldEvent The original event to be replaced.
   * @param newEvent The new event to replace with.
   * @throws IllegalArgumentException If the user does not exist or the events are invalid.
   */
  void updateEvent(String name, ReadonlyIEvent oldEvent, IEvent newEvent);


  /**
   * Deletes an event from the user's schedule.
   * If the user is the host, also deletes the event from all participant's schedules.
   *
   * @param name  The name of the user (host) deleting the event.
   * @param event The event to delete.
   * @throws IllegalArgumentException If the user does not exist or is not the host.
   */
  void deleteEvent(String name, ReadonlyIEvent event);

  /**
   * Loads schedules from a list of XML files.
   *
   * @param filePaths A list of strings which are the file paths.
   * @throws Exception if an error occurs during file reading or processing.
   */
//  void loadSchedulesFromXML(List<String> filePaths);

  /**
   * Writes a user's schedule to an XML file at a specified directory.
   *
   * @param directoryPath  The directory path where the XML file will be saved.
   * @param targetUserName The name of the user whose schedule is to be written to an XML file.
   * @throws Exception if an error occurs during file writing.
   */
 // void writeScheduleToXML(String directoryPath, String targetUserName);

  /**
   * Generates a textual representation of all users and their schedules within the system.
   *
   * @return A string representing the textual view of all users' schedules in the system.
   */
 // String getTextualView();

}

