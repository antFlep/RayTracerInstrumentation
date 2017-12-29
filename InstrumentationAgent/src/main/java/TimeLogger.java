import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TimeLogger {

    /*
        Overall a bad solution, huge amounts of ram are needed for this solution,
        or we need to flush every other time, which in return impacts our measurements.
        Ok Solution if we only render the scene one time and close it afterwards,
        but still slows the raytracer down significantly compared to the runtime calculation solution
     */

    static PrintWriter out;
    static long count = 0;

    static {
        try {
            out = new PrintWriter("Call_Times.log", "ISO-8859-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void log(String methodname, long startTime, long endTime) {
        count++;
        out.println(methodname + ":" + startTime+ ":" + endTime);

        // Flush in order reduce ram usage however falsifies results a bit, can be removed
        if (methodname.equals("renderImage")) {
            count = 0;
            out.flush();
        }
    }
}