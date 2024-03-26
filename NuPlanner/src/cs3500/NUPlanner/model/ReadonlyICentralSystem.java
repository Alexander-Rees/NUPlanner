package cs3500.NUPlanner.model;

import java.util.List;

public interface ReadonlyICentralSystem {

  /**
   * Returns a user by their name.
   *
   * @param name The name of the user to retrieve.
   * @return The user with the specified name, or null if no such user exists.
   */
  IUser getUser(String name);


  /**
   * Gets a list of all users in the system.
   *
   * @return A list of all users.
   */
  List<IUser> getAllUsers();
}
