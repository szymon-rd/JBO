package pl.jaca.jbo.transform;

import java.io.InputStream;

/**
 * @author Jaca777
 *         Created 2016-02-26 at 21
 */
public class JavaClass {
    private String name;
    private InputStream classData;

    public JavaClass(String name, InputStream classData) {
        this.name = name;
        this.classData = classData;
    }

    public String getName() {
        return name;
    }

    public InputStream getClassData() {
        return classData;
    }
}
