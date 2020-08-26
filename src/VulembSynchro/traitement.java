/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VulembSynchro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VULEMBERE
 */
public class traitement {

    public static String getString(ArrayList<String> liste) {
        String txtString = "";
        txtString = liste.stream().map((string) -> string + ",").reduce(txtString, String::concat);
        return txtString.substring(0, txtString.length() - 1);
    }

    public static String getColoneTable(String table, SqlData sqldata) {
        String colones = "";
        try {
            PreparedStatement pst = sqldata.getCon().prepareStatement("select * from " + table);
            ResultSet rs = pst.executeQuery();
            int x = 1;
            while (x <= rs.getMetaData().getColumnCount()) {
                if (!rs.getMetaData().getColumnTypeName(x).equals("BLOB") && !rs.getMetaData().getColumnTypeName(x).equals("IMAGE") && !rs.getMetaData().getColumnTypeName(x).equals("LONGBLOB")) {
                    colones = colones + "," + rs.getMetaData().getColumnLabel(x);
                } else {
                    sqldata.ColoneListeImage.add(rs.getMetaData().getColumnLabel(x));
                }
                x++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(traitement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colones.substring(1, colones.length());
    }

    public static String getQuerry(String table, String stringColone, boolean InsetOrUpdate) {
        String[] listecolones = stringColone.split(",");

        String value = "";
        String data = "";
        if (InsetOrUpdate) {
            for (int index = 1; index < listecolones.length; index++) {
                data = data + " " + listecolones[index] + " =?, ";
            }
        } else {
            for (String listecolone : listecolones) {
                data = data + " " + listecolone + " =?, ";
            }
            for (String listecolone : listecolones) {
                value = value + "?,";
            }
        }
        if (InsetOrUpdate) {
            data = data.substring(0, (data.length() - 2));
            return "UPDATE " + table + " set " + data + " where " + listecolones[0] + " = ?";
        } else {
            value = value.substring(0, (value.length() - 1));
            return "INSERT INTO " + table + "(" + stringColone + ") values(" + value + ")";
        }
    }

    public static boolean exist(String table, String colone, String reference, SqlData sqlData) {
        try {
            String rqt = "select * from " + table + " where " + colone + " ='" + reference + "'";
            PreparedStatement pst = sqlData.getCon().prepareStatement(rqt);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(traitement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
