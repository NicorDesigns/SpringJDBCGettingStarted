package com.nicordesigns;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

  private static File getRootFolder() {
    try {
      File root;
      String runningJarPath =
          Main.class
              .getProtectionDomain()
              .getCodeSource()
              .getLocation()
              .toURI()
              .getPath()
              .replaceAll("\\\\", "/");
      int lastIndexOf = runningJarPath.lastIndexOf("/target/");
      if (lastIndexOf < 0) {
        root = new File("");
      } else {
        root = new File(runningJarPath.substring(0, lastIndexOf));
      }
      System.out.println("application resolved root folder: " + root.getAbsolutePath());
      return root;
    } catch (URISyntaxException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void main(String[] args) throws Exception {

    File root = getRootFolder();
    System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
    Tomcat tomcat = new Tomcat();
    Path tempPath = Files.createTempDirectory("tomcat-base-dir");
    tomcat.setBaseDir(tempPath.toString());

    // The port that we should run on can be set into an environment variable
    // Look for that variable and default to 8080 if it isn't there.
    String webPort = System.getenv("PORT");
    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }

    tomcat.setPort(Integer.parseInt(webPort));

    File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
    if (!webContentFolder.exists()) {
      webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
    }
    StandardContext standardContext =
        (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
    // Set execution independent of current thread context classloader
    // (compatibility with exec:java mojo)
    standardContext.setParentClassLoader(Main.class.getClassLoader());
    System.out.println("configuring app with basedir: " + webContentFolder.getAbsolutePath());

    File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
    WebResourceRoot resources = new StandardRoot(standardContext);
    WebResourceSet resourceSet;
    if (additionWebInfClassesFolder.exists()) {
      resourceSet =
          new DirResourceSet(
              resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/");
      System.out.println(
          "loading WEB-INF resources from as '"
              + additionWebInfClassesFolder.getAbsolutePath()
              + "'");

    } else {
      resourceSet = new EmptyResourceSet(resources);
    }
    resources.addPreResources(resourceSet);
    standardContext.setResources(resources);

    //    tomcat.enableNaming();
    //
    //    ContextResource mariaDBSource = new ContextResource();
    //    mariaDBSource.setName("jdbc/charityDB");
    //    mariaDBSource.setAuth("Container");
    //    mariaDBSource.setType("javax.sql.DataSource");
    //    mariaDBSource.setProperty("driverClassName", "org.mariadb.jdbc.Driver");
    //    mariaDBSource.setProperty(
    //        "url", "jdbc:mariadb://localhost:3306/charityDB?user=root&password=secret");
    //    mariaDBSource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
    //    mariaDBSource.setProperty("user", "root");
    //    mariaDBSource.setProperty("password", "secret");
    //
    //    standardContext.getNamingResources().addResource(mariaDBSource);

    tomcat.start();
    tomcat.getServer().await();
  }
}
