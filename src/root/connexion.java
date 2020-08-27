/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.sql.*;

/**
 *
 * @author wills
 */
public class connexion {

    public static Connection con;
    public static PreparedStatement pst;
    public static ResultSet rs;

    public static Connection Con() {
        if (con == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:unnDB.db");
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Erreur " + ex);
                con = null;
            }
        }
        return con;
    }
    
}
