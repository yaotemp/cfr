package org.benf.cfr.examples;

import org.benf.cfr.reader.util.JarExtractor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Example demonstrating the usage of JarExtractor utility.
 * This utility finds all JAR files containing "25" in their name
 * and extracts them to a specified target folder.
 */
public class JarExtractorExample {
    
    public static void main(String[] args) {
        // Define source and target directories
        String sourceDir = "./lib";
        String targetDir = "./extracted_jars";
        
        try {
            System.out.println("Searching for JAR files containing '25' in: " + sourceDir);
            
            // Find all matching JAR files without extracting them
            List<Path> jarFiles = JarExtractor.findJars(sourceDir);
            
            System.out.println("Found " + jarFiles.size() + " matching JAR files:");
            for (Path jarPath : jarFiles) {
                System.out.println("- " + jarPath);
            }
            
            // Extract all matching JAR files
            System.out.println("\nExtracting JAR files to: " + targetDir);
            List<String> extractedJars = JarExtractor.findAndExtractJars(sourceDir, targetDir);
            
            System.out.println("Successfully extracted " + extractedJars.size() + " JAR files:");
            for (String jarPath : extractedJars) {
                System.out.println("- " + jarPath);
            }
            
        } catch (IOException e) {
            System.err.println("Error processing JAR files: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 