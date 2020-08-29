package pl.sda.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

public class JdbcApp {

    public static void main(String[] args) {

        // old way to connect - DriverManager
        // try with resources
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa")) {
            System.out.println("connected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // old way with finally block
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
            System.out.println("connected again!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        // new way DataSource
        DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "sa");
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("connected via datasource");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
