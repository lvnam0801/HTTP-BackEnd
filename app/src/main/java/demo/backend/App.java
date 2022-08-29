/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package demo.backend;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import demo.backend.model.UserModel;

public class App {
    public String getGreeting() {
        return "Starting...";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        /**
         * Http methods: 
         * GET, POST, PUT, DELETE: read, create, update, delete (CURL)
         */
        get("/read", (req, res) -> {
            Integer userId = Integer.parseInt(req.queryParams("userId"));
            return UserModel.getInst().read(userId);
        });

        post("/create", (req, res) -> {
            Integer userId = Integer.parseInt(req.queryParams("userId"));
            String name = req.queryParams("name");
            Integer age = Integer.parseInt(req.queryParams("age"));
            return UserModel.getInst().create(userId, name, age);
        });

        put("/update", (req, res) -> {
            Integer userId = Integer.parseInt(req.queryParams("userId"));
            String name = req.queryParams("name");
            Integer age = Integer.parseInt(req.queryParams("age"));
            return UserModel.getInst().update(userId, name, age);
        });
        
        delete("/delete", (req, res) -> {
            Integer userId = Integer.parseInt(req.queryParams("userId"));
            return UserModel.getInst().delete(userId); 
        });
    }
}
