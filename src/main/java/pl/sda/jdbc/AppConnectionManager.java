package pl.sda.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

final class AppConnectionManager {

    public static void main(String[] args) throws IOException {
        Properties properties = loadDatabaseProperties();
        ConnectionManager manager = new MySqlConnectionManager(properties);

        Employee employee = getEmployee(manager, "2");
        System.out.println(employee);
    }

    private static Employee getEmployee(ConnectionManager manager, String id) {
        try (Connection conn = manager.getConnection()) {
            System.out.println("connected!");

            final PreparedStatement stmt = conn.prepareStatement(
                "select employeeNumber, lastName, firstName "
                + "from employees where employeeNumber = ?");

            stmt.setString(1, id);
            stmt.execute();

//            stmt.execute("select employeeNumber, lastName, firstName "
//                + "from employees where employeeNumber = " + id);

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                return new Employee(
                    rs.getString("employeeNumber"),
                    rs.getString("firstName"),
                    rs.getString("lastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Properties loadDatabaseProperties() throws IOException {
        Properties properties = new Properties(); // Map<String, String>
        properties.load(AppConnectionManager.class.getClassLoader()
            .getResourceAsStream("database.properties"));
        return properties;
    }
}
