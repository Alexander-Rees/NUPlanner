package cs3500.NUPlanner.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.NUPlanner.model.Day;
import cs3500.NUPlanner.model.ISchedule;
import cs3500.NUPlanner.model.ReadonlyIEvent;

public class SchedulePanel extends JPanel implements IViewSchedulePanel {
  private JLayeredPane layeredPane;

  public SchedulePanel() {

    setLayout(new BorderLayout());
    layeredPane = new JLayeredPane();
    layeredPane.setPreferredSize(new Dimension(800, 600));

    this.add(layeredPane, BorderLayout.CENTER);
    initializeGrid();
  }

  private void initializeGrid() {
    int cellWidth = layeredPane.getPreferredSize().width / 7;
    int cellHeight = layeredPane.getPreferredSize().height / 24;

    for (int col = 0; col < 7; col++) {
      for (int row = 0; row < 24; row++) {
        JPanel cellPanel = new JPanel();
        cellPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        if (row % 4 == 0) {
          cellPanel.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.GRAY));
        }

        cellPanel.setBounds(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
        cellPanel.setOpaque(false);
        layeredPane.add(cellPanel, JLayeredPane.DEFAULT_LAYER);
      }
    }
  }


  public void displaySchedule(ArrayList<ReadonlyIEvent> events) {

    for (ReadonlyIEvent event : events) {
      addEventToSchedule(event);
    }

    layeredPane.revalidate();
    layeredPane.repaint();
  }






  private int convertDayToColumnIndex(Day day) {

    switch (day) {
      case SUNDAY: return 0;
      case MONDAY: return 1;
      case TUESDAY: return 2;
      case WEDNESDAY: return 3;
      case THURSDAY: return 4;
      case FRIDAY: return 5;
      case SATURDAY: return 6;
      default: return -1;
    }
  }

  public void addEventToSchedule(ReadonlyIEvent event) {
    int cellWidth = layeredPane.getPreferredSize().width / 7;
    int cellHeight = layeredPane.getPreferredSize().height / 24;

    Day startDay = event.startDay();
    Day endDay = event.endDay();

    int daySpan = endDay.ordinal() - startDay.ordinal();

    for (int i = 0; i <= daySpan; i++) {
      int dayIndex = convertDayToColumnIndex(Day.values()[startDay.ordinal() + i]);
      int startHourForPanel = (i == 0) ? event.startTime() / 100 : 0;
      int endHourForPanel = (i == daySpan) ? event.endTime() / 100 : 24;

      int yPos = startHourForPanel * cellHeight;
      int panelHeight;

      if (i < daySpan) {
        panelHeight = (24 - startHourForPanel) * cellHeight;
      } else {
        int startMinuteForPanel = (i == 0) ? event.startTime() % 100 : 0;
        int endMinuteForPanel = (i == daySpan) ? event.endTime() % 100 : 0;
        int durationInMinutesForPanel = (endHourForPanel * 60 + endMinuteForPanel) - (startHourForPanel * 60 + startMinuteForPanel);
        panelHeight = (durationInMinutesForPanel * cellHeight) / 60;
      }

      EventPanel eventPanel = new EventPanel(event);
      eventPanel.setBounds(dayIndex * cellWidth, yPos, cellWidth, panelHeight);
      layeredPane.add(eventPanel, Integer.valueOf(100));

    }
  }







}
