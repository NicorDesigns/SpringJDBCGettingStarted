package com.nicordesigns;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseUtil {

  private final DataSource dataSource;

  public DatabaseUtil(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private Connection createConnection() throws SQLException {
    return dataSource.getConnection();
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
