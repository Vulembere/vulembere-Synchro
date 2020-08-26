/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import VulembSynchro.SqlData;
import java.util.ArrayList;
import vulemb.Local;

/**
 *
 * @author VULEMBERE
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> tempo = new ArrayList();
//        while (true) {

            SqlData data = new SqlData(connexion.Con());
            data.setDatabase("c1updepense");
            data.setHost("updepense.uptodatedevelopers.com");
            data.setUser("mfvLC4Z@DrvK");
            data.setPort(3233);
            data.setPassword("uptodateupdepense");
            data.setColoneStatutSynch("synchro", false);

            data.setTable("groupe");
            String txt = data.synchronise();
            System.out.print(txt);
//            Local local = new Local(txt, data, tempo);
//         System.out.print(local.getResponse());
            data.initialise();
        }
//    }

}
