package com.dean4j.framework.uitl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * 获取类加载器，加载类，获取指定包名下的所有类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader(); //单前线程的类加载器
    }

    /**
     * 加载类
     *
     * @param classNmae     类名
     * @param isInitialized 是否初始化（是否执行类的静态代码块）,为了提高性能可设为false
     */
    public static Class<?> loadClass(String classNmae, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(classNmae, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类失败", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包下的所有类
     *
     * @param packageNmae 包名
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageNmae) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageNmae.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        String packagePath = url.getPath().replaceAll("20%", " ");
                        addClass(classSet, packagePath, packageNmae);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String classNmae = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                                        doAddClass(classSet, classNmae);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取类集合失败", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    /**
     * 递归加载所有的类
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for (File file : files) {
            String fileNmae = file.getName();
            if (file.isFile()) {
                String className = fileNmae.substring(0, fileNmae.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileNmae;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackageName = fileNmae;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String classNmae) {
        Class<?> cls = loadClass(classNmae, false);
        classSet.add(cls);
    }

}
