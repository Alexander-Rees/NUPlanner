package cs3500.NUPlanner.controller;

import java.util.Map;

import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.ReadonlyISchedule;

/**
 * Represents the xml handler.
 */
public interface IXmlHandler {


  /**
   * Reads a schedule from an XML file.
   *
   * @param filePath the path to the XML file containing the schedule.
   * @return a map with the user's name and their schedule extracted from the XML file.
   * @throws Exception if an error occurs while parsing the XML file.
   */
  Map<String, Object> readSchedule(String filePath) throws Exception;

  /**
   * Writes a schedule to an XML file.
   *
   * @param schedule the schedule to write to the file.
   * @param filePath the file path where the schedule will be written.
   * @param userName the name of the user that the schedule belongs to.
   * @throws Exception if an error occurs during the writing process.
   */
  void writeSchedule(ReadonlyISchedule schedule, String filePath, String userName) throws Exception;


}
