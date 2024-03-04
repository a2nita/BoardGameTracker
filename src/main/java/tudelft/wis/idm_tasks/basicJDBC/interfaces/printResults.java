package tudelft.wis.idm_tasks.basicJDBC.interfaces;
import java.util.*;
import java.io.*;

public class printResults {
    // Calls all methods of JDBCTask2Interface and prints the first 20 results of each query
    public static void print() {
        Scanner s = new Scanner(System.in);
        // Query 1 - getTitlesPerYear
        System.out.println("Please type in a year:");
        int year = s.nextInt();
        Collection<String> rs = JDBCTask2Interface.getTitlesPerYear(year);

        // Query 2 - getJobCategoriesFromTitles
        System.out.println("Please type in a primary title:");
        String title = s.nextLine();

        // Query 3 - getAverageRuntimeOfGenre
        System.out.println("Please type in a genre:");
        String genre = s.nextLine();

        // Query 4 - getPlayedCharacters
        System.out.println("Please type in a person's name:");
        String actor = s.nextLine();
    }
}
