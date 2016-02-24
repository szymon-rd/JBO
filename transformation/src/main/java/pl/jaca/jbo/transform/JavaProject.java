package pl.jaca.jbo.transform;

import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public interface JavaProject {
    Stream<JavaClass> getClasses();
}
