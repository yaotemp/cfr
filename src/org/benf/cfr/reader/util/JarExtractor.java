package org.benf.cfr.reader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarExtractor {
    
    /**
     * Find all JAR files containing "25" in their name in a directory and its subdirectories
     * and extract them to a target folder
     * 
     * @param sourceDir The source directory to search in
     * @param targetDir The target directory to extract to
     * @return A list of extracted JAR file paths
     * @throws IOException If an I/O error occurs
     */
    public static List<String> findAndExtractJars(String sourceDir, String targetDir) throws IOException {
        List<String> extractedJars = new ArrayList<>();
        List<Path> jarPaths = findJars(sourceDir);
        
        // Create target directory if it doesn't exist
        File targetDirectory = new File(targetDir);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        
        // Extract each JAR file
        for (Path jarPath : jarPaths) {
            String jarName = jarPath.getFileName().toString();
            String extractDir = targetDir + File.separator + jarName.replace(".jar", "");
            extractJar(jarPath.toString(), extractDir);
            extractedJars.add(jarPath.toString());
        }
        
        return extractedJars;
    }
    
    /**
     * Find all JAR files containing "25" in their name in a directory and its subdirectories
     * 
     * @param sourceDir The source directory to search in
     * @return A list of JAR file paths
     * @throws IOException If an I/O error occurs
     */
    public static List<Path> findJars(String sourceDir) throws IOException {
        return Files.walk(Paths.get(sourceDir))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().toLowerCase().endsWith(".jar"))
                .filter(path -> path.getFileName().toString().contains("25"))
                .collect(Collectors.toList());
    }
    
    /**
     * Extract a JAR file to a target directory
     * 
     * @param jarFilePath The path to the JAR file
     * @param destDir The destination directory
     * @throws IOException If an I/O error occurs
     */
    public static void extractJar(String jarFilePath, String destDir) throws IOException {
        File destDirectory = new File(destDir);
        if (!destDirectory.exists()) {
            destDirectory.mkdirs();
        }
        
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(jarFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            
            // Iterates over entries in the zip file
            while (entry != null) {
                String filePath = destDir + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // If the entry is a file, extract it
                    extractFile(zipIn, filePath);
                } else {
                    // If the entry is a directory, create the directory
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }
    
    /**
     * Extract a file from a zip input stream
     * 
     * @param zipIn The zip input stream
     * @param filePath The path to extract the file to
     * @throws IOException If an I/O error occurs
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        // Create parent directories if they don't exist
        File parentDir = new File(new File(filePath).getParent());
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = zipIn.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }
} 