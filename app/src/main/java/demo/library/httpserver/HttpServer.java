package demo.library.httpserver;

import demo.library.entity.HttpResponse;
import spark.Request;
import spark.Response;
import spark.Spark;

/*
 * Wrap http methods of SparkJava
 */
public class HttpServer {

    private static HttpResponse process(Router router, Request req, Response res) {
        try {
            // run method in functional interface after that is implementated
            return router.handle(req, res);
        } catch (Exception e) {
            HttpResponse errRes = new HttpResponse(-1, null, e.getMessage());
            // print message of Exception to screen for debug
            e.printStackTrace();
            System.out.println("An error occurred. Maybe userName/password invalid");
            System.out.println(e.getMessage());
            return errRes;
        }
    }

    public static void get(final String path, final Router router) {
        Spark.get(path, (req, res) -> process(router, req, res));
    }

    public static void put(final String path, final Router router) {
        Spark.put(path, (req, res) -> process(router, req, res));
    }

    public static void post(final String path, final Router router) {
        Spark.post(path, (req, res) -> process(router, req, res));
    }

    public static void delete(final String path, final Router router) {
        Spark.delete(path, (req, res) -> process(router, req, res));
    }

}
