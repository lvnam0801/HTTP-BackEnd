package demo.backend.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import demo.backend.entity.User;
import demo.backend.library.entity.HttpResponse;
import demo.backend.library.model.DbCpModel;

public class UserModel {

    // ----------------Singleton Design Pattern---------------------
    private static UserModel INST = null;

    // private constructor
    private UserModel() {
    }

    // Lazy Load technique
    // create get instance for one instace of class (parallel request processing)
    public static UserModel getInst() {
        if (INST != null) {
            return INST;
        } else {
            // parallel initialize processing
            synchronized (UserModel.class) {
                if (INST == null) {
                    INST = new UserModel();
                }
                return INST;
            }
        }
    }

    // read data from database with key=userId
    public HttpResponse read(Integer userId) throws SQLException {

        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
        HttpResponse res = new HttpResponse();
        Connection conn = DbCpModel.getInst().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);

        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        // retrieve data in rs ResultSet
        if (rs.next()) {
            // get data in resultset rs after query data from database
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));

            // set response data if request is successfull
            res.code = 0;
            res.data = user;
            res.message = "Success";
        } else {
            // set response of data is not found in database
            res.code = 1;
            res.data = null;
            res.message = "User not found";
        }
        return res;
    }

    // insert data into database
    public HttpResponse create(Integer userId, String name, Integer age) throws SQLException {

        String sqlUpdate = "INSERT IGNORE INTO user(user_id, name, age) VALUES(?, ?, ?)";
        HttpResponse res = new HttpResponse();

        Connection conn = DbCpModel.getInst().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlUpdate);

        stmt.setInt(1, userId);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        Integer suc = stmt.executeUpdate();

        // assing result to response object
        if (suc == 1) {
            User user = new User(userId, name, age);
            // set Response result for successful
            res.code = 0;
            res.data = user;
            res.message = "Create user successfully";

        } else {
            res.code = 1;
            res.data = null;
            res.message = "Create user failed";
        }
        return res;
    }

    // update data into database
    public HttpResponse update(Integer userId, String name, Integer age) throws SQLException {
        
        String sqlUpdate = "UPDATE user SET name = ?, age = ? WHERE user_id = ?";
        HttpResponse res = new HttpResponse();

        Connection conn = DbCpModel.getInst().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlUpdate);

        stmt.setInt(3, userId);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        Integer suc = stmt.executeUpdate();

        if (suc == 1) {
            User user = new User(userId, name, age);
            // if request is performed successfully
            res.code = 0;
            res.data = user;
            res.message = "Update user successfully";
        } else {
            res.code = 1;
            res.data = null;
            res.message = "Update user failed";
        }
        return res;
    }

    // delete data in database
    public HttpResponse delete(Integer userId) throws SQLException {
        String sqlUpdate = "DELETE FROM user WHERE user_id= ?";
        HttpResponse res = new HttpResponse();

        Connection conn = DbCpModel.getInst().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlUpdate);

        stmt.setInt(1, userId);
        Integer suc = stmt.executeUpdate();

        if (suc == 1) {
            User user = new User(userId, null, null);
            // If delete user successfully
            res.code = 0;
            res.data = user;
            res.message = "Delete user successfully";
        } else {
            res.code = 1;
            res.data = null;
            res.message = "Delete user failed";
        }
        return res;
    }
}