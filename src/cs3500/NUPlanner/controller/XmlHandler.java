package cs3500.NUPlanner.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.ReadonlyIEvent;
import cs3500.NUPlanner.model.ReadonlyISchedule;
import cs3500.NUPlanner.model.Schedule;
import cs3500.NUPlanner.model.User;

/**
 * Handles the reading and writing of schedules and events to and from XML format.
 */
public class XmlHandler implements IXmlHandler {

  /**
   * Reads a schedule from an XML file and creates a schedule object.
   */
  public Map<String, Object> readSchedule(String filePath) throws Exception {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document document = builder.parse(new File(filePath));
    document.getDocumentElement().normalize();

    ISchedule schedule = new Schedule();

    String userName = document.getDocumentElement().getAttribute("id");

    NodeList eventNodes = document.getElementsByTagName("event");
    for (int i = 0; i < eventNodes.getLength(); i++) {
      Element eventElement = (Element) eventNodes.item(i);
      IEvent event = extractEventFromElement(eventElement);
      schedule.addEvent(event);
    }

    Map<String, Object> result = new HashMap<>();
    result.put("userName", userName);
    result.put("schedule", schedule);
    return result;
  }


  @Override
  public void writeSchedule(ReadonlyISchedule schedule, String filePath, String userName) throws Exception {
    try (FileWriter file = new FileWriter(filePath)) {
      file.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      file.write("<schedule id=\"" + userName + "\">\n");

      List<ReadonlyIEvent> events = schedule.getAllEvents();
      for (ReadonlyIEvent event : events) {
        file.write("\t<event>\n");
        file.write("\t\t<name>" + event.name() + "</name>\n");

        file.write("\t\t<time>\n");
        file.write("\t\t\t<start-day>" + event.startDay().toString() + "</start-day>\n");
        file.write("\t\t\t<start>" + event.startTime() + "</start>\n");
        file.write("\t\t\t<end-day>" + event.endDay().toString() + "</end-day>\n");
        file.write("\t\t\t<end>" + event.endTime() + "</end>\n");
        file.write("\t\t</time>\n");

        file.write("\t\t<location>\n");
        file.write("\t\t\t<online>" + event.online() + "</online>\n");
        file.write("\t\t\t<place>" + event.location() + "</place>\n");
        file.write("\t\t</location>\n");

        if (event.participants() != null && !event.participants().isEmpty()) {
          file.write("\t\t<users>\n");
          for (String participant : event.participants()) {
            file.write("\t\t\t<uid>" + participant + "</uid>\n");
          }
          file.write("\t\t</users>\n");
        }

        file.write("\t</event>\n");
      }

      file.write("</schedule>\n");
    } catch (IOException ex) {
      throw new RuntimeException("Error writing schedule to file: " + ex.getMessage(), ex);
    }
  }


  /**
   * Extracts event information from an XML element and creates an cs3500.NUPlanner.model.IEvent.
   *
   * @param eventElement the XML element containing the event details.
   * @return the event extracted from the provided XML element.
   */
  private static IEvent extractEventFromElement(Element eventElement) {
    String name = eventElement.getElementsByTagName("name").item(0).getTextContent().
            replaceAll("\"", "");
    Day startDay = Day.valueOf(eventElement.getElementsByTagName("start-day").item(0).
            getTextContent().toUpperCase());
    int startTime = Integer.parseInt(eventElement.getElementsByTagName("start").item(0).
            getTextContent());
    Day endDay = Day.valueOf(eventElement.getElementsByTagName("end-day").item(0).
            getTextContent().toUpperCase());
    int endTime = Integer.parseInt(eventElement.getElementsByTagName("end").item(0).
            getTextContent());

    Element locationElement = (Element) eventElement.getElementsByTagName("location").item(0);
    String location = locationElement.getElementsByTagName("place").getLength() > 0
            ? locationElement.getElementsByTagName("place").item(0).getTextContent().
            replaceAll("\"", "")
            : "";
    boolean online = Boolean.parseBoolean(locationElement.getElementsByTagName("online").
            item(0).getTextContent());

    NodeList userNodes = eventElement.getElementsByTagName("users").item(0).getChildNodes();
    List<String> participants = new ArrayList<>();
    for (int i = 0; i < userNodes.getLength(); i++) {
      if (userNodes.item(i) instanceof Element && "uid".equals(userNodes.item(i).getNodeName())) {
        String userId = userNodes.item(i).getTextContent().replaceAll("\"", "");
        participants.add(userId);
      }
    }

    String host = participants.isEmpty() ? "" : participants.get(0);

    Event event = new Event(name, startDay,
            startTime, endDay, endTime, online, location, host, participants);
    return event;
  }






}
