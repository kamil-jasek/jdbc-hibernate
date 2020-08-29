package pl.sda.jdbc;

import java.util.Properties;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

final class H2PoolDataSource extends ConnectionManager {

    public H2PoolDataSource(Properties properties) {
        super(properties);
    }

    @Override
    DataSource createDataSource(String url, String user, String password) {
        return JdbcConnectionPool.create(url, user, password);
    }
}
