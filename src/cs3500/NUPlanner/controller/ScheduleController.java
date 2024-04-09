package cs3500.NUPlanner.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.NUPlanner.controller.XmlHandler;
import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.ReadonlyIEvent;
import cs3500.NUPlanner.model.ReadonlyISchedule;
import cs3500.NUPlanner.model.ReadonlyIUser;
import cs3500.NUPlanner.model.User;
import cs3500.NUPlanner.view.MainSystemFrame;

public class ScheduleController implements IFeatures {
  private CentralSystem model;
  private XmlHandler xmlHandler;
  private MainSystemFrame view;


  public ScheduleController(CentralSystem model, MainSystemFrame view) {
    this.model = model;
    this.view = view;

    this.view.setController(this);


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
          System.out.println("Skipping event due to conflict: " + event.name());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to load schedule from XML: " + filePath);
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
        System.out.println("Failed to write schedule for user: " + targetUserName);
        e.printStackTrace();
      }
    } else {
      System.out.println("cs3500.NUPlanner.model.User not found: " + targetUserName);
    }
  }

  @Override
  public void addEvent() {

  }

  @Override
  public void modifyEvent(String eventId) {

  }

  @Override
  public void removeEvent(String eventId) {

  }



  @Override
  public void scheduleEvent() {

  }

  @Override
  public void saveScheduleToXML(String selectedUser) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showSaveDialog(view);
    if (option == JFileChooser.APPROVE_OPTION) {
      File selectedDirectory = fileChooser.getSelectedFile();
      String directoryPath = selectedDirectory.getAbsolutePath();
      System.out.println("Selected directory: " + directoryPath);
      String targetUserName = view.getSelectedUser();
      if (!"<none>".equals(targetUserName)) {
        writeScheduleToXML(directoryPath, targetUserName);
      } else {
        System.out.println("No user selected.");
      }
    }

  }



  private void updateUsersInView() {
    List<ReadonlyIUser> users = model.getAllUsers();
    String[] userNames = users.stream().map(ReadonlyIUser::getName).toArray(String[]::new);
    view.setUserList(userNames);
  }



}
