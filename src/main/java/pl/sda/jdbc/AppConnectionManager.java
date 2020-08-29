package pl.sda.jdbc;

import java.io.IOException;
import java.util.Properties;

final class AppConnectionManager {

    public static void main(String[] args) throws IOException {
        Properties properties = loadDatabaseProperties();
        ConnectionManager manager = new H2PoolDataSource(properties);

    }

    private static Properties loadDatabaseProperties() throws IOException {
        Properties properties = new Properties(); // Map<String, String>
        properties.load(AppConnectionManager.class.getClassLoader()
            .getResourceAsStream("database.properties"));
        return properties;
    }
}
