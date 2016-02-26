package pl.jaca.jbo.transform.optimizers.redundantremover;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import pl.jaca.jbo.report.Reportable;
import pl.jaca.jbo.report.SchedulingReport;
import pl.jaca.jbo.transform.*;
import pl.jaca.jbo.util.FutureUtil;
import rx.subjects.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public class RedundantMethodRemover implements Transformer {

    public static final String RESOLVING_TRANSFORM = "Private methods optimized.";

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8);

    @Override
    public Transformation transform(JavaProject project) {
        Transformation transformation = new Transformation("Redundant methods removal.");
        try {
            List<ClassNode> classes = readProject(project, transformation);
            Set<String> methods = resolveMethods(classes);
        } catch (InterruptedException e) {
            transformation.fail(e);
        }
        return transformation;
    }

    private Set<String> resolveMethods(List<ClassNode> classes) {
        int a = 2 + 3;
        return null;
    }

    private List<ClassNode> readProject(JavaProject project, Transformation transformation) throws InterruptedException {
        List<Callable<ClassNode>> readerTasks = project.getClassesData()
                .map(c -> transformation.reported(new JavaClassReader(c.getClassData()), c.getName(), JavaClassReader.READ_TRANSFORM))
                .collect(Collectors.toList());
        transformation.reportScheduling(readerTasks.size(), JavaClassReader.READ_TRANSFORM);
        List<ClassNode> classes = executorService.invokeAll(readerTasks).stream()
                .map(FutureUtil::uncheckedGet)
                .collect(Collectors.toList());
        return classes;
    }


}
