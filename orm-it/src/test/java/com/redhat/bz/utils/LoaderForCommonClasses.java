package com.redhat.bz.utils;

import com.redhat.bz.EntityManagerProducer;
import com.redhat.bz.Resources;
import com.redhat.bz.das.AbstractDAS;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.util.stream.Collectors;

public class LoaderForCommonClasses {
    public static void addClasses(WebArchive webArchive) {
        webArchive.addClasses(
            AbstractIT.class,
            AbstractDAS.class,
            CorsFilter.class,
            Collectors.class,
            DBUnitUtils.class,
            RunSQLScript.class,
            EntityManagerProducer.class,
            Resources.class
        );
    }
}
