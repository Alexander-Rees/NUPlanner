package cs3500.NUPlanner.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.ReadonlyIEvent;
import cs3500.NUPlanner.model.ReadonlyISchedule;
import cs3500.NUPlanner.model.ReadonlyIUser;
import cs3500.NUPlanner.model.SchedulingStrategy;
import cs3500.NUPlanner.model.User;
import cs3500.NUPlanner.view.MainSystemFrame;

public class ScheduleController implements IFeatures {
  private CentralSystem model;
  private XmlHandler xmlHandler;
  private MainSystemFrame view;
  private SchedulingStrategy schedulingStrategy;


  public ScheduleController(CentralSystem model, MainSystemFrame view) {
    this.model = model;
    this.view = view;

  }

  public void setSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
    this.schedulingStrategy = schedulingStrategy;
  }



  @Override
  public void loadScheduleFromXML(String filePath) {
    XmlHandler xmlHandler = new XmlHandler();
    try {
      Map<String, Object> result = xmlHandler.readSchedule(filePath);
      String userName = (String) result.get("userName");
      ISchedule loadedSchedule = (ISchedule) result.get("schedule");

      IUser user = model.getUser(userName);
      if (user == null) {
        user = new User(userName);
        model.addUser(user);
      }

      for (ReadonlyIEvent event : loadedSchedule.getAllEvents()) {
        try {
          user.getModifiableSchedule().addEvent((IEvent) event);
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, "Skipping event due to conflict: " + event.name(), "Event Conflict", JOptionPane.WARNING_MESSAGE);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Failed to load schedule from XML: " + filePath, "Loading Error", JOptionPane.ERROR_MESSAGE);
    }
    updateUsersInView();
  }


  public void writeScheduleToXML(String directoryPath, String targetUserName) {
    IUser user = model.getUser(targetUserName);
    XmlHandler xmlHandler = new XmlHandler();
    if (user != null) {
      ReadonlyISchedule schedule = user.getSchedule();
      String filePath = directoryPath + "/" + targetUserName + "-schedule.xml";

      try {
        xmlHandler.writeSchedule(schedule, filePath, targetUserName);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Failed to write schedule for user: " + targetUserName, "Writing Error", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null, "User not found: " + targetUserName, "User Not Found", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void createEvent(Map<String, String> eventDetails) {
    try {
      String eventName = eventDetails.get("eventName");
      Day startDay = Day.valueOf(eventDetails.get("startDay").toUpperCase());
      int startTime = Integer.parseInt(eventDetails.get("startTime").replace(":", ""));
      Day endDay = Day.valueOf(eventDetails.get("endDay").toUpperCase());
      int endTime = Integer.parseInt(eventDetails.get("endTime").replace(":", ""));
      boolean isOnline = Boolean.parseBoolean(eventDetails.get("isOnline"));
      String location = eventDetails.get("location");
      String host = eventDetails.get("host");
      List<String> participants = new ArrayList<>(Arrays.asList(eventDetails.get("participants").split(",")));
      Event newEvent = new Event(eventName, startDay, startTime, endDay, endTime, isOnline, location, host, participants);


      model.createEvent(view.getSelectedUser(), newEvent);

      view.updateUserScheduleInView();
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, "Error creating event: " + e.getMessage(), "Creation Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void modifyEvent(ReadonlyIEvent oldEvent, Map<String, String> eventDetails) {
    try {
      if (oldEvent == null) {
        JOptionPane.showMessageDialog(null, "Old event empty");
      }


      String eventName = eventDetails.get("eventName");
      Day startDay = Day.valueOf(eventDetails.get("startDay").toUpperCase());
      int startTime = Integer.parseInt(eventDetails.get("startTime").replace(":", ""));
      Day endDay = Day.valueOf(eventDetails.get("endDay").toUpperCase());
      int endTime = Integer.parseInt(eventDetails.get("endTime").replace(":", ""));
      boolean isOnline = Boolean.parseBoolean(eventDetails.get("isOnline"));
      String location = eventDetails.get("location");
      String host = eventDetails.get("host");
      List<String> participants = new ArrayList<>(Arrays.asList(eventDetails.get("participants").split(",")));
      Event newEvent = new Event(eventName, startDay, startTime, endDay, endTime, isOnline, location, host, participants);


      model.updateEvent(view.getSelectedUser(), oldEvent, newEvent);

      view.updateUserScheduleInView();
    } catch(IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, "Error modifying event: " + e.getMessage(), "Modifying Error", JOptionPane.ERROR_MESSAGE);
    }
  }


  @Override
  public void removeEvent(ReadonlyIEvent event) {
    try {
      model.deleteEvent(view.getSelectedUser(), event);

      view.updateUserScheduleInView();
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, "Error removing event: " +
              e.getMessage(), "Removing Error", JOptionPane.ERROR_MESSAGE);
    }

  }

  @Override
  public void scheduleEvent(String name, String location, boolean isOnline, int duration, List<String> participants) {
    try {
       Event scheduledEvent = this.schedulingStrategy.scheduleEvent(this.model, name, location,
              isOnline, duration, participants);
       model.createEvent(view.getSelectedUser(), scheduledEvent);

      view.updateUserScheduleInView();
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, "Error scheduling event: " +
              e.getMessage(), "Scheduling Error", JOptionPane.ERROR_MESSAGE);
    }


  }


  @Override
  public void saveScheduleToXML(String selectedUser) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showSaveDialog(view);
    if (option == JFileChooser.APPROVE_OPTION) {
      File selectedDirectory = fileChooser.getSelectedFile();
      String directoryPath = selectedDirectory.getAbsolutePath();
      String targetUserName = view.getSelectedUser();
      if (!"<none>".equals(targetUserName)) {
        writeScheduleToXML(directoryPath, targetUserName);
      } else {
        JOptionPane.showMessageDialog(null, "No User selected " + targetUserName, "User Error", JOptionPane.ERROR_MESSAGE);
      }
    }

  }



  private void updateUsersInView() {
    List<ReadonlyIUser> users = model.getAllUsers();
    String[] userNames = users.stream().map(ReadonlyIUser::getName).toArray(String[]::new);
    view.setUserList(userNames);
  }



}
