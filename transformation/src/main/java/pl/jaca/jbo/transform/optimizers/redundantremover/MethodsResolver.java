package pl.jaca.jbo.transform.optimizers.redundantremover;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.jaca.jbo.transform.TransformationReport;

import java.util.*;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public class MethodsResolver extends ClassVisitor {

    private String name;
    private Multimap<String, String> calls = HashMultimap.create();
    private Set<String> privateMethods = new HashSet<>();
    private Set<String> publicMethods = new HashSet<>();

    public MethodsResolver(int api) {
        super(api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.name = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        String identifier = name + desc;
        if (isPrivate(access)) privateMethods.add(identifier);
        else publicMethods.add(identifier);
        return new MethodCallsVisitor(identifier);
    }

    @Override
    public void visitEnd() {
        publicMethods.forEach(this::deleteCalls);
    }

    private void deleteCalls(String publicMethod) {
        Stack<String> callers = new Stack<>();
        callers.push(publicMethod);
        while (!callers.isEmpty()) {
            String caller = callers.pop();
            for (String call : calls.get(caller)) {
                if (privateMethods.contains(call)) {
                    privateMethods.remove(call);
                    callers.push(call);
                }
            }
        }
    }

    public Set<String> getRedundantMethods() {
        return privateMethods;
    }

    public String getName() {
        return name;
    }

    private boolean isPrivate(int access) {
        return (access & Opcodes.ACC_PRIVATE) != 0;
    }

    private class MethodCallsVisitor extends MethodVisitor {

        private String identifier;

        public MethodCallsVisitor(String identifier) {
            super(MethodsResolver.this.api);
            this.identifier = identifier;
        }


        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            String identifier = name + desc;
            calls.put(this.identifier, identifier);
        }
    }
}
