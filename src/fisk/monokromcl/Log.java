package fisk.monokromcl;

public class Log {
  public static void d(String message){
    if(MonokromCL.debug) {
      System.out.printf("Debug: %s%n", message);
    }
  }

  public static void e(String message){
    if(MonokromCL.debug) {
      System.out.printf("Debug error: %s%n", message);
    }
  }
}
