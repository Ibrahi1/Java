package com.fstt.esalaf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {
    PreparedStatement st = null ;
    ResultSet res = null;
    Connection con = ConnectionDB.getConnection();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<Commande, String> colclient;

    @FXML
    private TableColumn<Commande, String> coldate;

    @FXML
    private TableColumn<Commande, Integer> colid;

    @FXML
    private TableColumn<Commande, String> colproduit;

    @FXML
    private Button tfClear;

    @FXML
    private Button tfDelete;

    @FXML
    private Button tfSave;
    @FXML Button tfmenu;

    @FXML
    private TableView<Commande> tfTable;
    int id = 0;

    @FXML
    private Button tfUpdate;

    @FXML
    private TextField tf_Client;

    @FXML
    private TextField tf_Date;

    @FXML
    private TextField tf_Produit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCommande();
    }
    public ObservableList<Commande> getCommande(){
        ObservableList<Commande> commande = FXCollections.observableArrayList();

        String query = "select* from commande";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(query);
            res = st.executeQuery();
            while (res.next()){
                Commande cm = new Commande();
                cm.setId_commande((res.getInt("id_commande")));
                cm.setProduit(res.getString("produit"));
                cm.setClient(res.getString("client"));
                cm.setDate(res.getString("date"));
                commande.add(cm);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commande;
    }

    public void showCommande(){
        ObservableList<Commande> list = getCommande();
        tfTable.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Commande , Integer>("id_commande"));
        colproduit.setCellValueFactory(new PropertyValueFactory<Commande , String>("produit"));
        colclient.setCellValueFactory(new PropertyValueFactory<Commande , String>("client"));
        coldate.setCellValueFactory(new PropertyValueFactory<Commande , String>("date"));
    }


    @FXML
    void DeleteCommande(ActionEvent event) {
        String delete = "delete from commande where id_commande =?";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            showCommande();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void SaveCommande(ActionEvent event) {
        String insert = "insert into commande(produit,client,date) values(?,?,?)";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(insert);
            st.setString(1,tf_Produit.getText());
            st.setString(2,tf_Client.getText());
            st.setString(3,tf_Date.getText());
            st.executeUpdate();
            clear();
            showCommande();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void clear(){
        tf_Produit.setText(null);
        tf_Client.setText(null);
        tf_Date.setText(null);
        tfSave.setDisable(false);
    }
    @FXML
    void getData(MouseEvent event) {
        Commande commande = tfTable.getSelectionModel().getSelectedItem();
        id = commande.getId_commande();
        tf_Produit.setText(commande.getProduit());
        tf_Client.setText(commande.getClient());
        tf_Date.setText(commande.getDate());
        tfSave.setDisable(true);
    }

    @FXML
    void UpdateCommande(ActionEvent event) {

        String update = "update commande set produit = ?, client = ?, date = ? where id_commande =?";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(update);
            st.setString(1,tf_Produit.getText());
            st.setString(2,tf_Client.getText());
            st.setString(3,tf_Date.getText());
            st.setInt(4,id);
            st.executeUpdate();
            showCommande();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void ClearCommande(ActionEvent event) {
        clear();
    }

    @FXML
    void menuaction(ActionEvent event) {
        Stage home = new Stage();
        try {
            tfmenu.getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/Fxml/Dashboard.fxml"));
            scene = new Scene(root);
            home.setScene(scene);
            home.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}