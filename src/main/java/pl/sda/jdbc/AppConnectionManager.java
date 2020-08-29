package pl.sda.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

final class AppConnectionManager {

    public static void main(String[] args) throws IOException {
        Properties properties = loadDatabaseProperties();
        ConnectionManager manager = new MySqlConnectionManager(properties);

        final int id = ThreadLocalRandom.current().nextInt(10000, 99999999);
        boolean inserted = insertEmployee(manager, new Employee(id, "Jan", "Kowalski", "ext", "jk@wp.pl", "1", "dev"));
        if (inserted) {
            Employee employee = getEmployee(manager, id);
            System.out.println(employee);
        }

        final boolean updated = updateEmployeeName(manager, id, "Adam", "Nowak");
        if (updated) {
            Employee employee = getEmployee(manager, id);
            System.out.println(employee);
        }
    }

    private static boolean insertEmployee(ConnectionManager manager, Employee employee) {
        try (Connection connection = manager.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement stmt = connection.prepareStatement(
                "insert  into employees(`employeeNumber`,`lastName`,`firstName`,`extension`,"
                    + "`email`,`officeCode`,`jobTitle`) values \n"
                    + "(?, ?, ?, ?, ?, ?, ?)");

            stmt.setInt(1, employee.getId());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getFirstName());
            stmt.setString(4, employee.getExtension());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getOfficeCode());
            stmt.setString(7, employee.getJobTitle());

            final int inserted = stmt.executeUpdate();
            connection.commit();
//            connection.rollback();
            return inserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Employee getEmployee(ConnectionManager manager, int id) {
        try (Connection conn = manager.getConnection()) {
            System.out.println("connected!");

            final PreparedStatement stmt = conn.prepareStatement(
                "select employeeNumber, lastName, firstName "
                + "from employees where employeeNumber = ?");

            stmt.setInt(1, id);
            stmt.execute();

//            stmt.execute("select employeeNumber, lastName, firstName "
//                + "from employees where employeeNumber = " + id);

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                return new Employee(
                    rs.getInt("employeeNumber"),
                    rs.getString("firstName"),
                    rs.getString("lastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean updateEmployeeName(ConnectionManager manager, int id, String firstName, String lastName) {
        try (Connection conn = manager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                "update employees set firstName = ?, lastName = ? where employeeNumber = ?");

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, id);

            final int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Properties loadDatabaseProperties() throws IOException {
        Properties properties = new Properties(); // Map<String, String>
        properties.load(AppConnectionManager.class.getClassLoader()
            .getResourceAsStream("database.properties"));
        return properties;
    }
}
