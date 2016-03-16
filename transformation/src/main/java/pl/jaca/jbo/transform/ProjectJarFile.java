package pl.jaca.jbo.transform;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-02-27 at 11
 */
public class ProjectJarFile implements JavaProject {
    private List<JavaClass> classes;
    private JarFile jarFile;

    private ProjectJarFile(JarFile jarFile, List<JavaClass> classes) {
        this.classes = classes;
        this.jarFile = jarFile;
    }

    public ProjectJarFile(JarFile file) throws IOException {
        this(file, read(file));
    }

    public ProjectJarFile(String path) throws IOException {
        this(new JarFile(path));
    }


    @Override
    public Stream<JavaClass> getClassesData() {
        return classes.stream();
    }

    @Override
    public void putClass(JavaClass javaClass) {
        classes.removeIf(c -> c.getName().equals(javaClass.getName()));
        classes.add(javaClass);
    }

    public JarFile getJarFile() {
        return jarFile;
    }

    private static List<JavaClass> read(JarFile jar) throws IOException {
        List<JavaClass> classes = new ArrayList<>();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements())
            readEntry(classes, jar, entries.nextElement());
        return classes;
    }

    private static void readEntry(List<JavaClass> dest, JarFile jar, JarEntry entry) throws IOException {
        if (!entry.isDirectory() && isClass(entry)) {
            String className = entry.getName().replace(".class", "");
            dest.add(new JavaClass(className, jar.getInputStream(entry)));
        }
    }

    private static boolean isClass(JarEntry entry) {
        return entry.getName().endsWith(".class");
    }
}
