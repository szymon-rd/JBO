package pl.jaca.jbo.report;

import pl.jaca.jbo.transform.TransformationReport;

import java.util.concurrent.Callable;

/**
 * @author Jaca777
 *         Created 2016-02-26 at 21
 */
public interface Reporter {
    void report(Reportable reportable);

    default <T> Callable<T> reported(Callable<T> callable, String target, String transformation) {
        return () -> {
            try {
                T t = callable.call();
                report(new TransformationReport(target, transformation));
                return t;
            } catch (Throwable t) {
                fail(t);
                throw t;
            }
        };
    }

    void fail(Throwable e);

    void complete();
}
