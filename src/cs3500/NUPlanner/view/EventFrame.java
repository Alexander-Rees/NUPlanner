package cs3500.NUPlanner.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

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

  public EventFrame() {
    this.setLayout(new BorderLayout(10, 10));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(400, 600); // Adjust the size as needed

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

    usersListModel = new DefaultListModel<>();
    usersList = new JList<>(usersListModel);
    usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    mainPanel.add(createLabelledField("Event name:", eventNameField));
    mainPanel.add(createLabelledField("Location:", locationField));
    mainPanel.add(isOnlineCheckBox);
    mainPanel.add(createLabelledField("Starting Day:", startingDayCombo));
    mainPanel.add(createLabelledField("Starting time:", startingTimeField));
    mainPanel.add(createLabelledField("Ending Day:", endingDayCombo));
    mainPanel.add(createLabelledField("Ending time:", endingTimeField));
    mainPanel.add(createListWithLabel("Available users", usersList));

    // Buttons panel at the bottom
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

  public void setController(ActionListener controller) {
    modifyEventButton.addActionListener(controller);
    removeEventButton.addActionListener(controller);
    createEventButton.addActionListener(controller);
  }

  public void populateEventDetails(ReadonlyIEvent event) {
    eventNameField.setText(event.name());
    locationField.setText(event.location());
    isOnlineCheckBox.setSelected(event.online());
    startingDayCombo.setSelectedItem(event.startDay().toString());
    startingTimeField.setText(String.format("%04d", event.startTime()));
    endingDayCombo.setSelectedItem(event.endDay().toString());
    endingTimeField.setText(String.format("%04d", event.endTime()));


    usersListModel.clear();
    for (String user : event.participants()) {
      usersListModel.addElement(user);
    }
  }

}
