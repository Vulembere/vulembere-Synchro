/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VulembSynchro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import root.Json;

/**
 *
 * @author VULEMBERE
 */
public class SqlData {

    public ResultSet rs;
    public PreparedStatement pst;
    public Connection con;

    public ArrayList<String> ColoneListe = new ArrayList();
    public ArrayList<String> ColoneListeImage = new ArrayList();
    public static ArrayList<String> tempo = new ArrayList();
    public static Map<String, Blob> ColoneImage = new HashMap();

    public ObservableList<ArrayList<String>> getAllData(Boolean addImage) {
        ObservableList<ArrayList<String>> detailles = FXCollections.observableArrayList();

        String col = traitement.getColoneTable(table, this, addImage);
        if (con != null) {
            try {
                String rqt = "select " + col + " from " + this.table + " " + this.coloneStatut;

                this.pst = this.con.prepareStatement(rqt);
                this.rs = pst.executeQuery();
                int x = 1;
                while (x <= rs.getMetaData().getColumnCount()) {
                    this.ColoneListe.add(rs.getMetaData().getColumnName(x));
                    x++;
                }
                while (rs.next()) {
                    ArrayList<String> det_ = new ArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        det_.add(rs.getString(i));
                    }
                    detailles.add(det_);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SqlData.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Erreur de connexion");
        }

        return detailles;
    }

    public SqlData(Connection con) {
        this.con = con;
    }

    public ArrayList<String> getColoneListe() {
        return ColoneListe;
    }

    public void setColoneListe(ArrayList<String> ColoneListe) {
        this.ColoneListe = ColoneListe;
    }
    public String coloneStatut;
    public String coloneStatutDelete;
    public String table;
    public String coloneSyncho;
    public String colone_reference;
    public String host;
    public String password;
    public String user;
    public int port;
    public String databanse;

    /**
     * url Uril de base
     */
//    public String url = "http://localhost/standardApi/index.php";
    public String url = "https://finup.uptodatedevelopers.com/api/finup/index.php";

    public String POST(String DATA_, String COLONES) {
//        System.err.println(DATA_);
        try {
            URL urlMaster = new URL(this.url);
            URLConnection connecion = urlMaster.openConnection();
            connecion.setDoOutput(true);
            StringBuilder content;
            try (PrintStream ps = new PrintStream(connecion.getOutputStream())) {
                ps.print("CONFIG=" + this.host + "," + this.databanse + "," + this.user + "," + this.password + "," + this.port + "," + this.colone_reference);
                ps.print("&DATA_=" + DATA_);
                ps.print("&COLONES=" + COLONES);
                ps.print("&TABLE=" + this.table);
                connecion.getInputStream();
                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(new InputStreamReader(connecion.getInputStream(), "UTF8"));
                // To receive the response
                content = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                bufferedReader.close();
            }
            return content.toString();
        } catch (MalformedURLException ex) {
            System.err.println(Arrays.toString(ex.getStackTrace()));
        } catch (IOException ex) {
            System.err.println(Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    public String synchronise(Boolean addImage) {

        this.ColoneListe.clear();
        ObservableList<ArrayList<String>> liste = FXCollections.observableArrayList();

        liste = this.getAllData(addImage);

        ArrayList<String> Colone = this.getColoneListe();
        this.colone_reference = Colone.get(0);
        String data = Json.JsonFormat(liste, Colone);

        String colone = traitement.getString(Colone);
        String txt = this.POST(data, colone);

        return txt;
    }

    public void initialise() {
        this.ColoneListe.clear();
    }

    public String getColoneStatut() {
        return coloneStatut;
    }

    public void setColoneStatutSynch(String coloneStatut, boolean etat) {
        int value = 0;
        if (etat) {
            value = 1;
        }
        if (coloneStatut == null) {
            this.coloneStatut = "";
        } else {
            this.coloneStatut = " where " + coloneStatut + " = " + value;
        }
        this.coloneSyncho = coloneStatut;
    }

    public String getColone_reference() {
        return colone_reference;
    }

    public void setColone_reference(String colone_reference) {
        this.colone_reference = colone_reference;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public PreparedStatement getPst() {
        return pst;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabanse() {
        return databanse;
    }

    public void setDatabase(String databanse) {
        this.databanse = databanse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
