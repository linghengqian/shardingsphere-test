package com.hulalaga;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.ShardingSphereDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings({"SqlNoDataSourceInspection", "resource"})
@Testcontainers
public class ShardingSphereTest {
    private static JdbcTemplate JDBC_TEMPLATE;

    @Container
    public static final MySQLContainer<?> CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"))
            .withDatabaseName("the_database")
            .withInitScript("init-schema.sql");

    @BeforeAll
    static void initDataSource() {
        assertThat(System.getProperty("fixture.mysql.db.url"), is(nullValue()));
        assertThat(System.getProperty("fixture.mysql.db.username"), is(nullValue()));
        assertThat(System.getProperty("fixture.mysql.db.password"), is(nullValue()));
        assertThat(System.getProperty("fixture.mysql.db.databaseName"), is(nullValue()));
        System.setProperty("fixture.mysql.db.url", CONTAINER.getJdbcUrl());
        System.setProperty("fixture.mysql.db.username", CONTAINER.getUsername());
        System.setProperty("fixture.mysql.db.password", CONTAINER.getPassword());
        System.setProperty("fixture.mysql.db.databaseName", CONTAINER.getDatabaseName());
        final HikariDataSource poolDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(ShardingSphereDriver.class.getName())
                .url("jdbc:shardingsphere:classpath:shardingsphere.yaml?placeholder-type=system_props")
                .build();
        JDBC_TEMPLATE = new JdbcTemplate(poolDataSource);
    }

    @AfterAll
    static void afterAll() {
        System.clearProperty("fixture.mysql.db.url");
        System.clearProperty("fixture.mysql.db.username");
        System.clearProperty("fixture.mysql.db.password");
        System.clearProperty("fixture.mysql.db.databaseName");
    }

    @Test
    public void testDruidConnectionDisabled() {
        IntStream.range(0, 50).forEachOrdered(i -> {
            assertDoesNotThrow(() -> JDBC_TEMPLATE.execute("select ''"));
            assertDoesNotThrow(() -> JDBC_TEMPLATE.execute("insert into the_table(id) values(1)"));
        });
    }
}
