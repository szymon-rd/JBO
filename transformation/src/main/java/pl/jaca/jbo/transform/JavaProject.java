package pl.jaca.jbo.transform;

import java.io.InputStream;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public interface JavaProject {

    /**
     * @return Stream of projects' classes.
     */
    Stream<JavaClass> getClassesData();

    /**
     * Puts a java class in the project. If java class name is
     * duplicated, only @javaClass is kept.
     *
     * @param javaClass
     */
    void putClass(JavaClass javaClass);
}
