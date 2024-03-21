package cs3500.NUPlanner.view;

import java.awt.*;
import javax.swing.*;

// Assuming ISchedule is your model interface and it provides the data needed to paint the schedule.
import cs3500.NUPlanner.model.ISchedule;

public class SchedulePanel extends JPanel implements IViewSchedulePanel {
  // Constructor
  public SchedulePanel() {
    this.setLayout(new GridLayout(6, 7));
    this.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
    initializeGrid();
  }

  // Initialize the grid cells
  private void initializeGrid() {
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < 7; col++) {
        JPanel cellPanel = new JPanel(new GridLayout(4, 1));
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (int i = 0; i < 4; i++) {
          JPanel linePanel = new JPanel();
          linePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
          cellPanel.add(linePanel);
        }

        ((JPanel)cellPanel.getComponent(3)).setBorder(BorderFactory.createEmptyBorder());

        this.add(cellPanel);
      }
    }
  }

  public void displaySchedule(ISchedule schedule) {
    this.removeAll();

    this.revalidate();
    this.repaint();
  }
}
