package cs3500.NUPlanner.view;

import java.awt.*;
import javax.swing.*;
import cs3500.NUPlanner.model.ReadonlyIEvent;

public class EventPanel extends JPanel implements IViewEventPanel {

  private ReadonlyIEvent event;

  public EventPanel(ReadonlyIEvent event) {
    this.event = event;
    this.setOpaque(true);
    this.setBackground(Color.RED);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }


  private void initialize() {
    this.setLayout(new BorderLayout());


    String startTimeStr = String.format("%04d", event.startTime());
    String endTimeStr = String.format("%04d", event.endTime());
    String timeStr = startTimeStr + " - " + endTimeStr;
    JLabel timeLabel = new JLabel(timeStr);
    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

    this.add(timeLabel, BorderLayout.CENTER);
  }

}
