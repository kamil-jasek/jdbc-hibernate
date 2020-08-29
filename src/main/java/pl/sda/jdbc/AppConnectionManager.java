package pl.sda.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

final class AppConnectionManager {

    public static void main(String[] args) throws IOException {
        Properties properties = loadDatabaseProperties();
        ConnectionManager manager = new MySqlConnectionManager(properties);

        try (Connection conn = manager.getConnection()) {
            System.out.println("connected!");

            final Statement stmt = conn.createStatement();
            stmt.execute("select lastName, firstName from employees");

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                System.out.println(rs.getString("lastName") + ", " + rs.getString("firstName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadDatabaseProperties() throws IOException {
        Properties properties = new Properties(); // Map<String, String>
        properties.load(AppConnectionManager.class.getClassLoader()
            .getResourceAsStream("database.properties"));
        return properties;
    }
}
