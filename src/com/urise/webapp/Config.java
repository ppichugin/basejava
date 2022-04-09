package com.urise.webapp;

import com.urise.webapp.sql.ConnectionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Properties props = new Properties();
    private final File storageDir;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private final ConnectionFactory sqlStorage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
            sqlStorage = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public ConnectionFactory getSqlStorage() {
        return sqlStorage;
    }
}
