import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class MyMethodVisitor extends AdviceAdapter implements Opcodes{

    String mName = "";

    protected MyMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc){
        super(api, mv, access, name, desc);
        mName = name;
    }

//    @Override
//    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf){
//        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
//        mv.visitLdcInsn("CALL " + name);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//        super.visitMethodInsn(opcode, owner, name, desc,itf);
//    }

    @Override
    public  void onMethodEnter() {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("=======start========");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);

//        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mv.visitLdcInsn("--- >> " + mName);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);

        mv.visitLdcInsn(mName);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "setStartTime","(Ljava/lang/String;J)V", false);

        mv.visitLdcInsn(mName);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "increaseCounter","(Ljava/lang/String;)V", false);
    }

    @Override
    public  void onMethodExit(int opcode) {
        mv.visitLdcInsn(mName);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "setEndTime","(Ljava/lang/String;J)V", false);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn(mName);
        mv.visitMethodInsn(INVOKESTATIC, "Metrics", "getCostTime","(Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);

//        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mv.visitLdcInsn("--- << " + mName);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);

        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("========end=========");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V", false);
    }

}
