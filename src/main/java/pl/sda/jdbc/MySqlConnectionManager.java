package pl.sda.jdbc;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.util.Properties;
import javax.sql.DataSource;

final class MySqlConnectionManager extends ConnectionManager {

    public MySqlConnectionManager(Properties properties) {
        super(properties);
    }

    @Override
    DataSource createDataSource(String url, String user, String password) {
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
