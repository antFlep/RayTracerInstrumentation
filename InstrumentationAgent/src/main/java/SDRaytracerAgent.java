import java.lang.instrument.Instrumentation;

public class SDRaytracerAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new RaytracerTransformer());
    }
}