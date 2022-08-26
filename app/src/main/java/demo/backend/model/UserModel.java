package demo.backend.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.backend.entity.User;

public class UserModel {
    // read data from database with key=userId
    public User read(Integer userId) {
        String databaseURL = "jdbc:mysql://localhost:3306/demo";
        String userName = "lvnam";
        String password = "nam0801";
        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
        User user = null;
        Connection conn = null;
        try {
            // connect with database using driverManager in jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(databaseURL, userName, password);
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            user = new User();
            // retrieve data in rs ResultSet
            while (rs.next()) {
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Maybe userName/password invalid");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not find database driver class");
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    // read data from database with key=userId
    public User create(Integer userId, String name, Integer age) {
        String databaseURL = "jdbc:mysql://localhost:3306/demo";
        String userName = "lvnam";
        String password = "nam0801";
        String sqlUpdate = "INSERT INTO user(user_id, name, age) VALUES(?, ?, ?)";
        User user = null;
        Connection conn = null;
        try {
            // connect with database using driverManager in jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(databaseURL, userName, password);
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            Integer suc = stmt.executeUpdate();
            // retrieve data in rs ResultSet
            if (suc == 1) {
                user = new User(userId, name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Maybe userName/password invalid");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not find database driver class");
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    public User update(Integer userId, String name, Integer age){
        String databaseURL = "jdbc:mysql://localhost:3306/demo";
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String userName = "lvnam";
        String password = "nam0801";
        String sqlUpdate = "UPDATE user SET(name = ?, age = ?) WHERE user_id = ?";
        User user = null;
        Connection conn = null;
        try{
            // specify driver for Driver Manager load
            Class.forName(driverClassName);
            // get connection by DriverManager class
            conn = DriverManager.getConnection(databaseURL, userName, password);
            // set up sql statement to execute in the database through jdbc connector
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(3, userId);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            Integer suc = stmt.executeUpdate();
            if(suc == 1){
                user = new User(userId, name, age);
            }
        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("An error occurred. Maybe username/password invalid");
            System.out.println(e.getMessage());
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Could not find database driver class");
            System.out.println(e.getMessage());
        } finally{
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Close connection error");
                    System.out.println(e.getMessage());
                }
            }
        }
        return user;
    }

    public User delete(Integer userId){
        String databaseURL = "jdbc:mysql://localhost:3306/demo";
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String userName = "lvnam";
        String password = "nam0801";
        String sqlUpdate = "DELETE FROM user WHERE user_id= ?";
        User user = null;
        Connection conn = null;
        try {
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(databaseURL, userName, password);
            PreparedStatement stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(1, userId);
            Integer suc = stmt.executeUpdate();
            if(suc == 1){
                user = new User(userId, null, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Maybe username/password invalid");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Could not find database driver class");
        } finally{
            if(conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    System.out.println("Could not close conn connection");
                    System.out.println(e.getMessage());
                }
            }
        }
        return user;
    }
}