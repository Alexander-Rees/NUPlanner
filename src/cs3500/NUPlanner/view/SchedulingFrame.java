package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.NUPlanner.controller.IFeatures;

public class SchedulingFrame extends JFrame implements IViewScheduleFrame {
  private JTextField eventNameField;
  private JTextField locationField;
  private JCheckBox isOnlineCheckBox;
  private JTextField eventDurationField;
  private JList<String> usersList;
  private DefaultListModel<String> usersListModel;
  private JButton scheduleButton;
  private JTextArea participantsTextArea;
  private IFeatures controller;
  private ActionListener currentScheduleListener;


  public SchedulingFrame() {
    this.setLayout(new BorderLayout(10, 10));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(400, 600);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    eventNameField = new JTextField();
    locationField = new JTextField();
    isOnlineCheckBox = new JCheckBox("Is online");
    eventDurationField = new JTextField(5);

    participantsTextArea = new JTextArea(5, 20);
    participantsTextArea.setLineWrap(true);
    participantsTextArea.setWrapStyleWord(true);
    JScrollPane participantsScrollPane = new JScrollPane(participantsTextArea);



    mainPanel.add(createLabelledField("Event name:", eventNameField));
    mainPanel.add(createLabelledField("Location:", locationField));
    mainPanel.add(isOnlineCheckBox);
    mainPanel.add(createLabelledField("Duration (minutes):", eventDurationField));
    mainPanel.add(createLabelledField("Participants (one per line):", participantsScrollPane));




    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    scheduleButton = new JButton("Schedule event");
    buttonPanel.add(scheduleButton);

    this.add(mainPanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.pack();
  }

  private JPanel createLabelledField(String label, Component field) {
    JPanel panel = new JPanel(new BorderLayout(5, 0));
    panel.add(new JLabel(label), BorderLayout.WEST);
    panel.add(field, BorderLayout.CENTER);
    return panel;
  }


  public void setScheduleController(IFeatures controller) {
    this.controller = controller;

    if (currentScheduleListener != null) {
      scheduleButton.removeActionListener(currentScheduleListener);
    }

    currentScheduleListener = e -> {
      Map<String, String> info = getSchedulingCriteria();
      int durationMinutes = Integer.parseInt(info.get("duration"));
      String eventName = info.get("eventName");
      String location = info.get("location");
      boolean isOnline = Boolean.parseBoolean(info.get("isOnline"));
      ArrayList<String> participants = new ArrayList<>(Arrays.asList(participantsTextArea.getText().split("\\n")));

      controller.scheduleEvent(eventName, location, isOnline, durationMinutes, participants);

      SchedulingFrame.this.dispose();
    };
    scheduleButton.addActionListener(currentScheduleListener);
  }


  private Map<String, String> getSchedulingCriteria() {
    Map<String, String> criteria = new HashMap<>();
    criteria.put("eventName", eventNameField.getText().trim());
    criteria.put("location", locationField.getText().trim());
    criteria.put("isOnline", String.valueOf(isOnlineCheckBox.isSelected()));
    criteria.put("duration", eventDurationField.getText().trim());
    return criteria;
  }

  public void resetForm() {
    eventNameField.setText("");
    locationField.setText("");
    isOnlineCheckBox.setSelected(false);
    eventDurationField.setText("");
  }
}
