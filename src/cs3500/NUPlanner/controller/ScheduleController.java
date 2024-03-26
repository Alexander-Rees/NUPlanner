
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

public class ScheduleController implements ActionListener {
  private CentralSystem model;
  private XmlHandler xmlHandler;
  private MainSystemFrame view;


  public ScheduleController(CentralSystem model, MainSystemFrame view) {
    this.model = model;
    this.view = view;

    this.view.setController(this);


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

        for (ReadonlyIEvent event : loadedSchedule.getAllEvents()) {
          try {
            user.getModifiableSchedule().addEvent((IEvent) event);
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
  public void actionPerformed(ActionEvent e) {
    System.out.println("Action performed: " + e.getActionCommand());

    String command = e.getActionCommand();
    if ("Load XML".equals(command)) {
      System.out.println("Load XML command received.");
      loadXmlFile();
    } else if ("Save XML".equals(command)) {
      System.out.println("Save XML command received.");
      // saveXmlFile();
    } else if ("Add Event".equals(command)) {
      System.out.println("Add Event command received.");
      view.showEventSchedulingFrame();
    } else if ("Schedule Event".equals(command)) {
      System.out.println("Schedule Event command received.");
    } switch (e.getActionCommand()) {
      case "User Selection Changed":
        updateUserScheduleInView();
        break;
    }
  }

  private void updateUserScheduleInView() {
    String selectedUser = view.getSelectedUser();
    if (selectedUser != null && !selectedUser.equals("<none>")) {
      ReadonlyIUser user = model.getUser(selectedUser);
      if (user != null) {
        view.clearSchedulePanel();
        List<ReadonlyIEvent> events = user.getSchedule().getAllEvents();
        view.updateSchedule(user.getName());
      }
    }
  }


  private void updateUsersInView() {
    List<ReadonlyIUser> users = model.getAllUsers();
    String[] userNames = users.stream().map(ReadonlyIUser::getName).toArray(String[]::new);
    view.setUserList(userNames);
  }


  private void loadXmlFile() {
    System.out.println("Preparing to show open dialog.");
    JFileChooser fileChooser = new JFileChooser();
    int option = fileChooser.showOpenDialog(view);
    System.out.println("Dialog closed with option: " + option);
    if (option == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      System.out.println("Selected file: " + selectedFile.getAbsolutePath());
      loadSchedulesFromXML(Collections.singletonList(selectedFile.getAbsolutePath()));
    }
    updateUsersInView();
  }

}
