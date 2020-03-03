package by.urikxx.DAO.MySQL;

import by.urikxx.DAO.Interfaces.UserDAO;
import by.urikxx.models.Student;
import by.urikxx.models.Teacher;
import by.urikxx.models.User;
import by.urikxx.util.ConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;


public class MySQLUserDAO extends MySQLJDBC implements UserDAO {

    private static final Logger logger = Logger.getLogger(MySQLUserDAO.class);

    @Override
    public User login(String login, String password) {

        User user = null;
        boolean auth = false;
        String roleShortName = null;
        String name = null;
        int userId = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            CallableStatement callableStatement  = conn.prepareCall(ConfigurationManager.getInstance().select_user_sql);
            callableStatement.setString("loginName", login.trim());
            callableStatement.setString("password", password.trim());
            callableStatement.registerOutParameter("auth", Types.BIT);
            callableStatement.registerOutParameter("role_short_name", Types.NVARCHAR);
            callableStatement.registerOutParameter("name", Types.NVARCHAR);
            callableStatement.registerOutParameter("userId", Types.INTEGER);
            callableStatement.execute();
            auth = callableStatement.getBoolean("auth");
            roleShortName = callableStatement.getString("role_short_name");
            name = callableStatement.getString("name");
            userId = callableStatement.getInt("userId");
        }
        catch(Exception ex){
            logger.warn(ex.getMessage());
        }finally {
            connectionPool.closeConnection(conn);
        }

        if (auth){
            if (roleShortName.equals("s")){
                user = new Student(userId, name, login);
            }
            else if (roleShortName.equals("t")){
                user = new Teacher(userId, name, login);
            }
            return user;
        } else
            return null;
    }

    @Override
    public User singUp(String userName, String login, String password, String role) {

        int rows = 0;
        User user = null;
        boolean reg = false;
        String roleShortName = null;
        String name = null;
        int userId = 0;

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            CallableStatement callableStatement = conn.prepareCall(ConfigurationManager.getInstance().insert_user_sql);
            callableStatement.setString("userName", userName.trim());
            callableStatement.setString("loginName", login.trim());
            callableStatement.setString("password", password.trim());
            callableStatement.setString("role", role);
            callableStatement.registerOutParameter("userId", Types.INTEGER);
            callableStatement.registerOutParameter("reg", Types.BIT);
            callableStatement.execute();
            reg = callableStatement.getBoolean("reg");
            userId = callableStatement.getInt("userId");
        }
        catch(Exception ex){
            logger.warn(ex.getMessage());
        }finally {
            connectionPool.closeConnection(conn);
        }

        if (reg){
            if (role.equals("s")){
                user = new Student(userId, userName, login);
            }else if (role.equals("t")){
                user = new Teacher(userId, userName, login);
            }
            return  user;
        }else
            return null;
    }

    @Override
    public ArrayList<User> getTeachers() {
        return null;
    }

    @Override
    public ArrayList<User> getStudents() {
        return null;
    }
}
