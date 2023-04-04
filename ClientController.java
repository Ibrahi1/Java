package com.fstt.esalaf;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientController implements Initializable {

    PreparedStatement st = null ;
    ResultSet res = null;
    Connection con = ConnectionDB.getConnection();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField chercheclient;
    @FXML
    private Button tfClear;

    @FXML
    private Button tfDelete;

    @FXML
    private Button tfSave;
    @FXML Button tfmenu;

    @FXML
    private TableColumn<Client, String> colclient;

    @FXML
    private TableColumn<Client, String> colcredit;

    @FXML
    private TableColumn<Client, Integer> colid;

    @FXML
    private TableColumn<Client, String> coltel;

    @FXML
    private TableView<Client> tfTable;

    int id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showClient();

    }


    public ObservableList<Client> getClient(){
        ObservableList<Client> client = FXCollections.observableArrayList();

        String query = "select* from client";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(query);
            res = st.executeQuery();
            while (res.next()){
                Client cl = new Client();
                cl.setId_client(res.getInt("id_client"));
                cl.setClient(res.getString("client"));
                cl.setN_tel(res.getString("n_tel"));
                cl.setCredit(res.getString("credit"));
                client.add(cl);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    public void showClient(){
        ObservableList<Client> list = getClient();
        tfTable.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Client , Integer>("id_client"));
        colclient.setCellValueFactory(new PropertyValueFactory<Client , String>("client"));
        coltel.setCellValueFactory(new PropertyValueFactory<Client , String>("n_tel"));
        colcredit.setCellValueFactory(new PropertyValueFactory<Client , String>("credit"));
    }
    @FXML
    private Button tfUpdate;

    @FXML
    private TextField tf_Client;

    @FXML
    private TextField tf_Credit;

    @FXML
    private TextField tf_Tel;
    @FXML
    void ClearClient(ActionEvent event) {
        clear();
    }

    @FXML
    void DeleteClient(ActionEvent event) {
        String delete = "delete from client where id_client =?";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            showClient();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void SaveClient(ActionEvent event) {
        String insert = "insert into client(client,n_tel,credit) values(?,?,?)";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(insert);
            st.setString(1,tf_Client.getText());
            st.setString(2,tf_Tel.getText());
            st.setString(3,tf_Credit.getText());
            st.executeUpdate();
            clear();
            showClient();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    void clear(){
        tf_Client.setText(null);
        tf_Tel.setText(null);
        tf_Credit.setText(null);
        tfSave.setDisable(false);
    }
    @FXML
    void getData(MouseEvent event) {
        Client client = tfTable.getSelectionModel().getSelectedItem();
        id = client.getId_client();
        tf_Client.setText(client.getClient());
        tf_Tel.setText(client.getN_tel());
        tf_Credit.setText(client.getCredit());
        tfSave.setDisable(true);
    }

    @FXML
    void UpdateClient(ActionEvent event) {

        String update = "update client set client = ?, n_tel = ?, credit = ? where id_client =?";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(update);
            st.setString(1,tf_Client.getText());
            st.setString(2,tf_Tel.getText());
            st.setString(3,tf_Credit.getText());
            st.setInt(4,id);
            st.executeUpdate();
            showClient();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
