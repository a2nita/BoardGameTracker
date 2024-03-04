package tudelft.wis.idm_tasks.basicJDBC.interfaces;
import java.util.*;
import java.io.*;

public class PrintResults {
    // Calls all methods of JDBCTask2Interface and prints the first 20 results of each query

    public static void print() {
        Scanner s = new Scanner(System.in);
        // Query 1 - getTitlesPerYear
        System.out.println("Please type in a year:");
        int year = s.nextInt();
        JDBCTask2Interface obj = new JDBCTask2Imp();
        Collection<String> rs = obj.getTitlesPerYear(year);
        Scanner m = new Scanner(String.valueOf(rs)).useDelimiter(", ");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            output.append(m.next()).append("\n");
        }
        System.out.println(output);

        Scanner s2 = new Scanner(System.in);
        // Query 2 - getJobCategoriesFromTitles
        System.out.println("Please type in a primary title:");
        String title = s2.nextLine();
        Collection<String> rs2 = obj.getJobCategoriesFromTitles(title);
        Scanner m2 = new Scanner(String.valueOf(rs2)).useDelimiter(", ");
        StringBuilder output2 = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            output2.append(m2.next()).append("\n");
        }
        System.out.println(output2);

        Scanner s3 = new Scanner(System.in);
        // Query 3 - getAverageRuntimeOfGenre
        System.out.println("Please type in a genre:");
        String genre = s3.nextLine();
        System.out.println(obj.getAverageRuntimeOfGenre(genre));
        System.out.println("\n");


        Scanner s4 = new Scanner(System.in);
        // Query 4 - getPlayedCharacters
        System.out.println("Please type in a person's name:");
        String actor = s4.nextLine();
        Collection<String> rs4 = obj.getPlayedCharacters(actor);
        Scanner m4 = new Scanner(String.valueOf(rs4)).useDelimiter(", ");
        StringBuilder output4 = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            output4.append(m4.next()).append("\n");
        }
        System.out.println(output4);
    }
}
