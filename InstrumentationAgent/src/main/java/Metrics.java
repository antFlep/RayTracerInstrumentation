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
            out += key + " - DURATION_SUM: " + duration + " - NR_OF_CALLS: " + count + " - AVERAGE_EXEC_TIME: " + ((duration*1.0)/count) + "\n";
        }

        return out ;
    }
}