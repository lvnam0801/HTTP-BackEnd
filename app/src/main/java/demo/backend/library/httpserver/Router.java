package demo.backend.library.httpserver;

import demo.backend.library.entity.HttpResponse;
import spark.Request;
import spark.Response;

/**
 * Created by Per Wendel on 2014-05-10.
 */
@FunctionalInterface
public interface Router {

    /**
     * Invoked when a request is made on this route's corresponding path e.g. '/hello'
     *
     * @param request  The request object providing information about the HTTP request
     * @param response The response object providing functionality for modifying the response
     * @return The content to be set in the response
     * @throws java.lang.Exception implementation can choose to throw exception
     */
    HttpResponse handle(Request request, Response response) throws Exception;
}