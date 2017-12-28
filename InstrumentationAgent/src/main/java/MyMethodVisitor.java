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
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("=======start======== " + mName);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);

        // push starttime onto the stack
        starttime = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System" ,"nanoTime", "()J", false);
        mv.visitVarInsn(Opcodes.LSTORE, starttime);
    }

    @Override
    public  void onMethodExit(int opcode) {

        //Nr. of calls is only incremented at the end in order to not falsify our results
        mv.visitLdcInsn(mName);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "increaseCounter","(Ljava/lang/String;)V", false);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(mName);
        mv.visitVarInsn(LLOAD,starttime);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System" ,"nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "duration","(Ljava/lang/String;JJ)Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("========end========= " + mName);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);
    }

}
