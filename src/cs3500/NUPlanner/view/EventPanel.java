package cs3500.NUPlanner.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EventPanel extends JPanel implements IViewEventPanel {
  private JTextField eventNameField;
  private JTextField locationField;
  private JCheckBox isOnlineCheckBox;
  private JComboBox<String> startingDayCombo;
  private JComboBox<String> startingTimeCombo;
  private JComboBox<String> endingDayCombo;
  private JComboBox<String> endingTimeCombo;
  private JList<String> usersList;
  private JButton modifyEventButton;
  private JButton removeEventButton;
  private DefaultListModel<String> usersListModel;

  public EventPanel() {
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    eventNameField = new JTextField(20);
    locationField = new JTextField(20);
    isOnlineCheckBox = new JCheckBox("Is online");

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] times = generateTimes();

    startingDayCombo = new JComboBox<>(days);
    startingTimeCombo = new JComboBox<>(times);
    endingDayCombo = new JComboBox<>(days);
    endingTimeCombo = new JComboBox<>(times);

    usersListModel = new DefaultListModel<>();
    usersList = new JList<>(usersListModel);
    usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    modifyEventButton = new JButton("Modify event");
    removeEventButton = new JButton("Remove event");

    add(createLabelledComponent("Event name:", eventNameField));
    add(createLabelledComponent("Location:", locationField));
    add(createLabelledComponent("", isOnlineCheckBox));
    add(createLabelledComponent("Starting Day:", startingDayCombo));
    add(createLabelledComponent("Starting time:", startingTimeCombo));
    add(createLabelledComponent("Ending Day:", endingDayCombo));
    add(createLabelledComponent("Ending time:", endingTimeCombo));

    add(new JLabel("Available users"));
    add(new JScrollPane(usersList));

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(modifyEventButton);
    buttonPanel.add(removeEventButton);
    add(buttonPanel);
  }

  private Component createLabelledComponent(String label, Component component) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(new JLabel(label));
    panel.add(component);
    return panel;
  }

  private String[] generateTimes() {
    // This should return an array of string representations of times throughout the day
    // ...
    return new String[0];
  }

  public void setController(ActionListener controller) {
    modifyEventButton.addActionListener(controller);
    removeEventButton.addActionListener(controller);
  }


}

