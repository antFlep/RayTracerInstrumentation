import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Metrics {

    public static Map<String, Long > sCount = new HashMap<>();
    public static Map<String, Long> sDuration = new HashMap<>();

    public static void duration(String methodName, long start, long end) {

        // Count number of calls
        long count = (sCount.get(methodName) == null) ? 1 : sCount.get(methodName) + 1;
        sCount.put(methodName, count);

        // Calculate method call duration,
        // since this is a simple subtract operation it should not have a major impact on performance
        long duration = end - start;
        long sum = (sDuration.get(methodName) == null) ? duration : sDuration.get(methodName) + duration;
        sDuration.put(methodName, sum);
    }

    public static String calcAverages() {
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

        return out ;
    }
}