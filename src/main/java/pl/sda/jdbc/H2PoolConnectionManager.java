package pl.sda.jdbc;

import java.util.Properties;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

final class H2PoolConnectionManager extends ConnectionManager {

    public H2PoolConnectionManager(Properties properties) {
        super(properties);
    }

    @Override
    DataSource createDataSource(String url, String user, String password) {
        return JdbcConnectionPool.create(url, user, password);
    }
}
