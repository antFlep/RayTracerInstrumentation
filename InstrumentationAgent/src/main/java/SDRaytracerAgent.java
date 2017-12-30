/*
    Name: Filipe Emanuel Antunes Almeida
    Matrikelnummer: 1103386
 */

import java.lang.instrument.Instrumentation;

public class SDRaytracerAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new RaytracerTransformer());
    }
}
