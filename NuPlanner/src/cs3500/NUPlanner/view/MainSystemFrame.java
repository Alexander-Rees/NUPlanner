package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.NUPlanner.model.ISchedule;

public class MainSystemFrame extends JFrame implements IViewSystemFrame {
  private JButton addEventButton;
  private JButton removeEventButton;
  private SchedulePanel schedulePanel;
  private EventPanel eventPanel;
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;

  public MainSystemFrame() {
    super("NUPlanner Main System");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());


    initializeComponents();
    initializeMenu();
    this.setSize(800, 600);
    this.setVisible(true);
  }

  private void initializeComponents() {
    addEventButton = new JButton("Add Event");
    removeEventButton = new JButton("Schedule Event");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addEventButton);
    buttonPanel.add(removeEventButton);

    schedulePanel = new SchedulePanel();
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.add(schedulePanel, BorderLayout.CENTER);

    eventPanel = new EventPanel();



  }

  public void setController(ActionListener controller) {
    addEventButton.addActionListener(controller);
    removeEventButton.addActionListener(controller);
    eventPanel.setController(controller);
  }


  public void updateSchedule(ISchedule schedule) {
    schedulePanel.displaySchedule(schedule);
  }

  public void showEventSchedulingPanel() {
    JDialog eventDialog = new JDialog(this, "Event Details", true);
    eventDialog.setLayout(new BorderLayout());
    eventDialog.add(eventPanel, BorderLayout.CENTER);
    eventDialog.pack();
    eventDialog.setLocationRelativeTo(this);
    eventDialog.setVisible(true);
  }

  private void initializeMenu() {
    menuBar = new JMenuBar();

    fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    loadMenuItem = new JMenuItem("Load XML");
    saveMenuItem = new JMenuItem("Save XML");
    fileMenu.add(loadMenuItem);
    fileMenu.add(saveMenuItem);

    this.setJMenuBar(menuBar);
  }

  public void setMenuController(ActionListener controller) {
    loadMenuItem.addActionListener(controller);
    saveMenuItem.addActionListener(controller);
  }




  public void displaySchedule(ISchedule schedule) {
    schedulePanel.displaySchedule(schedule);
  }
}

