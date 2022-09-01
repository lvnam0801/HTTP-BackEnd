package demo.backend.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import demo.backend.entity.User;
import demo.library.entity.HttpResponse;

public class UserModel {

    // ----------------Singleton Design Pattern---------------------
    private static UserModel INST = null;
    // create cahe for User
    private Map<Integer, HttpResponse> userCache = new HashMap<Integer, HttpResponse>();

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
        System.out.println(userCache);

        HttpResponse res = new HttpResponse(0, null, "User not found");
        // find the user data in userCache first, if not found, then perform a query in
        // the database.
        if (userCache.containsKey(userId)) {
            return userCache.get(userId);
        }

        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
        // Execute query with implement fuctional interface ConsumerEx
        DbCpModel.getInst().executeQuery(sqlQuery,
                (rs) -> {
                    // get data in resultset rs after query data from database
                    User user = new User(rs);
                    // set Response result for successful
                    res.setCode(0)
                            .setData(user)
                            .setMessage("Success");
                    // if data user is founded, add to userCache
                    userCache.put(userId, res);
                }, userId);
        return res;
    }

    // insert data into database
    public HttpResponse create(Integer userId, String name, Integer age) throws SQLException {

        HttpResponse res = new HttpResponse(1, null, "User already exist");
        // find data user in userCahe
        if (userCache.containsKey(userId)) {
            return res;
        }

        String sqlUpdate = "INSERT IGNORE INTO user(user_id, name, age) VALUES(?, ?, ?)";
        Integer suc = DbCpModel.getInst().executeUpdate(sqlUpdate, userId, name, age);
        // assing result to response object
        if (suc == 1) {
            User user = new User(userId, name, age);
            // set Response result for successful
            res.setCode(0)
                    .setData(user)
                    .setMessage("Success");
            // add new the user data to userCache
            userCache.put(userId, res);
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
            // set Response result for successful
            res.setCode(0)
                    .setData(user)
                    .setMessage("Success");

            // check if user data already exist in cache then update, if not put
            userCache.put(userId, res);
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
            // set Response result for successful
            res.setCode(0)
                    .setData(user)
                    .setMessage("Success");

            // Delete data user in userCache
            if (userCache.containsKey(userId)) {
                userCache.remove(userId);
            }
        }
        return res;
    }
}