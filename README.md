# HTTP Server

- **Plain text(text-base) protocol** : is a communication protocol whose content representation is string(human-readable format)

- **Stateless protocol** :(no save information) is a type protocol in which their clients send a server request after which the server responds based on the current state. A stateless protocol doesn't require a server to retain the infomation of a session or the status of every communicating partner in mutiple requests.

- **Header(Meta data)** : Contains descriptive information about request or response(additional information) between client and server

- **Body(data)** : is the data bytes transmitted in an HTTP transaction message immediately following the headers if there are any.

- **Request** : resource URL `(protocol://ip:port/path/{pathParm}?{queryParm})`
    - QueryParam: is not path of URL and passed in key=value format those parameters must be defined by API developer. Ex : `http://localhost:4567/read/userId=1234`
    - PathParam: path parameters are variable parts of URL path. They are typically used to point to specific resource within a collection. Ex: `http://localhost:4567/read/1234`

- **9 Method of HTTP** : 
    - `GET, HEAD(no body)` : SAFE MODE(No action on server).
    - `POST, PUT, PATCH` : Message with body(send data to server).
    - `OPTIONS, TRACE`
    - `DELETE` : Delete a resource (Not guaranteed)

- **Return value** :
    - 200: success
    - 404: client error
    - 500: server error

# Project Information (!TODO)
## Tools build: Gradle 7.5.1
- gradle init: initialize project
- gradle build: build project
- gradle run (--stacktrace option): run project

## Libraries (!TODO)
- HTTP Server: Sparkjava
- Lombok: Project Lombok is a java library that automatically plugs into editor and build tools, spicing up your java.


## Entity: Contain data of object(Instance)
- User: Contains user information(represents user data in database).

## Model: Perform operations and logic on the respective entities
- UserModel: Performs operations on user entitys.

## Connect database with DriverManager in JDBC
- **JDBC (Java Database Connectivity)**: is the API that manages connecting to a database, issuing queries and commands, and handing results set obtained from the database.
- **DriverManager**: This fully implemented class connects an application to a data source, which is specified by a database URL.
    > For each request, establish a connection with database through DriverManager in JDBC connector. Execute query, modify or update. Once done, close the connection.
    ```
    String databaseURL = "jdbc:mysql://localhost:3306/demo";
    String userName = "username";
    String password = "password";
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
    }```