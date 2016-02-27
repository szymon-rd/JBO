package pl.jaca.jbo.transform;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-02-27 at 10
 */
public class CompleteJavaProject implements JavaProject {
    private Collection<JavaClass> classes;

    public CompleteJavaProject(Collection<JavaClass> classes) {
        this.classes = classes;
    }

    @Override
    public Stream<JavaClass> getClassesData() {
        return classes.stream();
    }
}
