package com.hulalaga;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.ShardingSphereDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@Testcontainers
public class ShardingSphereTest {
    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"))
            .withUrlParam("userSSL", "false")
            .withDatabaseName("the_database");
    private static JdbcTemplate JDBC_TEMPLATE;

    @BeforeAll
    public static void dataSource() throws Exception {
        final DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(MY_SQL_CONTAINER.getUsername());
        dataSource.setPassword(MY_SQL_CONTAINER.getPassword());
        dataSource.setUrl(MY_SQL_CONTAINER.getJdbcUrl());

        // ### init-schema
        final SqlInitializationProperties sqlInitializationProperties = new SqlInitializationProperties();
        sqlInitializationProperties.setMode(DatabaseInitializationMode.ALWAYS);
        sqlInitializationProperties.setSchemaLocations(
                ImmutableList.of(
                        "classpath:init-schema.sql"
                )
        );
        final SqlDataSourceScriptDatabaseInitializer sqlDataSourceScriptDatabaseInitializer = new SqlDataSourceScriptDatabaseInitializer(
                DataSourceBuilder.derivedFrom(dataSource)
                        .username("root")
                        .build(),
                SqlDataSourceScriptDatabaseInitializer.getSettings(
                        sqlInitializationProperties
                )
        );
        sqlDataSourceScriptDatabaseInitializer.setResourceLoader(
                RESOURCE_PATTERN_RESOLVER
        );
        sqlDataSourceScriptDatabaseInitializer.afterPropertiesSet();

        // ### build jdbcTemplate

        // this datasource can't cause 'connection disabled', because no connection pool
//        final DataSource ds = ShardingSphereDataSourceFactory.createDataSource(
//                MY_SQL_CONTAINER.getDatabaseName(),
//                ImmutableMap.of(
//                        "db", dataSource
//                ),
//                Collections.emptyList(),
//                new Properties()
//        );
        assertThat(System.getProperty("fixture.mysql.db.url"), is(nullValue()));
        assertThat(System.getProperty("fixture.mysql.db.username"), is(nullValue()));
        assertThat(System.getProperty("fixture.mysql.db.password"), is(nullValue()));
        assertThat(System.getProperty("fixture.mysql.db.databaseName"), is(nullValue()));
        System.setProperty("fixture.mysql.db.url", MY_SQL_CONTAINER.getJdbcUrl());
        System.setProperty("fixture.mysql.db.username", MY_SQL_CONTAINER.getUsername());
        System.setProperty("fixture.mysql.db.password", MY_SQL_CONTAINER.getPassword());
        System.setProperty("fixture.mysql.db.databaseName", MY_SQL_CONTAINER.getDatabaseName());
        // this pool datasource will cause 'connection disabled',
        // because 'getConnections' reuse connections which disabled by DruidPooledConnection
        // in org.apache.shardingsphere.driver.jdbc.core.connection.ConnectionManager#getConnections
        final HikariDataSource poolDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(ShardingSphereDriver.class.getName())
                .url("jdbc:shardingsphere:classpath:shardingsphere.yaml?placeholder-type=system_props")
                .build();
        JDBC_TEMPLATE = new JdbcTemplate(poolDataSource);
    }

    @Test
    public void testDruidConnectionDisabled() {
        for (int i = 0; i < 50; i++) {
            Assertions.assertDoesNotThrow(() -> {
                JDBC_TEMPLATE.execute("select ''");
            });
            Assertions.assertThrows(Exception.class, () -> {
                JDBC_TEMPLATE.execute("insert into the_table(id) values(1)");
            });
        }
    }

    @AfterAll
    static void afterAll() {
        System.clearProperty("fixture.mysql.db.url");
        System.clearProperty("fixture.mysql.db.username");
        System.clearProperty("fixture.mysql.db.password");
        System.clearProperty("fixture.mysql.db.databaseName");
    }
}
