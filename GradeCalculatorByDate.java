import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeCalculatorByDate {

    // Simple model class to represent an assignment
    static class Assignment {
        String name;
        LocalDate dueDate;
        double maxPoints;

        Assignment(String name, LocalDate dueDate, double maxPoints) {
            this.name = name;
            this.dueDate = dueDate;
            this.maxPoints = maxPoints;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1) Define a list of assignments with their due dates and max points (all in 2025 now)
        List<Assignment> allAssignments = new ArrayList<>();
        allAssignments.add(new Assignment("Debugging 1-1",  LocalDate.of(2025, 1, 28),  5));
        allAssignments.add(new Assignment("Debugging 1-2",  LocalDate.of(2025, 1, 28),  5));
        allAssignments.add(new Assignment("Programming 1-6", LocalDate.of(2025, 1, 30), 10));
        allAssignments.add(new Assignment("Programming 1-10",LocalDate.of(2025, 1, 30), 10));
        allAssignments.add(new Assignment("Chapter 1 Test",  LocalDate.of(2025, 2, 4),  20));
        allAssignments.add(new Assignment("Debugging 2-1",   LocalDate.of(2025, 2, 6),  10));
        allAssignments.add(new Assignment("Debugging 2-2",   LocalDate.of(2025, 2, 6),  10));
        allAssignments.add(new Assignment("Programming 2-7", LocalDate.of(2025, 2, 11), 10));
        allAssignments.add(new Assignment("Programming 2-9", LocalDate.of(2025, 2, 11), 10));
        allAssignments.add(new Assignment("Chapter 2 Test",  LocalDate.of(2025, 2, 13), 20));
        allAssignments.add(new Assignment("Debugging 3-1",   LocalDate.of(2025, 2, 18), 10));
        allAssignments.add(new Assignment("Debugging 3-2",   LocalDate.of(2025, 2, 20), 10));
        allAssignments.add(new Assignment("Programming 3-1", LocalDate.of(2025, 2, 25), 10));
        allAssignments.add(new Assignment("Chapter 3 Test",  LocalDate.of(2025, 2, 27), 20));
        allAssignments.add(new Assignment("Mid Term Exam",   LocalDate.of(2025, 3, 6),  100));
        allAssignments.add(new Assignment("Debugging 4-1",   LocalDate.of(2025, 3, 18), 10));
        allAssignments.add(new Assignment("Debugging 4-2",   LocalDate.of(2025, 3, 20), 10));
        allAssignments.add(new Assignment("Debugging 5-1",   LocalDate.of(2025, 3, 25), 10));
        allAssignments.add(new Assignment("Debugging 5-2",   LocalDate.of(2025, 4, 1),  10));
        allAssignments.add(new Assignment("Programming 5-4", LocalDate.of(2025, 4, 3),  10));
        allAssignments.add(new Assignment("Programming 5-6", LocalDate.of(2025, 4, 8),  10));
        allAssignments.add(new Assignment("Chapter 5 Test",  LocalDate.of(2025, 4, 10), 20));
        allAssignments.add(new Assignment("Debugging 6-1",   LocalDate.of(2025, 4, 15), 10));
        allAssignments.add(new Assignment("Debugging 6-2",   LocalDate.of(2025, 4, 22), 10));
        allAssignments.add(new Assignment("Programming 6-2", LocalDate.of(2025, 4, 24), 10));
        allAssignments.add(new Assignment("Programming 6-3", LocalDate.of(2025, 4, 29), 10));
        allAssignments.add(new Assignment("Chapter 6 Test",  LocalDate.of(2025, 5, 1),  20));
        allAssignments.add(new Assignment("Final Exam",      LocalDate.of(2025, 5, 8),  100));

        // 2) Ask the user for today's date
        System.out.print("Enter today's date (MM/dd/yyyy): ");
        String dateInput = input.nextLine();

        LocalDate currentDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            currentDate = LocalDate.parse(dateInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please try again.");
            input.close();
            return; // or System.exit(1);
        }

        // 3) Filter assignments due on or before 'currentDate'
        List<Assignment> dueAssignments = new ArrayList<>();
        for (Assignment a : allAssignments) {
            if (!a.dueDate.isAfter(currentDate)) {
                dueAssignments.add(a);
            }
        }

        // 4) Prompt user for the score earned on each due assignment
        double totalEarned = 0.0;
        double totalPossible = 0.0;

        if (dueAssignments.isEmpty()) {
            System.out.println("\nNo assignments are due on or before this date.");
        } else {
            System.out.println("\nAssignments due on or before " + currentDate + ":\n");
            for (Assignment a : dueAssignments) {
                System.out.printf("Enter score earned for '%s' (Max %.2f): ",
                        a.name, a.maxPoints);
                double earnedScore = input.nextDouble();

                // Optionally clamp the score
                if (earnedScore < 0) earnedScore = 0;
                if (earnedScore > a.maxPoints) earnedScore = a.maxPoints;

                totalEarned += earnedScore;
                totalPossible += a.maxPoints;
            }
        }

        // 5) Calculate current percentage
        double currentPercentage = 0.0;
        if (totalPossible != 0) {
            currentPercentage = (totalEarned / totalPossible) * 100.0;
        }

        // 6) Determine letter grade based on the calculated percentage
        char letterGrade;
        if (currentPercentage >= 90) {
            letterGrade = 'A';
        } else if (currentPercentage >= 80) {
            letterGrade = 'B';
        } else if (currentPercentage >= 70) {
            letterGrade = 'C';
        } else if (currentPercentage >= 60) {
            letterGrade = 'D';
        } else {
            letterGrade = 'F';
        }

        // Display the results
        System.out.println("\n--- GRADE REPORT ---");
        System.out.println("Date: " + dateInput);
        System.out.printf("Assignments counted: %d%n", dueAssignments.size());
        System.out.printf("Points Earned: %.2f%n", totalEarned);
        System.out.printf("Points Possible: %.2f%n", totalPossible);
        System.out.printf("Current Percentage: %.2f%%%n", currentPercentage);
        System.out.println("Current Letter Grade: " + letterGrade);

        input.close();
    }
}
