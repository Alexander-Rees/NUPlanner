package cs3500.NUPlanner;

import java.util.Scanner;

import cs3500.NUPlanner.model.AnyTimeSchedulingStrategy;
import cs3500.NUPlanner.model.ReadonlyICentralSystem;
import cs3500.NUPlanner.model.WorkHoursSchedulingStrategy;
import cs3500.NUPlanner.view.MainSystemFrame;
import cs3500.NUPlanner.controller.ScheduleController;
import cs3500.NUPlanner.model.CentralSystem;

public class NUPlannerRunner {


  public static void main(String[] args) {


    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter scheduling strategy (workhours/anytime):");

    String strategy = scanner.nextLine();

    CentralSystem model = new CentralSystem();
    ReadonlyICentralSystem readOnlymodel = model;
    MainSystemFrame view = new MainSystemFrame(readOnlymodel);
    ScheduleController controller = new ScheduleController(model, view);

    if ("workhours".equalsIgnoreCase(strategy)) {
      WorkHoursSchedulingStrategy workhours = new WorkHoursSchedulingStrategy();
      controller.setSchedulingStrategy(workhours);
    } else if ("anytime".equalsIgnoreCase(strategy)) {
      AnyTimeSchedulingStrategy anytime = new AnyTimeSchedulingStrategy();
      controller.setSchedulingStrategy(anytime);
    } else {
      System.out.println("Invalid strategy. Defaulting to 'anytime'.");
      AnyTimeSchedulingStrategy anytime = new AnyTimeSchedulingStrategy();
      controller.setSchedulingStrategy(anytime);
    }

    view.setController(controller);
  }
}
