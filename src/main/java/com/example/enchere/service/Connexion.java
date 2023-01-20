package com.example.enchere.service;

import java.sql.*;

public class Connexion {
    public static Connection con = null;

    public Connexion() {
    }

    public static Connection getConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://containers-us-west-171.railway.app:7724/railway", "postgres",
                    "pnFJ0Buhv8sxcZxEp4d0");
        } catch (Exception e) {
            throw e;
        }
    }
}