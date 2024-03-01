package tudelft.wis.idm_tasks.basicJDBC.interfaces;
import java.sql.*;
import java.util.*;
import java.io.*;

public class JDBCManagerImp implements JDBCManager {

        public Connection getConnection () throws SQLException, ClassNotFoundException {
            try {
                String url = DbConfig.getDbUrl();
                String password = DbConfig.getDbPassword();
                String username = DbConfig.getDbUsername();
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
