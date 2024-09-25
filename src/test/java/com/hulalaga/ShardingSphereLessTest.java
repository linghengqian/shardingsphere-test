package com.hulalaga;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"SqlNoDataSourceInspection", "resource"})
@Testcontainers
public class ShardingSphereLessTest {
    private static JdbcTemplate JDBC_TEMPLATE;

    @Container
    public static final MySQLContainer<?> CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"))
            .withDatabaseName("the_database")
            .withUsername("root")
            .withPassword("")
            .withInitScript("init-schema.sql");

    @BeforeAll
    static void initDataSource() {
        final DruidDataSource poolDataSource = DataSourceBuilder.create()
                .type(DruidDataSource.class)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(CONTAINER.getJdbcUrl())
                .username("readonly")
                .password("testPassword")
                .build();
        JDBC_TEMPLATE = new JdbcTemplate(poolDataSource);
    }

    @Test
    public void testDruidConnectionDisabled() {
        IntStream.range(0, 50).forEachOrdered(i -> {
            assertDoesNotThrow(() -> JDBC_TEMPLATE.execute("select ''"));
            assertThrows(BadSqlGrammarException.class, () -> JDBC_TEMPLATE.execute("insert into the_table(id) values(1)"));
        });
    }
}
