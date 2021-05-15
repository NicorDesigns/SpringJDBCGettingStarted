package com.nicordesigns;

import java.sql.*;
import java.util.Objects;

public class TransactionMain {

  public static void main(String[] args) {
    TransactionMain transactionMain = new TransactionMain();
    transactionMain.insertCharity(
        "4760176232",
        "Zisize Care Centre ",
        "Zisize is a care centre for the disabled situated at Dingaanstat mission at Makhosini Valley\n"
            + "    // near Ulundi.",
        "https://www.facebook.com/ZISIZE/?ref=page_internal",
        "https://www.facebook.com/ZISIZE/?ref=page_internal",
        "N/A");
  }

  // https://www.facebook.com/ZISIZE/?ref=page_internal

  // Zisize Care Centre
  // VAT REG NO 4760176232
  // PBO NO 830016285
  // NPO NO 038-367 (Non Profit Organization)
  // Postal Address PO Box 535, Melmoth, 3835, KwaZulu Natal, South Africa
  // Phone Number +27833160369
  // Email zisize@telkomsa.net

  // Zisize is a care centre for the disabled situated at Dingaanstat mission at Makhosini Valley
  // near Ulundi.

  // The centre was started by the church – Dutch Reformed Church and the Uniting Reformed Church
  // in 1988. The church is still responsible for this project and provide the necessary support.
  // The Centre caters for any disabled people from KZN despite of any religion, though it is a
  // faith-based organization.
  // The need then expanded which led to admitting other disabled people from the communities. The
  // institution caters for both physically and mentally challenged people from the age of 18
  // years as long as they are not dangerous to other people and to themselves. Zisize is a
  // registered NPO since 2005.

  // The services rendered by Zisize are :
  // • Residential facility
  // • Social Work offices for psycho- social services
  // • Protective workshops
  // • Home based care
  // . The institution is funded by the Department of Social Development and an overseas funder
  // Canadian Reformed World Relief for the 2 home based care programs.

  // Zisize Care Centre is managed by and is a ministry of the Uniting Reformed Church of South
  // Africa and it's diaconal conference in the area called USIZO.

  public void insertCharity(
      String charityTaxID,
      String charityName,
      String charityMission,
      String charityWebAddress,
      String charityFacebookAddress,
      String charityTwitterAddress) {

    Connection conn = null;

    PreparedStatement pstmtInsertCharity = null;
    PreparedStatement pstmtInsertCategory = null;
    PreparedStatement pstmtInsertCharityCategory = null;

    ResultSet charitySet = null;
    ResultSet categorySet = null;
    ResultSet charityCategoryResultSet = null;

    int charityIDInt = 0;
    int categoryID = 0;

    try {

      DatabaseUtil databaseUtil =
          new DatabaseUtil("root", "secret", "mariadb", "localhost", "3306", "charityDB");

      conn = databaseUtil.createConnection();

      conn.setAutoCommit(false);

      String sqlInsertCharity =
          "INSERT INTO charity(CHARITY_TAX_ID, CHARITY_NAME, CHARITY_MISSION, CHARITY_WEB_ADDRESS, CHARITY_FACEBOOK_ADDRESS, CHARITY_TWITTER_ADDRESS) "
              + "VALUES(?,?,?,?,?,?)";

      pstmtInsertCharity = conn.prepareStatement(sqlInsertCharity, Statement.RETURN_GENERATED_KEYS);

      pstmtInsertCharity.setString(1, charityTaxID);
      pstmtInsertCharity.setString(2, charityName);
      pstmtInsertCharity.setString(3, charityMission);
      pstmtInsertCharity.setString(4, charityWebAddress);
      pstmtInsertCharity.setString(5, charityFacebookAddress);
      pstmtInsertCharity.setString(6, charityTwitterAddress);

      int rowsAffected = pstmtInsertCharity.executeUpdate();

      charitySet = pstmtInsertCharity.getGeneratedKeys();

      if (charitySet.next()) {
        charityIDInt = charitySet.getInt(1);
      }

      // When charity table insert succeeds, insert a new Category in the category table and
      // add/insert the
      // association between the Charity and the Category in the Charity_Category Table

      if (rowsAffected == 1) {
        String insertCategory = "INSERT INTO CATEGORY(CATEGORY_NAME) VALUES(?)";
        pstmtInsertCategory =
            conn.prepareStatement(insertCategory, Statement.RETURN_GENERATED_KEYS);
        pstmtInsertCategory.setString(1, "DISABILITIES");
        int categoryRowAffected = pstmtInsertCategory.executeUpdate();

        categorySet = pstmtInsertCategory.getGeneratedKeys();

        if (categorySet.next()) {
          categoryID = categorySet.getInt(1);
        }

        if (categoryRowAffected == 1) {
          String insertCharityCategory =
              "INSERT INTO CHARITY_CATEGORY(CHARITY_ID, CATEGORY_ID) VALUES(?,?)";

          pstmtInsertCharityCategory =
              conn.prepareStatement(insertCharityCategory, Statement.RETURN_GENERATED_KEYS);
          pstmtInsertCharityCategory.setInt(1, charityIDInt);
          pstmtInsertCharityCategory.setInt(2, categoryID);

          int rowsCharityCategoryAffected = pstmtInsertCharityCategory.executeUpdate();

          charityCategoryResultSet = pstmtInsertCharityCategory.getGeneratedKeys();

          if (rowsCharityCategoryAffected == 1) {
            System.out.println("CharityCategory Rows Affected: " + rowsCharityCategoryAffected);
            // TODO Insert or Update new Charity Programs and Insert the Association between the
            // Charity and Programs in the charity_program table
            if (charityCategoryResultSet.next()) {
              int charityCategoryID = charityCategoryResultSet.getInt(0);
            }
          }
        }

        conn.commit();

      } else {
        conn.rollback();
      }

    } catch (SQLException throwable) {
      throwable.printStackTrace();

      try {
        Objects.requireNonNull(conn).rollback();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    } finally {
      try {
        if (charitySet != null) {

          charitySet.close();
        }
        if (categorySet != null) {
          categorySet.close();
        }

        if (pstmtInsertCharity != null) {
          pstmtInsertCharity.close();
        }

        if (pstmtInsertCategory != null) {
          pstmtInsertCategory.close();
        }

        if (pstmtInsertCharityCategory != null) {
          pstmtInsertCharityCategory.close();
        }

      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }
}
