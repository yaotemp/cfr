package org.benf.cfr.examples;

/**
 * A simple Hello World example to demonstrate CFR's decompilation capabilities.
 */
public class HelloWorld {
    private static final String MESSAGE = "Hello, World!";
    
    /**
     * Main method that prints a greeting message.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Print a greeting message
        System.out.println(MESSAGE);
        
        // Demonstrate some Java features that CFR handles well
        for (int i = 0; i < 3; i++) {
            printNumber(i);
        }
        
        // Lambda expression example
        Runnable runnable = () -> System.out.println("This is a lambda expression");
        runnable.run();
        
        // Try-with-resources example
        try (AutoCloseable resource = new AutoCloseable() {
            @Override
            public void close() throws Exception {
                System.out.println("Resource closed");
            }
        }) {
            System.out.println("Using a resource in try-with-resources");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Print a number with a message.
     * 
     * @param num The number to print
     */
    private static void printNumber(int num) {
        switch (num) {
            case 0:
                System.out.println("Zero");
                break;
            case 1:
                System.out.println("One");
                break;
            default:
                System.out.println("Number: " + num);
                break;
        }
    }
} 