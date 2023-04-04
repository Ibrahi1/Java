package com.fstt.esalaf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public TextField tf_Username;
    public PasswordField tf_Password;
    public Button Login_button;
    public Button SignUp_button;
    @FXML
    private Label messagetext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Login_button.setOnAction(ActionEvent -> login());
    }


    public void SignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void login(){
        PreparedStatement st = null ;
        ResultSet res = null;
        Connection con = ConnectionDB.getConnection();
        try {
            st = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            st.setString(1,tf_Username.getText());
            st.setString(2,tf_Password.getText());
            res = st.executeQuery();
            if(res.next()){
                Stage home = new Stage();
                try {
                    Login_button.getScene().getWindow().hide();
                    root = FXMLLoader.load(getClass().getResource("/Fxml/Dashboard.fxml"));
                    scene = new Scene(root);
                    home.setScene(scene);
                    home.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                messagetext.setText("Username or Password incorect !");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
