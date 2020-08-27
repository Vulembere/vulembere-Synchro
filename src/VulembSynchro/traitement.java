/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VulembSynchro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
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

    public static String getColoneTable(String table, SqlData sqldata, Boolean addImage) {
        String colones = "";
        try {
            PreparedStatement pst = sqldata.getCon().prepareStatement("select * from " + table);
            ResultSet rs = pst.executeQuery();
            int x = 1;
            while (x <= rs.getMetaData().getColumnCount()) {
                if (!addImage) {
                    if (!rs.getMetaData().getColumnTypeName(x).equals("IMAGE") && !rs.getMetaData().getColumnTypeName(x).equals("BLOB") && !rs.getMetaData().getColumnTypeName(x).equals("LONGBLOB")) {
                        colones = colones + "," + rs.getMetaData().getColumnLabel(x);
                    } else {
                        sqldata.ColoneListeImage.add(rs.getMetaData().getColumnLabel(x));
                    }
                } else {
                    colones = colones + "," + rs.getMetaData().getColumnLabel(x);
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

    public static void decoderImageBase64(String fileName, String base64Image) {
        try (FileOutputStream imageOutFile = new FileOutputStream(fileName + ".png")) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    public static String getBase64Image(String fileName, String base64Image) {

        try (FileOutputStream imageOutFile = new FileOutputStream(fileName + ".png")) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return EncoderImageBase64(fileName + ".png");
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

    public static String EncoderImageBase64(String imagePath) {
        String base64Image = null;
        if (imagePath != null) {
            File file = new File(imagePath);
            try (FileInputStream imageInFile = new FileInputStream(file)) {
                // Reading a Image file from file system
                byte imageData[] = new byte[(int) file.length()];
                imageInFile.read(imageData);
                base64Image = Base64.getEncoder().encodeToString(imageData);
            } catch (FileNotFoundException e) {
                System.out.println("Image not found" + e);
            } catch (IOException ioe) {
                System.out.println("Exception while reading the Image " + ioe);
            }
        }
        return base64Image;
    }

    public static String convertImageBase64(String file, InputStream initialStream)
            throws IOException {

        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File(file + ".png");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        String element = EncoderImageBase64(targetFile.getAbsolutePath());
        return element;
    }
}
