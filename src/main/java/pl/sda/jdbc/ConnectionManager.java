package pl.sda.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

public abstract class ConnectionManager {

    private final DataSource dataSource;

    public ConnectionManager(Properties properties) {
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String password = properties.getProperty("database.password");
        this.dataSource = createDataSource(url, user, password);
    }

    abstract DataSource createDataSource(String url, String user, String password);

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
