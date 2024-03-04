package tudelft.wis.idm_tasks.basicJDBC.interfaces;
import java.sql.*;
import java.util.*;
import java.io.*;

public class JDBCManagerImp implements JDBCManager {

        public Connection getConnection () throws SQLException, ClassNotFoundException {
            try {
                String url = "jdbc:postgresql://localhost:5432/imdb";
                String password = "!@9Xp49LbTq";
                String username = "postgres";
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(url, username, password);
                return conn;
            } catch (SQLException sqlExpt){
                System.out.println("Unable to set connection");
                return null;
            } catch (ClassNotFoundException classExpt) {
                System.out.println("Class Not Found!");
                return null;
            }
        }

}
