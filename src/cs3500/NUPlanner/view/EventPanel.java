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
  private JTextField startingTimeCombo;
  private JComboBox<String> endingDayCombo;
  private JTextField endingTimeCombo;
  private JList<String> usersList;
  private JButton modifyEventButton;
  private JButton removeEventButton;
  private JButton createEventButton;
  private DefaultListModel<String> usersListModel;

  public EventPanel() {
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    eventNameField = new JTextField(20);
    locationField = new JTextField(20);
    isOnlineCheckBox = new JCheckBox("Is online");

    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    startingDayCombo = new JComboBox<>(days);
    startingTimeCombo = new JTextField(4);
    endingDayCombo = new JComboBox<>(days);
    endingTimeCombo = new JTextField(4);

    usersListModel = new DefaultListModel<>();
    usersList = new JList<>(usersListModel);
    usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    modifyEventButton = new JButton("Modify event");
    removeEventButton = new JButton("Remove event");
    createEventButton = new JButton("Create event");

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
    buttonPanel.add(createEventButton);
    buttonPanel.add(modifyEventButton);
    buttonPanel.add(removeEventButton);

    add(buttonPanel);
  }

  private Component createLabelledComponent(String label, Component component) {
    Box box = Box.createHorizontalBox();
    box.add(new JLabel(label));
    box.add(Box.createHorizontalStrut(5));
    box.add(component);
    box.add(Box.createHorizontalGlue());
    return box;
  }


  public void setController(ActionListener controller) {
    createEventButton.addActionListener(controller);
    modifyEventButton.addActionListener(controller);
    removeEventButton.addActionListener(controller);
  }


}

