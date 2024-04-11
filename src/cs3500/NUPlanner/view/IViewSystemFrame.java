package cs3500.NUPlanner.view;

import cs3500.NUPlanner.controller.IFeatures;

public interface IViewSystemFrame {

  public void setController(IFeatures controller);

  public void updateUserScheduleInView();
  public void setUserList(String[] userNames);
  public String getSelectedUser();
}
