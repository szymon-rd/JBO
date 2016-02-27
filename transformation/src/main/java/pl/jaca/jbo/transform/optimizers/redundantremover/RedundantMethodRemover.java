package pl.jaca.jbo.transform.optimizers.redundantremover;


import org.objectweb.asm.tree.ClassNode;
import pl.jaca.jbo.transform.*;
import pl.jaca.jbo.util.FutureUtil;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public class RedundantMethodRemover implements Transformer {


    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8);

    @Override
    public Transformation transform(JavaProject project) {
        Transformation transformation = new Transformation("Redundant methods removal.");
        try {
            List<ClassNode> classes = readProject(project, transformation);
            List<ClassNode> transformedClasses = removeRedundant(classes, transformation);
            JavaProject transformedProject = createProject(transformedClasses, transformation);
            transformation.complete(transformedProject);
        } catch (InterruptedException e) {
            transformation.fail(e);
        }
        return transformation;
    }

    private List<ClassNode> removeRedundant(List<ClassNode> classes, Transformation transformation) throws InterruptedException {
        List<Callable<ClassNode>> removerTasks = classes.stream()
                .map(node -> transformation.reported(new CallableRedundantRemover(node), node.name, CallableRedundantRemover.RESOLVING_TRANSFORM))
                .collect(Collectors.toList());
        return invokeAndAwait(transformation, removerTasks);
    }

    private List<ClassNode> readProject(JavaProject project, Transformation transformation) throws InterruptedException {
        List<Callable<ClassNode>> readerTasks = project.getClassesData()
                .map(c -> transformation.reported(new JavaClassReader(c.getClassData()), c.getName(), JavaClassReader.READ_TRANSFORM))
                .collect(Collectors.toList());
        return invokeAndAwait(transformation, readerTasks);
    }

    private JavaProject createProject(List<ClassNode> classes, Transformation transformation) throws InterruptedException {
        List<Callable<JavaClass>> writerTasks = classes.stream()
                .map(c -> transformation.reported(new JavaClassWriter(c), c.name, JavaClassWriter.WRITE_TRANSFORM))
                .collect(Collectors.toList());
        List<JavaClass> javaClasses = invokeAndAwait(transformation, writerTasks);
        return new CompleteJavaProject(javaClasses);
    }

    private <T> List<T> invokeAndAwait(Transformation transformation, List<Callable<T>> removerTasks) throws InterruptedException {
        transformation.reportScheduling(removerTasks.size(), CallableRedundantRemover.RESOLVING_TRANSFORM);
        return executorService.invokeAll(removerTasks).stream()
                .map(FutureUtil::uncheckedGet)
                .collect(Collectors.toList());
    }

}
