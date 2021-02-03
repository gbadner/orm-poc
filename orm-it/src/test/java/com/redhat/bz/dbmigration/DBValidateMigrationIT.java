package com.redhat.bz.dbmigration;

import com.redhat.bz.utils.AbstractIT;
import com.redhat.bz.utils.OrmDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class DBValidateMigrationIT {


    @AfterClass
    public static void deleteSchema() throws Exception {
        AbstractIT.deleteSchema();
    }

    @Deployment(name = "first", order = 1)
    public static WebArchive createFirstDeployment() {
        return OrmDeployment.deployment("senbonfirstmigration.war", true)
            .addAsResource("db/migration/V1.0__Initial_migration.sql")
            .addAsResource("db/migration/V1.1__extends_partner_add_new_tables.sql");

    }


    @Deployment(name = "second", order = 2)
    public static WebArchive createSecondDeployment() {
        return OrmDeployment.deployment("senbonsecondmigration.war", true)
            .addAsResource("db/migration/V1.0__Initial_migration.sql")
            .addAsResource("db/migration/V1.1__extends_partner_add_new_tables.sql")
            .addAsWebInfResource("second-jboss-web.xml", "jboss-web.xml")
            .addAsResource("schema-validation-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void testValidate() {
        assertTrue(true);
    }
}
