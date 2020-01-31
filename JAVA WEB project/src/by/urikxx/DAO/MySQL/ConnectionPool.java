package by.urikxx.DAO.MySQL;

import by.urikxx.util.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final int POOL_SIZE = 5;
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connections;

    // SINGLETON
    public synchronized static ConnectionPool getInstance(){
        if(instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        return connections.take();
    }

    public void closeConnection(Connection connection){
        if (connection != null)
            connections.offer(connection);
    }

    private ConnectionPool(){
        connections = new ArrayBlockingQueue<Connection>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++){
            Connection connection;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(ConfigurationManager.getInstance().mysqlConnection,  ConfigurationManager.getInstance().mysqlUsername, ConfigurationManager.getInstance().mysqlPassword);
                connections.offer(connection);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
