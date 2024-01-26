/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seatsync_filmreservationsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Iris Jewel
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button signin_close;

    @FXML
    private Hyperlink signin_createaccount;

    @FXML
    private AnchorPane signin_form;

    @FXML
    private Button signin_loginBtn;

    @FXML
    private Button signin_minimize;

    @FXML
    private PasswordField signin_password;

    @FXML
    private TextField signin_username;

    @FXML
    private Hyperlink signup_alreadyhaveacc;

    @FXML
    private Button signup_btn;

    @FXML
    private Button signup_close;

    @FXML
    private TextField signup_emailadd;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private Button signup_minimize;

    @FXML
    private PasswordField signup_password;

    @FXML
    private TextField signup_username;

    //database
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public boolean validEmail() {
        //marcoman_123@marcoman.com
        Pattern pattern = Pattern.compile("[a-zA-z0-9][a-zA-z0-9._]*@[a-zA-z0-9]+([.][a-zA-Z]+)+");

        Matcher match = pattern.matcher(signup_emailadd.getText());

        Alert alert;

        if (match.find() && match.group().matches(signup_emailadd.getText())) {
            return true;

        } else {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email");
            alert.showAndWait();

            return false;
        }
    }

    public void signup() {

        String sql = "INSERT INTO admin (email,username,password) VALUES (?,?,?)";
        
         String sql1 = "SELECT username FROM admin";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signup_emailadd.getText());
            prepare.setString(2, signup_username.getText());
            prepare.setString(3, signup_password.getText());

            Alert alert;

            if (signup_emailadd.getText().isEmpty() || signup_username.getText().isEmpty()
                    || signup_password.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill up all the blank fields");
                alert.showAndWait();
                //RANGE OF PASSWORD IS 8 CHARACTERS

            } else if (signup_password.getText().length() < 8) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please input more than 8 characters");
                alert.showAndWait();

            } else {

                if (validEmail()) { 
                    
                    prepare = connect.prepareStatement(sql1);
                    result = prepare.executeQuery();
                    
                    if (result.next()){
                        
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText(signup_username.getText() + " was already exist!");
                        alert.showAndWait();
                        
                    }else{

                        prepare.execute();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully created a new account!");
                        alert.showAndWait();

                        signup_emailadd.setText(" ");
                        signup_username.setText(" ");
                        signup_password.setText(" ");   
                    }
                }
               
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private double x=0;
    private double y=0;

    public void signin() {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, signin_username.getText());
            prepare.setString(2, signin_password.getText());

            result = prepare.executeQuery();

            Alert alert;

            //check if username or password is empty
            if (signin_username.getText().isEmpty() || signin_password.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                if (result.next()) {
                    
                    getData.username = signin_username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfuly Login!");
                    alert.showAndWait();

                    signin_loginBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) -> {
                        
                        x = event.getSceneX();
                        y = event.getSceneY();
                    
                    });
                    
                    root.setOnMouseDragged((MouseEvent event) -> {
                        
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                } else {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == signin_createaccount) {

            signin_form.setVisible(false);
            signup_form.setVisible(true);

        } else if (event.getSource() == signup_alreadyhaveacc) {
            signin_form.setVisible(true);
            signup_form.setVisible(false);
        }
    }

    public void signin_close() {
        System.exit(0);
    }

    public void signin_minimize() {
        Stage stage = (Stage) signin_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void signup_close() {
        System.exit(0);
    }

    public void signup_minimize() {
        Stage stage = (Stage) signup_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
