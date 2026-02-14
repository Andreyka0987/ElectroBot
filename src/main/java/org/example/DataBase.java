package org.example;

import java.sql.*;

public class DataBase {
    private static final String dataBaseURL = "jdbc:mysql://localhost:3306/electrostatus";
    private static final String dataBaseAdminName = "root";
    private static final String dataBasePassword = "admin";



    public static int getStatus(){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(dataBaseURL,dataBaseAdminName,dataBasePassword);
            statement = connection.createStatement();
            String textStatement = "select * from electricitystatus";
            ResultSet resultLoop = statement.executeQuery(textStatement);

            int tempStatus = 0;
            while (resultLoop.next()){
                tempStatus = resultLoop.getInt("ElStatus");
            }
            return tempStatus;
        } catch (SQLException e) {
            System.err.println("Database Exception");
        }

       return 0;
    }



}
