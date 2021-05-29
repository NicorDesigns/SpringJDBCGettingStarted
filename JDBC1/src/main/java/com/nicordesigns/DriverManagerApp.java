package com.nicordesigns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/***
 * DriverManagerApp
 */
public class DriverManagerApp {

  private String userName;
  private String password;
  private String dbms;
  private String serverName;
  private String portNumber;
  private String dbName;

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setDbms(String dbms) {
    this.dbms = dbms;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public void setPortNumber(String portNumber) {
    this.portNumber = portNumber;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public Connection getConnection() {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);

    if (this.dbms.equals("mariadb")) {
      try {
        conn =
            DriverManager.getConnection(
                "jdbc:"
                    + this.dbms
                    + "://"
                    + this.serverName
                    + ":"
                    + this.portNumber
                    + "/"
                    + this.dbName,
                connectionProps);
      } catch (SQLException throwable) {
        throwable.printStackTrace();
      }
    }
    System.out.println("Connected to database");
    return conn;
  }
}
