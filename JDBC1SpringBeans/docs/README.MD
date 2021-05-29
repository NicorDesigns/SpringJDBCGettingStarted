1. Create JDBC Spring Beans Connection Module (maven)
   - IntelliJ Maven project (no archetype)
   - Update POM.XML with Spring Dependencies (property versions)
   - Update build order in POM.XML (After JDBC1, before JDBC2)

2. Define DataSource Class and its Properties in beans.xml
   - Specify the database properties in bean.xml


3. Retrieve DataSource through Spring Classpath Context
   - Using the DriverManager Bean Class


4. Use DataSource Object to get a Connection in Main method.
   - Get the CatalogName from the connection (as in JDBC1)

