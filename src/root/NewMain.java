/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import VulembSynchro.SqlData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import vulemb.Local;

/**
 *
 * @author VULEMBERE
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {

        ArrayList<String> tempo = new ArrayList();
//        while (true) {

        SqlData data = new SqlData(connexion.Con());
//            data.setDatabase("test");
//            data.setHost("localhost");
//            data.setUser("root");
//            data.setPort(3306);
//            data.setPassword(""); 
        data.setDatabase("c1updepense");
        data.setHost("localhost");
        data.setUser("c1updepense");
        data.setPort(3306);
        data.setPassword("mfvLC4Z@DrvK");
        data.setColoneStatutSynch("synchro", false);

        data.setTable("groupe");
        String txt = data.synchronise(true);
        System.out.print(txt);
        Local local = new Local(txt, data, tempo);
        System.out.print(local.getResponse());
        data.initialise();
    }
//    }
//        try (InputStream stream = Files.newInputStream(Paths.get("txte.txt"))) {
//
//            // convert stream to file
//            Files.copy(stream, Paths.get("txte1.txt"), StandardCopyOption.REPLACE_EXISTING);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        upload("http://localhost/standardApi/index.php", null);
}

//    public static void upload(String url, File file) throws IOException, URISyntaxException {
//        DefaultHttpClient client = new DefaultHttpClient(); //The client object which will do the upload
//        HttpPost httpPost = new HttpPost(url); //The POST request to send
//
//        FileBody fileB = new FileBody(file);
//        MultipartEntity request = new MultipartEntity(); //The HTTP entity which will holds the different body parts, here the file
//        request.addPart("file", fileB);
//        request.addPart("data", new StringBody(""));
//
//        httpPost.setEntity(request);
//        CloseableHttpResponse response = client.execute(httpPost); //Once the upload is complete (successful or not), the client will return a response given by the server
//        if (response.getStatusLine().getStatusCode() == 200) {
//            System.out.println("### Succed " + response.getStatusLine().getStatusCode());
//
//        }
//    }

