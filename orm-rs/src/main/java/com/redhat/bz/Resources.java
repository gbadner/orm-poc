package com.redhat.bz;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;


/**
 * Created by spagop on 08/01/2017.
 */
@Stateless
public class Resources {

    public static final String DATABASE_JNDI_NAME = "java:jboss/datasources/TEST_MYSQLDS";

    @Resource(lookup = DATABASE_JNDI_NAME)
    private DataSource dataSource;

    @Produces
    public DataSource createDataSource() {
        return dataSource;
    }
}
