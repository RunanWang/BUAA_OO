import com.oocourse.TimableOutput;

public class Output {
    public static void printInit() {
        TimableOutput.initStartTimestamp();
    }
    
    public static void printOpen(int f) {
        TimableOutput.println(String.format("OPEN-%d", f));
    }
    
    public static void printClose(int f) {
        TimableOutput.println(String.format("CLOSE-%d", f));
    }
    
    public static void printIn(int id, int f) {
        TimableOutput.println(String.format("IN-%d-%d", id, f));
    }
    
    public static void printOut(int id, int f) {
        TimableOutput.println(String.format("OUT-%d-%d", id, f));
    }
}
