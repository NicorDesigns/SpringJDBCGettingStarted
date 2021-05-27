package com.nicordesigns;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseUtil {

  private final String userName;
  private final String password;
  private final String dbms;
  private final String serverName;
  private final String portNumber;
  private final String dbName;

  public DatabaseUtil(
      String userName,
      String password,
      String dbms,
      String serverName,
      String portNumber,
      String dbName) {
    this.userName = userName;
    this.password = password;
    this.dbms = dbms;
    this.serverName = serverName;
    this.portNumber = portNumber;
    this.dbName = dbName;
  }

  private Connection createConnection() throws SQLException {
    MariaDbPoolDataSource mariaDbPoolDataSource = new MariaDbPoolDataSource();
    Connection conn;
    mariaDbPoolDataSource.setServerName(this.serverName);
    mariaDbPoolDataSource.setPortNumber(Integer.parseInt("3306"));
    mariaDbPoolDataSource.setUser(this.userName);
    mariaDbPoolDataSource.setPassword(this.password);
    mariaDbPoolDataSource.setDatabaseName(this.dbms);
    mariaDbPoolDataSource.setMaxPoolSize(10);
    mariaDbPoolDataSource.setPoolName("JDBC3");
    mariaDbPoolDataSource.setUrl("jdbc:mariadb://localhost:3306/charityDB");
    conn = mariaDbPoolDataSource.getConnection();

    return conn;
  }

  public String getCatalogName() {

    String catalogName;
    try {
      Connection conn = createConnection();
      catalogName = Objects.requireNonNull(conn).getCatalog();
      conn.close();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return catalogName;
  }
}
