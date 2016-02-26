package pl.jaca.jbo.transform;

import pl.jaca.jbo.report.Work;

import java.util.concurrent.Callable;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public interface Transformer {
    Transformation transform(JavaProject project);
}
