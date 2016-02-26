package pl.jaca.jbo.util;

        import java.util.concurrent.ExecutionException;
        import java.util.concurrent.Future;

/**
 * @author Jaca777
 *         Created 2016-02-26 at 22
 */
public class FutureUtil {
    public static  <T> T uncheckedGet(Future<T> future) {
        try {
            return future.get();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
