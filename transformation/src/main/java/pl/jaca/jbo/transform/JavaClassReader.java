package pl.jaca.jbo.transform;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import pl.jaca.jbo.report.Reporter;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * @author Jaca777
 *         Created 2016-02-26 at 20
 */
public class JavaClassReader implements Callable<ClassNode> {
    public static final String READ_TASK = "Reading class";
    private InputStream classData;

    public JavaClassReader(InputStream classData) {
        this.classData = classData;
    }

    @Override
    public ClassNode call() throws Exception {
        org.objectweb.asm.tree.ClassNode node = new org.objectweb.asm.tree.ClassNode(Opcodes.ASM5);
        ClassReader reader = new ClassReader(classData);
        reader.accept(node, 0);
        return node;
    }

}
