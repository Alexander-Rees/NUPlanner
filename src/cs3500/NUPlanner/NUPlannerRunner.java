package cs3500.NUPlanner;

import cs3500.NUPlanner.view.MainSystemFrame;
import cs3500.NUPlanner.controller.ScheduleController;
import cs3500.NUPlanner.model.CentralSystem;

public class NUPlannerRunner {

  public static void main(String[] args) {
    CentralSystem centralSystem = new CentralSystem();
    MainSystemFrame mainSystemFrame = new MainSystemFrame();
    ScheduleController scheduleController = new ScheduleController(centralSystem, mainSystemFrame);

    mainSystemFrame.setVisible(true);
  }
}
