package com.redhat.bz.utils;


import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author spagop
 */
public class DBUnitUtils {

    private static DatabaseConnection databaseConnection;

    public static void createDataset(String jdbcDriver, String jdbcUrl, String username, String password, String dataset) {

        try {
            initConn(jdbcDriver, jdbcUrl, username, password);
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(FileLoader.loadAsString(dataset));
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            databaseConnection.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.FALSE);
            DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("could not initialize dataset:" + dataset + " \nmessage: " + e.getMessage());
        } finally {
            closeConn();
        }
    }

    public static void deleteDataset(String jdbcDriver, String jdbcUrl, String username, String password, String dataset) {
        try {
            initConn(jdbcDriver, jdbcUrl, username, password);
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(FileLoader.loadAsString(dataset));
            DatabaseOperation.DELETE_ALL.execute(databaseConnection, dataSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("could not delete dataset dataset:" + dataset + " \nmessage: " + e.getMessage());
        } finally {
            closeConn();
        }
    }

    private static void closeConn() {
        try {
            if (databaseConnection != null && !databaseConnection.getConnection().isClosed()) {
                databaseConnection.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("could not close conection \nmessage: " + e.getMessage());
        }

    }

    private static void initConn(String jdbcDriver, String jdbcUrl, String username, String password) throws SQLException, NamingException, DatabaseUnitException {
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, username, password);
            databaseConnection = new DatabaseConnection(con);
            DatabaseConfig config = databaseConnection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.FALSE);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("could not get conection \nmessage: " + e.getMessage());
        }

    }
}
