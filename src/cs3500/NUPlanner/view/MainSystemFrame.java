package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.NUPlanner.model.CentralSystem;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.IUser;
import cs3500.NUPlanner.model.ReadonlyICentralSystem;
import cs3500.NUPlanner.model.ReadonlyIEvent;
import cs3500.NUPlanner.model.ReadonlyISchedule;
import cs3500.NUPlanner.model.ReadonlyIUser;

public class MainSystemFrame extends JFrame {
  private JButton addEventButton;
  private JButton removeEventButton;
  private SchedulePanel schedulePanel;
  private EventFrame eventFrame;
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;
  private JComboBox<String> userList;
  private ReadonlyICentralSystem centralSystem;
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
    addEventButton = new JButton("Add Event");
    removeEventButton = new JButton("Schedule Event");

    userList = new JComboBox<>();
    userList.addItem("<none>");
    userList.setActionCommand("User Selection Changed");

    JPanel bottomPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    bottomPanel.add(userList);
    bottomPanel.add(addEventButton);
    bottomPanel.add(removeEventButton);

    schedulePanel = new SchedulePanel();
    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(schedulePanel, BorderLayout.CENTER);

    eventFrame = new EventFrame();
  }

  public void setController(ActionListener controller) {
    addEventButton.addActionListener(controller);
    removeEventButton.addActionListener(controller);
    eventFrame.setController(controller);
    userList.addActionListener(controller);
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
    }
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

  public void setMenuController(ActionListener controller) {
    addEventButton.addActionListener(e -> showEventSchedulingFrame());
    loadMenuItem.addActionListener(controller);
    saveMenuItem.addActionListener(controller);
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
