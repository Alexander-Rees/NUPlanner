package cs3500.NUPlanner.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import cs3500.NUPlanner.controller.XmlHandler;
import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.Event;
import cs3500.NUPlanner.model.IEvent;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.User;
import cs3500.NUPlanner.view.MainSystemFrame;

public class ScheduleController implements ActionListener {
  private CentralSystem model;
  private XmlHandler xmlHandler;
  private MainSystemFrame view; // The view

  public ScheduleController(CentralSystem model, MainSystemFrame view) {
    this.model = model;
    this.view = view;

    // Let the view know about the controller.
    this.view.setController(this);

    //this.view.updateEventList(model.getAllEvents());
  }

  public void loadSchedulesFromXML(List<String> filePaths) {
    XmlHandler xmlHandler = new XmlHandler();
    for (String filePath : filePaths) {
      try {
        Map<String, Object> result = xmlHandler.readSchedule(filePath);
        String userName = (String) result.get("userName");
        ISchedule loadedSchedule = (ISchedule) result.get("schedule");

        IUser user = model.getUser(userName);
        if (user == null) {
          user = new User(userName);
          model.addUser(user);
        }

        for (IEvent event : loadedSchedule.getAllEvents()) {
          try {
            user.getSchedule().addEvent(event);
          } catch (IllegalArgumentException e) {
            System.out.println("Skipping event due to conflict: " + event.name());
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  public void writeScheduleToXML(String directoryPath, String targetUserName) {
    IUser user = model.getUser(targetUserName);
    XmlHandler xmlHandler = new XmlHandler();
    if (user != null) {
      ISchedule schedule = user.getSchedule();
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
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if ("Add Event".equals(command)) {
      // Call the method on the view to display the scheduling panel
      view.showEventSchedulingPanel();
    } else if ("Remove Event".equals(command)) {
      // Handle remove event button click
    }
    // Add any other button command checks here
  }




}
