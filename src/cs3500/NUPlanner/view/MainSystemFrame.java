package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.NUPlanner.model.ISchedule;

public class MainSystemFrame extends JFrame implements IViewSystemFrame {
  private JButton addEventButton;
  private JButton removeEventButton;
  private SchedulePanel schedulePanel; // Using the custom SchedulePanel class

  public MainSystemFrame() {
    super("NUPlanner Main System");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    initializeComponents();

    this.setSize(800, 600);
    this.setVisible(true);
  }

  private void initializeComponents() {
    addEventButton = new JButton("Add Event");
    removeEventButton = new JButton("Remove Event");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addEventButton);
    buttonPanel.add(removeEventButton);

    schedulePanel = new SchedulePanel();
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.add(schedulePanel, BorderLayout.CENTER);
  }

  public void setController(ActionListener controller) {
    addEventButton.addActionListener(controller);
    removeEventButton.addActionListener(controller);
  }

  public void displaySchedule(ISchedule schedule) {
    schedulePanel.displaySchedule(schedule);
  }
}

