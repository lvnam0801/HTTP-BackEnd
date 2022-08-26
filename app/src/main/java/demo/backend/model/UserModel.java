package demo.backend.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.backend.entity.User;

public class UserModel {
    // read data from database with key=userId
    public User read(Integer userId) {

        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        User user = null;

        try {
            conn = DbCpModel.getInst().getConnection();
            stmt = conn.prepareStatement(sqlQuery);
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
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Can not close Connection/PrepareStatemet");
                System.out.println(e.getMessage());
            }
        }
        return user;
    }

    // insert data into database
    public User create(Integer userId, String name, Integer age) {
        String sqlUpdate = "INSERT INTO user(user_id, name, age) VALUES(?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        User user = null;

        try {
            conn = DbCpModel.getInst().getConnection();
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            Integer suc = stmt.executeUpdate();

            if (suc == 1) {
                user = new User(userId, name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Maybe userName/password invalid");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Can not close Connection/PrepareStatemet");
                System.out.println(e.getMessage());
            }
        }
        return user;
    }

    // update data into database
    public User update(Integer userId, String name, Integer age) {
        String sqlUpdate = "UPDATE user SET name = ?, age = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        User user = null;

        try {
            conn = DbCpModel.getInst().getConnection();
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(3, userId);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            Integer suc = stmt.executeUpdate();

            if (suc == 1) {
                user = new User(userId, name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Maybe username/password invalid");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Can not close Connection/PrepareStatemet");
                System.out.println(e.getMessage());
            }
        }
        return user;
    }

    // delete data in database
    public User delete(Integer userId) {
        String sqlUpdate = "DELETE FROM user WHERE user_id= ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        User user = null;

        try {
            conn = DbCpModel.getInst().getConnection();
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setInt(1, userId);
            Integer suc = stmt.executeUpdate();
            if (suc == 1) {
                user = new User(userId, null, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Maybe username/password invalid");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Can not close Connection/PrepareStatemet");
                System.out.println(e.getMessage());
            }
        }
        return user;
    }
}