package pl.jaca.jbo.transform.optimizers.redundantremover;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.ClassNode;

import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-02-27 at 01
 */
public class MethodsRemover extends ClassNode {
    private Set<String> redundantMethods;

    public MethodsRemover(int i, Set<String> redundantMethods) {
        super(i);
        this.redundantMethods = redundantMethods;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        String identifier = name + desc;
        if (!redundantMethods.contains(identifier))
            return super.visitMethod(access, name, desc, signature, exceptions);
        else return null;
    }
}
