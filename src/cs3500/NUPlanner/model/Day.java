package cs3500.NUPlanner.model;

/**
 * Defines a day as one of SUNDAY MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY.
 */

public enum Day {

  SUNDAY("SUNDAY"),
  MONDAY("MONDAY"),
  TUESDAY("TUESDAY"),
  WEDNESDAY("WEDNESDAY"),
  THURSDAY("THURSDAY"),
  FRIDAY("FRIDAY"),
  SATURDAY("SATURDAY");


  private final String dayName;

  Day(String dayName) {
    this.dayName = dayName;
  }

  public String dayName() {
    return this.dayName;
  }

}
