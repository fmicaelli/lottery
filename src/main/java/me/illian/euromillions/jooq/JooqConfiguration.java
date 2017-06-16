package me.illian.euromillions.jooq;

import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfiguration {

    @Bean
    public ConnectionProvider connectionProvider(final DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Bean
    public org.jooq.Configuration jooqConfig(final ConnectionProvider connectionProvider) {
        return new DefaultConfiguration()
                .derive(connectionProvider)
                .derive(SQLDialect.POSTGRES);
    }

    @Bean
    public DSLContext dslContext(final org.jooq.Configuration config) {
        final DefaultDSLContext defaultDSLContext = new DefaultDSLContext(config);
        // ensure that the database is reachable
        defaultDSLContext.select().execute();
        return defaultDSLContext;
    }
}
