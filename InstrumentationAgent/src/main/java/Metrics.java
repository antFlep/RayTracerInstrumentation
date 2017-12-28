import java.util.HashMap;
import java.util.Map;

public class Metrics {

    public static Map<String, Integer > sCount = new HashMap<>();
    public static Map<String, Long> sDuration = new HashMap<>();

    public static  void increaseCounter(String methodName) {
        int count = (sCount.get(methodName) == null) ? 1 : sCount.get(methodName) + 1;
        sCount.put(methodName, count);
    }

    public static String duration(String methodName, long start, long end) {
        long duration = end - start;
        long sum = (sDuration.get(methodName) == null) ? duration : sDuration.get(methodName) + duration;
        sDuration.put(methodName, sum);
        return methodName + " duration: " + duration + " - CompleteTime: " + sDuration.get(methodName) + " - Nr. of Calls: " + sCount.get(methodName);
    }
}