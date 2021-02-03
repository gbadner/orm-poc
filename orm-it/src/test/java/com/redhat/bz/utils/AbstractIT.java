package com.redhat.bz.utils;

import com.mysql.jdbc.Driver;
import org.junit.AfterClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


public abstract class AbstractIT {
    protected static final String MYSQL_JDBC_DRIVER = Driver.class.getName();


    protected static final String JDBC_URL = "jdbc:mysql://192.168.99.100:3306/testdb";

    protected static final String USER = "user1";
    protected static final String PASSWORD = "user1";
    protected static final String TARGET_DIR = System.getProperty("user.dir") + File.separator + "target";
    protected static final Path DOWNLOAD_DIR = Paths.get(TARGET_DIR, "download");


    @AfterClass
    public static void  deleteSchema() throws Exception {
        TimeUnit.SECONDS.sleep(10);
        RunSQLScript.execute(MYSQL_JDBC_DRIVER, JDBC_URL, USER, PASSWORD, "sql/after.sql");
    }

    protected static Path createDirectories(Path path) throws IOException {
        return Files.createDirectories(DOWNLOAD_DIR);
    }

    protected boolean deleteIfExists(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

}
