

import java.util.Scanner;

public class Scanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input phase
        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your age:");
        int age = scanner.nextInt();

        // Process phase
        String message;
        if (age >= 18) {
            message = "You are an adult.";
        } else {
            message = "You are a minor.";
        }

        // Output phase
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println(message);

        // Close the scanner
        scanner.close();
    }
}