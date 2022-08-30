package demo.backend.model;
/*
 * Use HikariCp to create connection pool.
 * Use Sington Pattern to manages connections.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import demo.library.funtionalInterface.ConsumerEx;

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
    public Connection getConnection() throws SQLException {
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

    // ------------------ Methods to interact or communication with
    // database-------------------------------------
    public void executeQuery(String sqlQuery, ConsumerEx<ResultSet> cons, Object... params) throws SQLException {
        try (
                // get connection in DataSource connection pool
                Connection conn = DbCpModel.getInst().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            // pass argruments of sql statement
            if (params != null) {
                Integer idx = 1;
                for (Object param : params) {
                    stmt.setObject(idx++, param);
                }
            }
            // execute query to get data from database
            ResultSet rs = stmt.executeQuery();
            // retrieve data from resultSet
            while (rs.next()) {
                cons.accept(rs);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer executeUpdate(String sqlUpdate, Object... parmas) throws SQLException {
        try (
                // get connection in connection pool
                Connection conn = DbCpModel.getInst().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlUpdate);) {
            // set param for sql statement
            if (parmas != null) {
                Integer idx = 1;
                for (Object param : parmas) {
                    stmt.setObject(idx++, param);
                }
            }
            // execute update statement in database
            Integer suc = stmt.executeUpdate();
            return suc;
        }
    }

}
