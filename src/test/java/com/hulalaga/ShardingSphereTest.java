package com.hulalaga;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.ShardingSphereDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Testcontainers
public class ShardingSphereTest {
    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>()
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
        final Resource resource = RESOURCE_PATTERN_RESOLVER.getResource("classpath:shardingsphere.yaml");
        String shardingsphereConfig =
                String.join("\n", Files.readAllLines(Paths.get(resource.getURI())));
        shardingsphereConfig = shardingsphereConfig.replace("<MYSQL_DATABASE>", MY_SQL_CONTAINER.getDatabaseName());
        shardingsphereConfig = shardingsphereConfig.replace("<MYSQL_JDBC_URL>", MY_SQL_CONTAINER.getJdbcUrl());
        shardingsphereConfig = shardingsphereConfig.replace("<MYSQL_USER>", MY_SQL_CONTAINER.getUsername());
        shardingsphereConfig = shardingsphereConfig.replace("<MYSQL_PASSWORD>", MY_SQL_CONTAINER.getPassword());
        final Path tempFile = Files.createTempFile(null, "shardingsphere.yaml");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write(shardingsphereConfig);
        }
        // this pool datasource will cause 'connection disabled',
        // because 'getConnections' reuse connections which disabled by DruidPooledConnection
        // in org.apache.shardingsphere.driver.jdbc.core.connection.ConnectionManager#getConnections
        final HikariDataSource poolDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(ShardingSphereDriver.class.getName())
                .url("jdbc:shardingsphere:" + tempFile)
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
}
