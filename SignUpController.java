package com.fstt.esalaf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    PreparedStatement st = null ;
    ResultSet res = null;
    Connection con = ConnectionDB.getConnection();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button Login_button;

    @FXML
    private Button SignUp_button;

    @FXML
    private TextField tf_Firstname;

    @FXML
    private TextField tf_Lastname;

    @FXML
    private TextField tf_Username;

    @FXML
    private TextField tf_password;

    @FXML
    public void Login(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void Signup(ActionEvent event) {
        String insert = "insert into users(first_name,last_name,username,password) values(?,?,?,?)";
        con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement(insert);
            st.setString(1,tf_Firstname.getText());
            st.setString(2,tf_Lastname.getText());
            st.setString(3,tf_Username.getText());
            st.setString(4,tf_password.getText());
            st.executeUpdate();

            Stage home = new Stage();
            try {
                SignUp_button.getScene().getWindow().hide();
                root = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));
                scene = new Scene(root);
                home.setScene(scene);
                home.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}