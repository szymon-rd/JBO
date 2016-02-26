package pl.jaca.jbo.transform.optimizers.redundantremover;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import pl.jaca.jbo.report.Reporter;
import pl.jaca.jbo.transform.TransformationReport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author Jaca777
 *         Created 2016-02-26 at 18
 */
public class CallableMethodsResolver implements Callable<MethodsResolver> {

    private MethodsResolver classVisitor;
    private ClassNode classNode;
    private Reporter reporter;

    public CallableMethodsResolver(ClassNode classNode, Reporter reporter) {
        this.classNode = classNode;
        this.classVisitor = new MethodsResolver(Opcodes.ASM5);
    }

    @Override
    public MethodsResolver call() throws Exception {
        classNode.accept(classVisitor);
        TransformationReport report = new TransformationReport(classNode.name, RedundantMethodRemover.RESOLVING_TRANSFORM);
        reporter.report(report);
        return classVisitor;
    }


    public ClassNode getClassNode() {
        return classNode;
    }

    public String getName() {
        return classVisitor.getName();
    }

}
