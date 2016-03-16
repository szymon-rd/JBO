package pl.jaca.jbo.transform;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * @author Jaca777
 *         Created 2016-03-11 at 22
 */
public class JarFileWriter {

    private ProjectJarFile jarFile;

    public JarFileWriter(ProjectJarFile jarFile) {
        this.jarFile = jarFile;
    }

    /**
     * Writes this java project to file with applied modifications.
     *
     * @param file
     * @throws IOException
     */
    public void write(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream);
        Enumeration<JarEntry> entries = this.jarFile.getJarFile().entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (!entry.isDirectory() && !isClass(entry))
                writeEntry(jarOutputStream, entry);
        }
        this.jarFile.getClassesData().forEach(javaClass -> writeClass(jarOutputStream, javaClass));
        jarOutputStream.close();

    }

    private void writeEntry(JarOutputStream jarOutputStream, JarEntry entry) throws IOException {
            jarOutputStream.putNextEntry(entry);
            InputStream inputStream = this.jarFile.getJarFile().getInputStream(entry);
            ByteStreams.copy(inputStream, jarOutputStream);
            jarOutputStream.closeEntry();

    }

    private void writeClass(JarOutputStream jarOutputStream, JavaClass javaClass) {
        ZipEntry entry = new ZipEntry(javaClass.getName() + ".class");
        try {
            jarOutputStream.putNextEntry(entry);
            ByteStreams.copy(javaClass.getClassData(), jarOutputStream);
            jarOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isClass(JarEntry entry) {
        return entry.getName().endsWith(".class");
    }
}
