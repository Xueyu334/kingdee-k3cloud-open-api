package com.rain.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class CustomClassLoader extends ClassLoader {
    /**
     * jar的路径
     */
    private String jarPath;
    
    public CustomClassLoader(String jarPath) {
        this.jarPath = jarPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String classFileName = name.replace('.', '/') + ".class";
            byte[] classData = loadClassDataFromJar(classFileName);
            if (classData == null) {
                throw new ClassNotFoundException(name);
            }
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
    }


    private byte[] loadClassDataFromJar(String classFileName) throws IOException {
        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarPath))) {
            JarEntry entry;
            while ((entry = jarInputStream.getNextJarEntry()) != null) {
                if (entry.getName().equals(classFileName)) {
                    return jarInputStream.readAllBytes();
                }
            }
        }
        return null;
    }
}
