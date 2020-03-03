package by.urikxx.DAO.MySQL;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class MySQLJDBC {
    private static final Logger logger = Logger.getLogger(Connection.class);
    public static Connection getConnection() throws SQLException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        }
        catch (Exception ex){
            logger.warn("Connection failed...");
            logger.warn(ex);
        }

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        } catch (Exception ex) {
            logger.warn("Connection failed...");
            logger.warn(ex);
        }

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}
