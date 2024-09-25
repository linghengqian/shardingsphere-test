package io.github.linghengqian;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.infra.datasource.pool.destroyer.DataSourcePoolActiveDetector;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class DruidDataSourcePoolActiveDetector implements DataSourcePoolActiveDetector {

    @Override
    public boolean containsActiveConnection(final DataSource dataSource) {
        return 0 != getActiveConnections(dataSource);
    }

    private int getActiveConnections(final DataSource dataSource) {
        try (DruidDataSource druidDataSource = dataSource.unwrap(DruidDataSource.class)) {
            return druidDataSource.getActiveConnections().size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getType() {
        return "com.alibaba.druid.pool.DruidDataSource";
    }
}
