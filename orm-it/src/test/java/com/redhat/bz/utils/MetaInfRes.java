package com.redhat.bz.utils;

import org.jboss.shrinkwrap.api.spec.WebArchive;

public class MetaInfRes {
    public static void addMetaInfRes(WebArchive webArchive) {
        webArchive.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsResource("db/migration/V1.0__Initial_migration.sql")
            .addAsResource("db/migration/V1.1__extends_partner_add_new_tables.sql");

    }
}
