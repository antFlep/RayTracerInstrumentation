import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ProfilingClassVisitor extends ClassVisitor implements Opcodes{

    private static final int API_VERSION =ASM5;
    ProfilingClassVisitor(ClassVisitor cv){
        super(API_VERSION,cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new MyMethodVisitor(API_VERSION, mv, access, name, desc);
    }
}