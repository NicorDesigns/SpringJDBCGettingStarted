package com.nicordesigns;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class DriverManagerApp {

  public static void main(String[] args) {
    try {

      ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

      DataSource dataSource = context.getBean(DataSource.class);

      System.out.println("buildDataSource: " + dataSource);

      String catalogName = getCatalogName(dataSource);
      System.out.println("Catalog name: " + catalogName);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static String getCatalogName(DataSource dataSource) {
    String catalogName = null;
    try (Connection conn = dataSource.getConnection()) {
      catalogName = conn.getCatalog();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return catalogName;
  }
}
