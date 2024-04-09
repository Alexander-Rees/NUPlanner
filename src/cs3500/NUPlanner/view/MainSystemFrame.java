package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.NUPlanner.controller.IFeatures;
import cs3500.NUPlanner.controller.ScheduleController;
import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.ReadonlyICentralSystem;
import cs3500.NUPlanner.model.ReadonlyIEvent;
import cs3500.NUPlanner.model.ReadonlyISchedule;
import cs3500.NUPlanner.model.ReadonlyIUser;

public class MainSystemFrame extends JFrame {
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private SchedulePanel schedulePanel;
  private EventFrame eventFrame;
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;
  private JComboBox<String> userList;
  private ReadonlyICentralSystem centralSystem;
  private IFeatures controller;

  public MainSystemFrame(ReadonlyICentralSystem centralSystem) {
    super("NUPlanner Main System");
    this.centralSystem = centralSystem;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    initializeComponents();
    initializeMenu();
    this.setSize(800, 600);
    this.setVisible(true);
    schedulePanel.setMainFrame(this);
  }


  private void initializeComponents() {
    createEventButton = new JButton("Create Event");
    scheduleEventButton = new JButton("Schedule Event");

    userList = new JComboBox<>();
    userList.addItem("<none>");
    userList.setActionCommand("User Selection Changed");

    JPanel bottomPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    bottomPanel.add(userList);
    bottomPanel.add(createEventButton);
    bottomPanel.add(scheduleEventButton);

    schedulePanel = new SchedulePanel();
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(schedulePanel, BorderLayout.CENTER);

    eventFrame = new EventFrame();
  }

  public void setController(IFeatures controller) {
    this.controller = controller;

    userList.addActionListener(e -> updateUserScheduleInView());
    createEventButton.addActionListener(e -> showEventSchedulingFrame());

    scheduleEventButton.addActionListener(e -> controller.scheduleEvent());
    loadMenuItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int option = fileChooser.showOpenDialog(MainSystemFrame.this);
      if (option == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        controller.loadScheduleFromXML(file.getPath());
      }
    });
    saveMenuItem.addActionListener(e -> {
      String selectedUser = getSelectedUser();
      if (selectedUser != null && !"<none>".equals(selectedUser)) {
        controller.saveScheduleToXML(selectedUser);
      }
    });

    if (eventFrame != null) {
      eventFrame.setController(controller);
    }


  }

  public void updateUserScheduleInView() {
    System.out.println("Updating view");
    String selectedUser = getSelectedUser();
    if (selectedUser != null && !"<none>".equals(selectedUser)) {
      ReadonlyIUser user = centralSystem.getUser(selectedUser);
      if (user != null) {
        ArrayList<ReadonlyIEvent> events = user.getSchedule().getAllEvents();
        schedulePanel.displaySchedule(events);
      } else {
        schedulePanel.displaySchedule(new ArrayList<>());
      }

    }
  }


  public void updateSchedule(String userName) {
    ReadonlyIUser user = centralSystem.getUser(userName);
    if (user != null) {
      ReadonlyISchedule schedule = user.getSchedule();
      schedulePanel.displaySchedule(schedule.getAllEvents());
    } else {
      schedulePanel.displaySchedule(new ArrayList<>());
    }
    this.revalidate();
    this.repaint();
  }

  public void setUserList(String[] userNames) {
    userList.setModel(new DefaultComboBoxModel<>(userNames));
    userList.setSelectedIndex(-1);
  }

  public String getSelectedUser() {
    return (String) userList.getSelectedItem();
  }



  public void showEventSchedulingFrame() {
    if (eventFrame == null) {
      eventFrame = new EventFrame();
    } else {
      eventFrame.resetForm();
    }
    String currentUser = getSelectedUser();
    eventFrame.setCurrentUser(currentUser);
    eventFrame.setLocationRelativeTo(this);
    eventFrame.setVisible(true);
  }

  private void initializeMenu() {
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    loadMenuItem = new JMenuItem("Load XML");
    saveMenuItem = new JMenuItem("Save XML");
    fileMenu.add(loadMenuItem);
    fileMenu.add(saveMenuItem);
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);
  }

  public void showEventDetails(ReadonlyIEvent event) {
    if (eventFrame == null) {
      eventFrame = new EventFrame();
    }
    eventFrame.populateEventDetails(event);
    eventFrame.setLocationRelativeTo(this);
    eventFrame.setVisible(true);
  }

}
