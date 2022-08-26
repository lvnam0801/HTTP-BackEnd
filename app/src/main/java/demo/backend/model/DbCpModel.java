package demo.backend.model;
/*
 * Use HikariCp to create connection pool.
 * Use Sington Pattern to manages connections.
 */

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DbCpModel {
    // unique instance of singleton class
    private static DbCpModel INST;
    // Datasource object to get connection in connection pool
    private HikariDataSource ds;

    // public get instace:
    public static DbCpModel getInst() {
        if (INST != null) {
            return INST;
        } else {
            // avoid INST is created by multi-thread -> override INST
            synchronized (DbCpModel.class) {
                if (INST == null) {
                    INST = new DbCpModel();
                }
                return INST;
            }
        }
    }

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

}
