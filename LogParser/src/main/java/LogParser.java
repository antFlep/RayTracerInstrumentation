import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LogParser {

    public static Map<String, Long > sCount = new HashMap<>();
    public static Map<String, Long> sDuration = new HashMap<>();

    public static void main (String[] argv) throws IOException {

        FileReader logFileReader = new FileReader("Raytracer/Call_Times.log");
        BufferedReader bufferedLogFileReader = new BufferedReader(logFileReader);

        String line;

        while ((line = bufferedLogFileReader.readLine()) != null) {

            String[] result = line.split(":");

            if (result.length == 3) {
                String methodName = result[0];
                long start = Long.parseLong(result[1]);
                long end = Long.parseLong(result[2]);

                long duration = end - start;

                // Count number of calls
                long count = (sCount.get(methodName) == null) ? 1 : sCount.get(methodName) + 1;
                sCount.put(methodName, count);

                // Sum durations of all calls
                long sum = (sDuration.get(methodName) == null) ? duration : sDuration.get(methodName) + duration;
                sDuration.put(methodName, sum);
            }
        }

        // Calculate and print results
        String out = new String("\n");

        Set<String> keys = sDuration.keySet();

        for(String key: keys){
            long count = sCount.get(key);
            long duration = sDuration.get(key);
            out += String.format("%1$-20s DURATION_SUM (in s): %2$-20s NR_OF_CALLS: %3$-20s AVERAGE_EXEC_TIME (in s): %4$-20s\n",
                    (key),
                    (duration/1000000000.0),
                    (count),
                    (((duration*1.0)/count)/1000000000.0));
        }

        System.out.println(out);
    }
}
