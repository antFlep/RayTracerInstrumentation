//import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.Type;

public class MyMethodVisitor extends AdviceAdapter implements Opcodes{

    String mName = "";
    int starttime = 0;

    protected MyMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc){
        super(api, mv, access, name, desc);
        mName = name;
    }

    @Override
    public  void onMethodEnter() {

        // Push starttime onto the stack
        starttime = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System" ,"nanoTime", "()J", false);
        mv.visitVarInsn(Opcodes.LSTORE, starttime);
    }

    @Override
    public  void onMethodExit(int opcode) {

        // get starttime from the stack, see the current time (endtime) and save them (call the duration method)
        mv.visitLdcInsn(mName);
        mv.visitVarInsn(LLOAD,starttime);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System" ,"nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "duration","(Ljava/lang/String;JJ)V", false);

        // Calculations are done here because the programs waits for input after rendering the image
        if (mName.equals("renderImage")) {
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitMethodInsn(INVOKESTATIC, "Metrics", "calcAverages","()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);
        }
    }

}
