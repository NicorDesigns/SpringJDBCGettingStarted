package com.nicordesigns;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DriverManagerApp {

  public static void main(String[] args) {

    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    DataSource dataSource = context.getBean(DataSource.class);

    System.out.println(dataSource);

    String catalogName = getCatalogName(dataSource);

    System.out.println("Catalog Name: " + catalogName);
  }

  private static String getCatalogName(DataSource dataSource) {
    String catalogName = null;
    try (Connection connection = dataSource.getConnection()) {
      catalogName = connection.getCatalog();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return catalogName;
  }
}
