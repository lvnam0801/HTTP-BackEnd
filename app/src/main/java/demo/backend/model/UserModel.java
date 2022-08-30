package demo.backend.model;

import java.sql.SQLException;

import demo.backend.entity.User;
import demo.library.entity.HttpResponse;

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

        HttpResponse res = new HttpResponse(0, null, "User not found");
        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
        
        // Execute query with implement fuctional interface ConsumerEx 
        DbCpModel.getInst().executeQuery(sqlQuery, 
        (rs) -> {
              // get data in resultset rs after query data from database
              User user = new User();
              user.setUserId(rs.getInt("user_id"));
              user.setName(rs.getString("name"));
              user.setAge(rs.getInt("age"));
  
              // set response data if request is successfull
              res.code = 0;
              res.data = user;
              res.message = "Success";
        }, userId);
   
        return res;
    }

    // insert data into database
    public HttpResponse create(Integer userId, String name, Integer age) throws SQLException {

        HttpResponse res = new HttpResponse(1, null, "Create user failed");
        String sqlUpdate = "INSERT IGNORE INTO user(user_id, name, age) VALUES(?, ?, ?)";

        Integer suc = DbCpModel.getInst().executeUpdate(sqlUpdate, userId, name, age);
        // assing result to response object
        if (suc == 1) {
            User user = new User(userId, name, age);
            // set Response result for successful
            res.code = 0;
            res.data = user;
            res.message = "Create user successfully";

        } 
        return res;
    }

    // update data into database
    public HttpResponse update(Integer userId, String name, Integer age) throws SQLException {
        
        HttpResponse res = new HttpResponse(1, null, "Update user failed");
        String sqlUpdate = "UPDATE user SET name = ?, age = ? WHERE user_id = ?";

        Integer suc = DbCpModel.getInst().executeUpdate(sqlUpdate, name, age, userId);
        if (suc == 1) {
            User user = new User(userId, name, age);
            // if request is performed successfully
            res.code = 0;
            res.data = user;
            res.message = "Update user successfully";
        } 
        return res;
    }

    // delete data in database
    public HttpResponse delete(Integer userId) throws SQLException {
        
        HttpResponse res = new HttpResponse(1, null, "Delete user failed");
        String sqlUpdate = "DELETE FROM user WHERE user_id= ?";
        
        Integer suc = DbCpModel.getInst().executeUpdate(sqlUpdate, userId);
        if (suc == 1) {
            User user = new User(userId, null, null);
            // If delete user successfully
            res.code = 0;
            res.data = user;
            res.message = "Delete user successfully";
        }
        return res;
    }
}