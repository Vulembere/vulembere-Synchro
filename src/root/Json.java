/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;

/**
 *
 * @author VULEMBERE
 */
public class Json {

    /**
     *
     * @param liste La liste des données provenant dans la table.
     * @param Colone La liste des colones de la table
     * @return Cette classe retourne un string en format json capable d'etre lue
     * par l' API dans le Cloud
     */
    public static String JsonFormat(ObservableList<ArrayList<String>> liste, ArrayList<String> Colone) {

        ObservableList<Map> MasterMap = FXCollections.observableArrayList();

        liste.stream().map((liste1) -> {
            Map<String, String> map = new HashMap();
            for (int i = 0; i < liste1.size(); i++) {
                map.put(Colone.get(i), liste1.get(i));
            }
            return map;
        }).forEachOrdered((map) -> {
            MasterMap.add(map);
        });
        JSONArray array = new JSONArray(MasterMap);
        return array.toString();
    }
}
