package cs3500.NUPlanner;

import cs3500.NUPlanner.view.MainSystemFrame;
import cs3500.NUPlanner.controller.ScheduleController;
import cs3500.NUPlanner.model.CentralSystem;

public class NUPlannerRunner {

  public static void main(String[] args) {

    CentralSystem model = new CentralSystem();
    MainSystemFrame view = new MainSystemFrame(model);
    ScheduleController controller = new ScheduleController(model, view);

    view.setController(controller);
    view.setMenuController(controller);


  }
}
