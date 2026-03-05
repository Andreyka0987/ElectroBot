package com.example.httpserverbasic.api;

import java.sql.*;

public class DataBaseModule {
    private static final String DATABASEURL = "jdbc:mysql://localhost:3306/electrostatus";
    private static final String ADMINNAME = "root";
    private static final String DATABASEPASSWORD = "admin";

    public static void SentData(int value){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(DATABASEURL,ADMINNAME,DATABASEPASSWORD);
            statement = connection.createStatement();
            String tempStatement = "update electricitystatus set ElStatus = "+value+";";
            statement.execute(tempStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
