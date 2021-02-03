package com.redhat.bz;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.sql.DataSource;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class DBMigration {

    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void onStartup() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                //.baselineOnMigrate(true)
                //.baselineVersion("0.0")
                .cleanDisabled(false)
                .load();
        //flyway.clean();
        flyway.migrate();

    }
}
