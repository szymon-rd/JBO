package pl.jaca.jbo.transform;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-02-22 at 19
 */
public interface JavaClass {
    String getPath();
    InputStream getClassData();
}
