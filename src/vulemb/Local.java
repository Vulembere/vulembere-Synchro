/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vulemb;

import VulembSynchro.SqlData;
import VulembSynchro.traitement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import root.NewMain;

/**
 *
 * @author VULEMBERE
 */
public final class Local {

    public boolean statut;
    public String response;
    public String error;

    public Local(String Json, SqlData sqlData, ArrayList<String> tempo) {
        String ColumnListe = traitement.getString(sqlData.getColoneListe());
        String reference = sqlData.getColoneListe().get(0);
        JSONArray array = null;
        try {
            array = new JSONArray(Json);
        } catch (JSONException e) {
            System.err.println("Vous devez vous rassurer que la table " + sqlData.getTable() + " existe dans le cloud ainsi que tout les champs qu'elle contienne.(" + Arrays.toString(sqlData.getColoneListe().toArray()) + ""
                    + "\n en outre les coordonnées de connection à votre base des donneés dans la cloud soient correct !.)");
        }
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject ob = array.getJSONObject(i);
                if (traitement.exist(sqlData.getTable(), reference, ob.getString(reference), sqlData)) {
                    String rqt = traitement.getQuerry(sqlData.getTable(), ColumnListe, true);
                    try {
                        if (!tempo.contains(ob.toString())) {
                            PreparedStatement pst = sqlData.getCon().prepareStatement(rqt);
                            for (int j = 1; j < sqlData.getColoneListe().size(); j++) {
                                String object = sqlData.getColoneListe().get(j);
                                if (object.equalsIgnoreCase(sqlData.coloneSyncho)) {
                                    pst.setInt(j, 1);
                                } else {
                                    pst.setObject(j, ob.get(object));
                                }
                            }
                            pst.setString(sqlData.getColoneListe().size(), ob.getString(sqlData.getColoneListe().get(0)));
                            pst.execute();
                            tempo.add(ob.toString());
                            this.setStatut(true);
                            this.setResponse("UPDATE SUCCED => TABLE : " + sqlData.getTable());
                            this.setError(null);
                            System.out.println("===================    UPDATE SUCCED => TABLE : " + sqlData.getTable());
                        }

                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                        this.setStatut(false);
                        this.setResponse("ERROR_UPDATE => TABLE : " + sqlData.getTable());
                        this.setError(ex.getMessage());
                    }
                } else {
                    String rqt = traitement.getQuerry(sqlData.getTable(), ColumnListe, false);
                    try {
                        PreparedStatement pst = sqlData.getCon().prepareStatement(rqt);
                        for (int j = 0; j < sqlData.getColoneListe().size(); j++) {
                            String object = sqlData.getColoneListe().get(j);
                            if (object.equalsIgnoreCase(sqlData.coloneSyncho)) {
                                pst.setInt(j + 1, 1);
                            } else {
                                pst.setObject(j, ob.get(object));
                            }
                        }
                        pst.execute();
                        this.setStatut(true);
                        this.setResponse("INSERT SUCCED => TABLE : " + sqlData.getTable());
                        this.setError(null);
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                        this.setStatut(false);
                        this.setResponse("ERROR_INSERT => TABLE : " + sqlData.getTable());
                        this.setError(ex.getMessage());
                    }
                }
            }
        } else {
            System.out.println("La liste est vide !.");
        }

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
