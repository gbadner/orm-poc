package com.redhat.bz.utils;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author spagop
 */
public class RunSQLScript {

    public static void execute(String jdbcDriver, String jdbcUrl, String username, String password, String sqlPath) {
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement stmt = null;


        // Initialize object for ScripRunner
        ScriptRunner sr = new ScriptRunner(con);

        // Give the input file to Reader
        InputStream inputStream = FileLoader.loadAsString(sqlPath);
        Reader reader = new InputStreamReader(inputStream);

        // Exctute script
        sr.runScript(reader);


    }
}
