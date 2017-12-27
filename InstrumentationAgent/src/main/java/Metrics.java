import java.util.HashMap;
import java.util.Map;

public class Metrics {

    public static Map<String, Long> sStartTime = new HashMap<>();
    public static Map<String, Long> sEndTime = new HashMap<>();
    public static Map<String, Integer > sCount = new HashMap<>();

    public static  void setStartTime(String methodName, long time) {
        sStartTime.put(methodName, time);
    }

    public static  void setEndTime(String methodName, long time) {
        sEndTime.put(methodName, time);
    }

    public static  void increaseCounter(String methodName) {
        int count = (sCount.get(methodName) == null) ? 1 : sCount.get(methodName) + 1;
        sCount.put(methodName, count);
    }

    public static  String getCostTime(String methodName) {
        long start = sStartTime.get(methodName);
        long end = sEndTime.get(methodName);
        return "Method: " + methodName + " " + Long.valueOf(end - start) + " ns" + "; Executions until now: " + sCount.get(methodName);
    }
}
