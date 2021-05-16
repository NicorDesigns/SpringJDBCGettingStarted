1. Create new JDBC3 Connection Module (Maven)
    - Use Intellij & align POM with Parent POM

2. Use MariaDbPoolDataSource pooled connection
    - Re-use DBUtils Class from JDBC2E project
    - Replace JNDI getConnection() method code with code that returns a Connection from a MariaDBPoolDataSource
        - https://mariadb.com/kb/en/pool-datasource-implementation/

3. Create App Main method to INSERT Charity
    - Main method will get the Connection created in (2) and perform an SQL INSERT of a Charity into the Charity DB
      table

    - https://www.facebook.com/ZISIZE

        - Zisize Care Centre
        - VAT REG NO: 4760176232
        - PBO NO: 830016285
        - NPO NO: 038-367 (Non Profit Organization)
        - Postal Address
            - PO Box 535, Melmoth, 3835, KwaZulu Natal, South Africa
        - Phone Number: +27833160369
        - Email: zisize@telkomsa.net


- Because of FK relationships, we will also have to
    - Create a new Category to INSERT into the Category table

- We will have to populate the charity-category relationship table
- We will have to populate the charity_program relationship table
- The services (programs) provided by Zisize are :
    - Residential facility
    - Social Work offices for psycho- social services
    - Protective workshops
    - Home based care


4. Use transactional capability with rollbacks in order to ensure that the Charity , Category, Program, and the
   relationship tables between Charity, Category and Programs will get updated in a single transaction.
    - The entire operation needs to be ACID:
        - https://en.wikipedia.org/wiki/ACID

    - Therefore, we need to enable transactional capability with rollbacks.

5. Before we run the Main Method in the TransactionMain class we need to prepare the database, this entails doing the
   following with the help of our previously created SQL SCRIPTS in the MariaDBConnect project resources:
    - Run the 2-CREATE-CHARITY-TABLE.SQL script
    - Run the 2A-CREATE-CATEGORY.SQL script
    - Run the 2AB-INSERT-CATEGORY.SQL script
    - Run part of the 2AB-CREATE-CHARITY-CATEGORY.SQL script in such a way that the Foreign Keys won't be created. This
      will allow us to test run our TransactionMain main method to insert the Charity, Category and Charity_Category
      relationship table without violating the FK constraint.
    - Left to do as a self-guided exercise is to populate the Programs table, and the Charity_Programs table within the
      Transaction code.
    - Ensure that AutoIncrement for PK is reset to zero
