package com.nicordesigns;

import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    XmlWebApplicationContext context = new XmlWebApplicationContext();
    context.setConfigLocation("/WEB-INF/applicationContext.xml");
    context.setServletContext(req.getServletContext());
    context.refresh();

    DataSource ds = (DataSource) context.getBean("dataSource");

    DatabaseUtil databaseUtil = new DatabaseUtil(ds);
    String catalogName;
    catalogName = databaseUtil.getCatalogName();
    ServletOutputStream out = resp.getOutputStream();
    out.write("servlet says hello - ".getBytes());
    out.write(catalogName.getBytes());
    out.write("/n".getBytes());
    out.flush();
    out.close();
  }
}
