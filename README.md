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

# Programming

## Database connection:

- **JDBC (Java Database Connectivity)**: is the API that manages connecting to a database, issuing queries and commands, and handing results set obtained from the database, helps user to interact or communication with various databse.


### DriverManager in JDBC

- DriverManger class is the component of JDBC API and also member of the java.sql package. The DriverManager class acts as an interface between users and drivers.

- **Database connection**: 
This fully implemented class connects an application to a data source, which is specified by a database URL.
    > For each request, establish a connection with the database through DriverManager in the JDBC connector. Execute the query, modify or update. Once done, close the connection.

```java
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
    }
```

### DataSource in JDBC
- **Connection Pool(!TODO)**

*A connection pool is a cache of database connections maintained so that the connections can be reused when future requests to the database required.*

```java
    // Datasource object to get connection in connection pool
    private HikariDataSource ds;

    // get a connection of connection pool
    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }

    private DbCpModel() {
        // config HikariCp connection pool
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/demo");
        config.setUsername("lvnam");
        config.setPassword("nam0801");
        // implement datasource object
        ds = new HikariDataSource(config);
    }
```
**HikariCp(Hikari connection pool)**: HikariCp is a implementation of JDBC DataSource, provides connection pools mechanism. 

## Singleton pattern

*The singleton pattern is a software design pattern the retricts instantiation of class to one "single" instance. This is useful when exactly one object is needed to coordinate actions across the system.*

### Implementations 

Implementations of singleton pattern must :
- Ensure that only one instance of singleton class ever exist.
- Provide global access to that instance.

Typically, this is done by :
- Declaring all constructor of class to be private.
- Providing a static method that return a reference to the instance.

# Project Information (!TODO)
## Tools build: Gradle 7.5.1
- gradle init: initialize project
- gradle build: build project
- gradle run (--stacktrace option): run project

## Sparkjava (!TODO)
*Sparkjava is a http server.*

## Hikari(!TODO)
*Hikari is JDBC connection pool.*

- DataSource: A DataSource object provides a new way for JDBCs clients to obtain a DBMS connection.

## Libraries (!TODO)
- HTTP Server: Sparkjava
- Lombok: Project Lombok is a java library that automatically plugs into editor and build tools, spicing up your java.
- Gson: is Java library that can be convert Java Object to into their JSON representation. It also can be used to conver a JSON string to an equivalent Java object.


## Entity: Contain data of object(Instance)
- User: Contains user information(represents user data in database).
- Http Response: 

## Model: Perform operations and logic on the respective entities
- UserModel: Performs operations on user entitys.
- DbCpModel: Contains Connection Pool, manages get connection to database.