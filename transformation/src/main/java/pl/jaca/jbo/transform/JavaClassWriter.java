package pl.jaca.jbo.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * @author Jaca777
 *         Created 2016-02-27 at 10
 */
public class JavaClassWriter implements Callable<JavaClass> {
    public static final String WRITE_TASK = "Writing class";
    private ClassNode classNode;

    public JavaClassWriter(ClassNode classNode) {
        this.classNode = classNode;
    }

    @Override
    public JavaClass call() throws Exception {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return new JavaClass(classNode.name, new ByteArrayInputStream(writer.toByteArray()));
    }

}
