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

public class ProduitController implements Initializable {
    PreparedStatement st = null ;
    ResultSet res = null;
    Connection con = ConnectionDB.getConnection();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<Produit, Integer> colid;

    @FXML
    private TableColumn<Produit, String> colprix;

    @FXML
    private TableColumn<Produit, String> colproduit;

    @FXML
    private TableColumn<Produit, String> colquantite;

    @FXML
    private Button tfClear;

    @FXML
    private Button tfDelete;

    @FXML
    private Button tfSave;

    @FXML
    private TableView<Produit> tfTable;
    int id = 0 ;

    @FXML
    private Button tfUpdate;
    @FXML
    private Button menu;

    @FXML
    private TextField tf_prix;

    @FXML
    private TextField tf_produit;

    @FXML
    private TextField tf_quantite;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProduit();
    }
    public ObservableList<Produit> getProduit(){
        ObservableList<Produit> produit = FXCollections.observableArrayList();

        String query = "select* from produit";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(query);
            res = st.executeQuery();
            while (res.next()){
                Produit pr = new Produit();
                pr.setId_produit((res.getInt("id_produit")));
                pr.setProduit(res.getString("produit"));
                pr.setPrix(res.getString("prix"));
                pr.setQuantite(res.getString("quantite"));
                produit.add(pr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produit;
    }

    public void showProduit(){
        ObservableList<Produit> list = getProduit();
        tfTable.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Produit , Integer>("id_produit"));
        colproduit.setCellValueFactory(new PropertyValueFactory<Produit , String>("produit"));
        colprix.setCellValueFactory(new PropertyValueFactory<Produit , String>("prix"));
        colquantite.setCellValueFactory(new PropertyValueFactory<Produit , String>("quantite"));
    }


    @FXML
    void DeleteProduit(ActionEvent event) {
        String delete = "delete from produit where id_produit =?";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            showProduit();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void SaveProduit(ActionEvent event) {
        String insert = "insert into produit(produit,prix,quantite) values(?,?,?)";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(insert);
            st.setString(1,tf_produit.getText());
            st.setString(2,tf_prix.getText());
            st.setString(3,tf_quantite.getText());
            st.executeUpdate();
            clear();
            showProduit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void clear(){
        tf_produit.setText(null);
        tf_prix.setText(null);
        tf_quantite.setText(null);
        tfSave.setDisable(false);
    }
    @FXML
    void getData(MouseEvent event) {
        Produit produit = tfTable.getSelectionModel().getSelectedItem();
        id = produit.getId_produit();
        tf_produit.setText(produit.getProduit());
        tf_prix.setText(produit.getPrix());
        tf_quantite.setText(produit.getQuantite());
        tfSave.setDisable(true);
    }

    @FXML
    void UpdateProduit(ActionEvent event) {

        String update = "update produit set produit = ?, prix = ?, quantite = ? where id_produit =?";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(update);
            st.setString(1,tf_produit.getText());
            st.setString(2,tf_prix.getText());
            st.setString(3,tf_quantite.getText());
            st.setInt(4,id);
            st.executeUpdate();
            showProduit();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void ClearProduit(ActionEvent event) {
        clear();
    }

    @FXML
    void menuaction(ActionEvent event) {
        Stage home = new Stage();
        try {
            menu.getScene().getWindow().hide();
            root = FXMLLoader.load(getClass().getResource("/Fxml/Dashboard.fxml"));
            scene = new Scene(root);
            home.setScene(scene);
            home.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
