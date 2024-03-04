package tudelft.wis.idm_tasks.basicJDBC.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class JDBCTask2Imp implements JDBCTask2Interface {

    public Connection getConnection() {
        try {
            JDBCManager DbManager = new JDBCManagerImp();
            Connection conn = DbManager.getConnection();
            return conn;
        } catch (ClassNotFoundException classExpt) {
            System.out.println("Class not found!");
            return null;
        } catch (SQLException sqlExpt){
            System.out.println("SQL query unsuccessful!");
            return null;
        }
    }

    public Collection<String> getTitlesPerYear(int year){
        Connection conn = this.getConnection();
        String query = "SELECT titles.primary_title FROM titles WHERE titles.start_year=" + year;
        Collection<String> coll = new ArrayList<String>();
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                coll.add(rs.getString("primary_title"));
            }
            return coll;
        } catch (SQLException e){
            return null;
        }
    }

    public Collection<String> getJobCategoriesFromTitles(String searchString){
        Connection conn = this.getConnection();
        String query = "SELECT cast_info.job_category FROM cast_info JOIN titles ON titles.title_id = cast_info.title_id WHERE titles.primary_title LIKE '%" + searchString + "%'";
        Collection<String> coll = new ArrayList<String>();
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                coll.add(rs.getString("job_category"));
            }
            return coll;
        } catch (SQLException e){
            return null;
        }
    }

    public double getAverageRuntimeOfGenre(String genre){
        Connection conn = this.getConnection();
        String query = "SELECT AVG(titles.runtime) FROM titles JOIN titles_genres ON titles.title_id = titles_genres.title_id WHERE titles_genres.genre='" + genre + "'";
        double avg = 0;
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                avg = rs.getDouble("avg");
            }
            return avg;
        } catch (SQLException e){
            return 0;
        }
    }

    public Collection<String> getPlayedCharacters(String actorFullname){
        Connection conn = this.getConnection();
        String query = "SELECT tpc.character_name FROM title_person_character AS tpc JOIN persons ON persons.person_id = tpc.person_id WHERE persons.full_name='" + actorFullname + "'";
        Collection<String> coll = new ArrayList<String>();
        try (Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                coll.add(rs.getString("character_name"));
            }
            return coll;
        } catch (SQLException e){
            return null;
        }
    }
}
