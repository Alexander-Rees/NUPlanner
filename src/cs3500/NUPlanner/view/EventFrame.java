package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.NUPlanner.controller.IFeatures;
import cs3500.NUPlanner.model.ReadonlyIEvent;

public class EventFrame extends JFrame implements IViewEventFrame {
  private JTextField eventNameField;
  private JTextField locationField;
  private JCheckBox isOnlineCheckBox;
  private JComboBox<String> startingDayCombo;
  private JTextField startingTimeField;
  private JComboBox<String> endingDayCombo;
  private JTextField endingTimeField;
  private JList<String> usersList;
  private DefaultListModel<String> usersListModel;
  private JButton modifyEventButton;
  private JButton removeEventButton;
  private JButton createEventButton;
  private String currentUserName;
  private IFeatures controller;
  private JTextArea participantsTextArea;
  private ReadonlyIEvent event;
  private ActionListener createEventListener;
  private ActionListener modifyEventListener;
  private ActionListener removeEventListener;



  public EventFrame() {
    this.setLayout(new BorderLayout(10, 10));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(400, 600);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    eventNameField = new JTextField();
    locationField = new JTextField();
    isOnlineCheckBox = new JCheckBox("Is online");

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    startingDayCombo = new JComboBox<>(days);
    startingTimeField = new JTextField(5);
    endingDayCombo = new JComboBox<>(days);
    endingTimeField = new JTextField(5);



    participantsTextArea = new JTextArea(5, 20);
    participantsTextArea.setLineWrap(true);
    participantsTextArea.setWrapStyleWord(true);

    JScrollPane participantsScrollPane = new JScrollPane(participantsTextArea);


    mainPanel.add(createLabelledField("Event name:", eventNameField));
    mainPanel.add(createLabelledField("Location:", locationField));
    mainPanel.add(isOnlineCheckBox);
    mainPanel.add(createLabelledField("Starting Day:", startingDayCombo));
    mainPanel.add(createLabelledField("Starting time:", startingTimeField));
    mainPanel.add(createLabelledField("Ending Day:", endingDayCombo));
    mainPanel.add(createLabelledField("Ending time:", endingTimeField));
    mainPanel.add(createListWithLabel("Available users", usersList));

    mainPanel.add(participantsScrollPane);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    createEventButton = new JButton("Create event");
    modifyEventButton = new JButton("Modify event");
    removeEventButton = new JButton("Remove event");
    buttonPanel.add(modifyEventButton);
    buttonPanel.add(removeEventButton);
    buttonPanel.add(createEventButton);

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

  private JScrollPane createListWithLabel(String label, JList<String> list) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel(label), BorderLayout.NORTH);
    JScrollPane scrollPane = new JScrollPane(list);
    panel.add(scrollPane, BorderLayout.CENTER);
    return scrollPane;
  }

  public void setController(IFeatures controller) {
    this.controller = controller;

    if (createEventListener != null) {
      createEventButton.removeActionListener(createEventListener);
    }
    if (modifyEventListener != null) {
      modifyEventButton.removeActionListener(modifyEventListener);
    }
    if (removeEventListener != null) {
      removeEventButton.removeActionListener(removeEventListener);
    }
    createEventListener = e -> {
      controller.createEvent(getEventDetails());
      EventFrame.this.dispose();
    };
    createEventButton.addActionListener(createEventListener);

    modifyEventListener = e -> controller.modifyEvent(this.event, getEventDetails());
    EventFrame.this.dispose();
    modifyEventButton.addActionListener(modifyEventListener);

    removeEventListener = e -> {controller.removeEvent(this.event);
      EventFrame.this.dispose();};
    removeEventButton.addActionListener(removeEventListener);

  }

  public void showFrame() {
    this.resetForm();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  public void hideFrame() {
    this.setVisible(false);
  }

  public Map<String, String> getEventDetails() {
    Map<String, String> details = new HashMap<>();
    details.put("eventName", eventNameField.getText().trim());
    details.put("location", locationField.getText().trim());
    details.put("isOnline", String.valueOf(isOnlineCheckBox.isSelected()));
    details.put("startDay", (String) startingDayCombo.getSelectedItem());
    details.put("startTime", startingTimeField.getText().trim().replaceAll(":", ""));
    details.put("endDay", (String) endingDayCombo.getSelectedItem());
    details.put("endTime", endingTimeField.getText().trim().replaceAll(":", ""));
    details.put("host", currentUserName);
    String participants = participantsTextArea.getText().trim();
    details.put("participants", participants);
    return details;
  }





  public void populateEventDetails(ReadonlyIEvent event) {
    this.event = event;
    eventNameField.setText(event.name());
    locationField.setText(event.location());
    isOnlineCheckBox.setSelected(event.online());

    startingDayCombo.setSelectedItem(toTitleCase(event.startDay().toString()));
    startingTimeField.setText(String.format("%04d", event.startTime()));
    endingDayCombo.setSelectedItem(toTitleCase(event.endDay().toString()));
    endingTimeField.setText(String.format("%04d", event.endTime()));

    StringBuilder participantsBuilder = new StringBuilder();
    for (String participant : event.participants()) {
      participantsBuilder.append(participant).append("\n");
    }
    participantsTextArea.setText(participantsBuilder.toString().trim());
  }



  private String toTitleCase(String day) {
    if (day == null || day.isEmpty()) {
      return day;
    }
    return day.charAt(0) + day.substring(1).toLowerCase();
  }

  public void resetForm() {
    eventNameField.setText("");
    locationField.setText("");
    isOnlineCheckBox.setSelected(false);
    startingDayCombo.setSelectedIndex(-1);
    startingTimeField.setText("");
    endingDayCombo.setSelectedIndex(-1);
    endingTimeField.setText("");
  }

  /**
  private void printEventInfo(String action) {
    if ("Create event".equals(action) || "Remove event".equals(action) || "Modify event".equals(action)) {
      if (isInformationMissing()) {
        System.out.println("Error: Missing event information. Please ensure all fields are filled out.");
        return;
      }
    }
    String eventName = eventNameField.getText();
    String location = locationField.getText();
    boolean isOnline = isOnlineCheckBox.isSelected();
    String startingDay = (String) startingDayCombo.getSelectedItem();
    String startingTime = startingTimeField.getText();
    String endingDay = (String) endingDayCombo.getSelectedItem();
    String endingTime = endingTimeField.getText();

    String hostName = currentUserName;


    String eventInfo = String.format("%s: Name: %s, Host: %s, Location: %s, Is online: %s, Starting: %s %s, Ending: %s %s, Users: [%s]",
            action, eventName, hostName, location, isOnline, startingDay, startingTime, endingDay, endingTime);

    System.out.println(eventInfo);
  }
*/

  private boolean isInformationMissing() {
    return eventNameField.getText().trim().isEmpty() ||
            locationField.getText().trim().isEmpty() ||
            startingDayCombo.getSelectedItem() == null ||
            startingTimeField.getText().trim().isEmpty() ||
            endingDayCombo.getSelectedItem() == null ||
            endingTimeField.getText().trim().isEmpty();
  }

  public void setCurrentUser(String currentUserName) {
    this.currentUserName = currentUserName;
  }



}
