package cs3500.NUPlanner;

import cs3500.NUPlanner.view.MainSystemFrame;
import cs3500.NUPlanner.controller.ScheduleController;
import cs3500.NUPlanner.model.CentralSystem;

public class NUPlannerRunner {

  public static void main(String[] args) {
    MainSystemFrame view = new MainSystemFrame();
    CentralSystem model = new CentralSystem();
    ScheduleController controller = new ScheduleController(model, view);

    view.setController(controller);
    view.setMenuController(controller);


  }
}
