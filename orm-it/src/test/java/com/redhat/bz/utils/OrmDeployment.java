package com.redhat.bz.utils;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public class OrmDeployment {

    public static WebArchive deployment(String archiveName, boolean withEmptyAssetForBeansXml) {
        File[] artifacts = Maven.resolver().loadPomFromFile("pom.xml").resolve(
            "javax.money:money-api",
            "org.javamoney.moneta:moneta-core",
            "org.dbunit:dbunit",
            "mysql:mysql-connector-java",
            "com.github.librepdf:openpdf",
            "org.subethamail:subethasmtp",
            "com.braintreepayments.gateway:braintree-java",
            "org.freemarker:freemarker",
            "org.xmlunit:xmlunit-core",
            "org.xmlunit:xmlunit-matchers",
            "org.flywaydb:flyway-core",
            "com.google.zxing:core",
            "com.google.zxing:javase",
            "com.google.guava:guava",
            "org.bitbucket.b_c:jose4j",
            "io.rest-assured:rest-assured"
        ).withTransitivity().asFile();

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, archiveName)
            .addPackages(true, "com.redhat.bz")
            .addAsLibraries(artifacts);
        // Loader for common classes
        LoaderForCommonClasses.addClasses(webArchive);
        // WEB-INF
        WebInfRes.addWebInfRes(webArchive, withEmptyAssetForBeansXml);
        // META-INF
        MetaInfRes.addMetaInfRes(webArchive);


        return webArchive;
    }
}
