package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Map map = new Map("MAP","localhost",3306,"root", "Steps.123");

        map.deleteCountry(2);
        map.deleteCountry(3);
        map.showCountries();
    }
}
