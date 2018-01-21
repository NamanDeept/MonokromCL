package fisk.monokromcl;

public class Out {
  public static void print(String message) {
    System.out.println(message);
  }

  public static void error(String message) {
    System.out.printf("Error: %s%n", message);
  }

  public static void fatalError(String message) {
    System.out.printf("Error: %s%n", message);
    System.exit(1);
  }

  public static void error(Exception e) {
    System.out.printf("Error: %s%n", e.toString());
  }

  public static void fatalError(Exception e) {
    System.out.printf("Error: %s%n", e.toString());
    System.exit(1);
  }
}
