1. Create new JDBC2ESpringBeanConfig Module (Maven)
   - Use Intellij
   - Align POM with Parent POM
   - Add Spring Dependencies (Same as in JDBC1SpringBean POM.XML)
   - Add Embedded Tomcat Dependencies (Same as in JDBC2E POM.XML)

2. Create a new package com.nicordesigns

3. Add new datasource spring bean configuration
   - Add webapp/WEB-INF/applicationContext.xml with spring bean data source definition
   - Add web.xml with a reference to the datasource defined in applicationContext.xml

4. Update the code with a Main method that accesses the MaraiaDB using the Spring Bean
   - Add com.nicordesigns.DatabaseUtil (Same as in JDBC2E)
   - Add com.nicordesigns.HelloServlet (Same as in JDBC2E)
      - The Servlet will contain the logic to read in the datasource Spring Bean from applicationContext.xml
   - Add com.nicordesigns.Main and update the Main Method code to get the MariaDBDatasource
