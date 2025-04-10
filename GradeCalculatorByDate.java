import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GradeCalculatorByDate {

    // Represents one assignment
    static class Assignment {
        String name;
        LocalDate dueDate;
        double maxPoints;
        String category;  // e.g., "Chapter Tests", "Debugging & Programming", "Mid Term", "Final Exam"

        Assignment(String name, LocalDate dueDate, double maxPoints, String category) {
            this.name = name;
            this.dueDate = dueDate;
            this.maxPoints = maxPoints;
            this.category = category;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1) Define a list of assignments with their due dates, max points, and categories (all for 2025)
        List<Assignment> allAssignments = new ArrayList<>();

        // Debugging & Programming (D&P) assignments
        allAssignments.add(new Assignment("Debugging 1-1",    LocalDate.of(2025, 1, 28),  5,  "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 1-2",    LocalDate.of(2025, 1, 28),  5,  "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 1-6",  LocalDate.of(2025, 1, 30), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 1-10", LocalDate.of(2025, 1, 30), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 2-1",    LocalDate.of(2025, 2, 6),  10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 2-2",    LocalDate.of(2025, 2, 6),  10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 2-7",  LocalDate.of(2025, 2, 11), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 2-9",  LocalDate.of(2025, 2, 11), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 3-1",    LocalDate.of(2025, 2, 18), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 3-2",    LocalDate.of(2025, 2, 20), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 3-1",  LocalDate.of(2025, 2, 25), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 4-1",    LocalDate.of(2025, 3, 18), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 4-2",    LocalDate.of(2025, 3, 20), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 5-1",    LocalDate.of(2025, 3, 25), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 5-2",    LocalDate.of(2025, 4, 1),  10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 5-4",  LocalDate.of(2025, 4, 3),  10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 5-6",  LocalDate.of(2025, 4, 8),  10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 6-1",    LocalDate.of(2025, 4, 15), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Debugging 6-2",    LocalDate.of(2025, 4, 22), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 6-2",  LocalDate.of(2025, 4, 24), 10, "Debugging & Programming"));
        allAssignments.add(new Assignment("Programming 6-3",  LocalDate.of(2025, 4, 29), 10, "Debugging & Programming"));

        // Chapter Tests
        allAssignments.add(new Assignment("Chapter 1 Test",   LocalDate.of(2025, 2, 4),  20, "Chapter Tests"));
        allAssignments.add(new Assignment("Chapter 2 Test",   LocalDate.of(2025, 2, 13), 20, "Chapter Tests"));
        allAssignments.add(new Assignment("Chapter 3 Test",   LocalDate.of(2025, 2, 27), 20, "Chapter Tests"));
        allAssignments.add(new Assignment("Chapter 5 Test",   LocalDate.of(2025, 4, 10), 20, "Chapter Tests"));
        allAssignments.add(new Assignment("Chapter 6 Test",   LocalDate.of(2025, 5, 1),  20, "Chapter Tests"));

        // Mid Term
        allAssignments.add(new Assignment("Mid Term Exam",    LocalDate.of(2025, 3, 6),  100, "Mid Term Exam"));

        // Final
        allAssignments.add(new Assignment("Final Exam",       LocalDate.of(2025, 5, 8),  100, "Final Exam"));

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
            return;
        }

        // 3) Filter assignments due on or before 'currentDate'
        List<Assignment> dueAssignments = new ArrayList<>();
        for (Assignment a : allAssignments) {
            if (!a.dueDate.isAfter(currentDate)) {
                dueAssignments.add(a);
            }
        }

        // 4) Prepare category-based accumulators (points earned + possible for each category)
        Map<String, Double> earnedByCategory = new HashMap<>();
        Map<String, Double> possibleByCategory = new HashMap<>();

        // We'll also track the grand totals
        double grandTotalEarned = 0.0;
        double grandTotalPossible = 0.0;

        // If no assignments are due yet, there's nothing to prompt for
        if (dueAssignments.isEmpty()) {
            System.out.println("\nNo assignments are due on or before " + currentDate + ".");
        } else {
            System.out.println("\nAssignments due on or before " + currentDate + ":\n");

            for (Assignment a : dueAssignments) {
                System.out.printf("Enter score earned for '%s' (Max %.2f) -> ",
                                  a.name, a.maxPoints);
                double earnedScore = input.nextDouble();
                if (earnedScore < 0) {
                    earnedScore = 0;
                }
                if (earnedScore > a.maxPoints) {
                    earnedScore = a.maxPoints;
                }

                // Add to category sums
                earnedByCategory.put(a.category,
                    earnedByCategory.getOrDefault(a.category, 0.0) + earnedScore);
                possibleByCategory.put(a.category,
                    possibleByCategory.getOrDefault(a.category, 0.0) + a.maxPoints);

                // Also add to grand totals
                grandTotalEarned += earnedScore;
                grandTotalPossible += a.maxPoints;
            }
        }
        input.close();

        // 5) Calculate the overall percentage
        double currentPercentage = 0.0;
        if (grandTotalPossible != 0) {
            currentPercentage = (grandTotalEarned / grandTotalPossible) * 100.0;
        }

        // 6) Determine letter grade
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

        // 7) Output the results

        // --- A) Category breakdown
        System.out.println("\n--- CATEGORY BREAKDOWN ---");
        if (earnedByCategory.isEmpty()) {
            System.out.println("(No earned scores to display.)");
        } else {
            for (String cat : earnedByCategory.keySet()) {
                double catEarned = earnedByCategory.get(cat);
                double catPossible = possibleByCategory.get(cat);
                System.out.printf("%s: %.2f / %.2f\n", cat, catEarned, catPossible);
            }
        }

        // --- B) Final totals and letter grade
        System.out.println("\n--- OVERALL GRADE REPORT ---");
        System.out.println("Date: " + dateInput);
        System.out.printf("Total Points Earned: %.2f\n", grandTotalEarned);
        System.out.printf("Total Points Possible: %.2f\n", grandTotalPossible);
        System.out.printf("Current Percentage: %.2f%%\n", currentPercentage);
        System.out.println("Current Letter Grade: " + letterGrade);
    }
}
