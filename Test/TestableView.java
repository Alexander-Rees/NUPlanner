import java.io.IOException;

import cs3500.NUPlanner.controller.IFeatures;
import cs3500.NUPlanner.view.IViewSystemFrame;

public class TestableView implements IViewSystemFrame {
  private final Appendable log;

  public TestableView(Appendable log) {
    this.log = log;
  }

  private void logAction(String message) {
    try {
      log.append(message).append("\n");
    } catch (IOException e) {
      throw new RuntimeException("Failed to log action", e);
    }
  }
  @Override
  public void setController(IFeatures controller) {
    logAction("setController called");
  }

  @Override
  public void updateUserScheduleInView() {
    logAction("updateUser called");
  }

  @Override
  public void setUserList(String[] userNames) {
    logAction("setuserlist called");
  }

  @Override
  public String getSelectedUser() {
    return null;
  }
}
