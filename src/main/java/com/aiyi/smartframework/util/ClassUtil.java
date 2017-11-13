package com.aiyi.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by aiyi on 2017/5/10.
 */
public class ClassUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }

        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> resources
                    = getClassLoader().getResources(packageName.replace('.', '/'));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    LOGGER.debug("packagePath = {}, packageName = {}", packagePath, packageName);
                    addClass(classSet, packagePath, packageName);
                } else if ("jar".equals(protocol)) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();

                        String jarEntryName = jarEntry.getName();

                        if (".class".equals(jarEntryName)) {
                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf('.'))
                                    .replaceAll("/", ".");
                            doAddClass(classSet, className);
                        }

                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return classSet;
    }


    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File curPackage = new File(packagePath);

        Arrays.stream(Optional.ofNullable(curPackage.listFiles(file -> file.isFile() && (file.getName().endsWith(".class") || file.isDirectory())))
                .orElseThrow(RuntimeException::new)).forEach(file -> {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf('.'));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath += packagePath + '/';
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName += packageName + '.';
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        });

    }


    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
