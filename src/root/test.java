/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author VULEMBERE
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
            PreparedStatement pst = connexion.Con().prepareStatement("select * from groupe where logo is not null ");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                convertImageTofile(rs.getString("nomGroupe"), rs.getBinaryStream("logo"));
            }
            
        } catch (SQLException e) {
        }
    }

    public static String convertImageTofile(String file, InputStream initialStream)
            throws IOException {
        
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);
        
        File targetFile = new File(file + ".png");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        
        return targetFile.getAbsolutePath();
    }    
}
