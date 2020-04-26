package snoopy.util;

import snoopy.log.SnoopyLogger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageUtil {

    public static List<String> getClasses(String packageName) {
        List<String> classes = new ArrayList<>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    classes = findClassPaths(packageName, filePath, recursive);
                    break;
                }else if ("jar".equals(protocol)){
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection)url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if (idx != -1 || recursive) {
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        classes.add(packageName + '.' + className);
                                    }
                                }
                            }
                        }
                    }catch (Exception e){
                        SnoopyLogger.error(PackageUtil.class, "getClasses" , "getClasses", e);
                    }
                }
            }
        } catch (IOException e) {
            SnoopyLogger.error(PackageUtil.class, "getClasses", "getClasses error", e);
        }
        return classes;
    }


    private static List<String> findClassPaths(String packageName, String packagePath, final boolean recursive) {
        List<String> result = new ArrayList<>();
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        File[] dirFiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                List<String> classPaths = findClassPaths(packageName + "." + file.getName(), file.getAbsolutePath(), recursive);
                result.addAll(classPaths);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                result.add(packageName + '.' + className);
            }
        }
        return result;
    }
}
