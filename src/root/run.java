package root;

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
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class run {

    private static final String inputFilePath = "E:\\DATA Cloud\\PERSONNELLE\\VulembereSynchro\\src\\root\\logo.png";
    private static final String outputFilePath = "E:\\DATA Cloud\\PERSONNELLE\\VulembereSynchro\\src\\root\\logo2.png";

    public static void main(String[] args) {
//        try {
//            try {
//                PreparedStatement pst = connexion.Con().prepareStatement("select * from groupe where synchro=0");
//                ResultSet rs = pst.executeQuery();
//                while(rs.next()){
//               String im =      encoder(convertImageTofile(rs.getString("code"), rs.getBinaryStream("logo")));
//               System.out.println(im);
//                }
//            } catch (SQLException e) {
//            }
//           
//        } catch (IOException ex) {
//            Logger.getLogger(run.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
