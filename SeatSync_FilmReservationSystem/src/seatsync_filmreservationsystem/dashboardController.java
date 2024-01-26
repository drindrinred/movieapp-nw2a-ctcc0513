/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seatsync_filmreservationsystem;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dashboardController implements Initializable {

    @FXML
    private Button addMovies_btn;

    @FXML
    private Button addMovies_clearBtn;

    @FXML
    private TableColumn<moviesData, String> addMovies_col_date;

    @FXML
    private TableColumn<moviesData, String> addMovies_col_duration;

    @FXML
    private TableColumn<moviesData, String> addMovies_col_genre;

    @FXML
    private TableColumn<moviesData, String> addMovies_col_movie;

    @FXML
    private DatePicker addMovies_date;

    @FXML
    private Button addMovies_deleteBtn;

    @FXML
    private TextField addMovies_duration;

    @FXML
    private AnchorPane addMovies_form;

    @FXML
    private TextField addMovies_genre;

    @FXML
    private ImageView addMovies_imageView;

    @FXML
    private Button addMovies_import;

    @FXML
    private Button addMovies_insertBtn;

    @FXML
    private TextField addMovies_movieTitle;

    @FXML
    private TextField addMovies_search;

    @FXML
    private TableView<moviesData> addMovies_tableView;

    @FXML
    private Button addMovies_updateBtn;

    @FXML
    private Button availMovies_btn;

    @FXML
    private Button availMovies_buyBtn;

    @FXML
    private Button availMovies_clearBtn;

    @FXML
    private TableColumn<moviesData, String> availMovies_col_genre;

    @FXML
    private TableColumn<moviesData, String> availMovies_col_movieTitle;

    @FXML
    private TableColumn<moviesData, String> availMovies_col_showingDate;

    @FXML
    private Label availMovies_date;

    @FXML
    private AnchorPane availMovies_form;

    @FXML
    private Label availMovies_genre;

    @FXML
    private ImageView availMovies_imageView;

    @FXML
    private Label availMovies_movieTitle;

    @FXML
    private Label availMovies_normalClass_price;
    
    @FXML
    private Spinner<Integer> availMovies_specialClass_quantity;

    @FXML
    private Spinner<Integer> availMovies_normalClass_quantity;

    @FXML
    private Button availMovies_receiptBtn;

    @FXML
    private Button availMovies_selectMovie;

    @FXML
    private Label availMovies_specialClass_price;    

    @FXML
    private TableView<moviesData> availMovies_tableView;

    @FXML
    private Label availMovies_title;

    @FXML
    private Label availMovies_total;

    @FXML
    private Button close;

    @FXML
    private Button customers_btn;

    @FXML
    private Button customers_clearBtn;

    @FXML
    private TableView<customerData> customers_tableView;
    
    @FXML
    private TableColumn<customerData, String> customers_col_date;

    @FXML
    private TableColumn<customerData, String> customers_col_movieTitle;

    @FXML
    private TableColumn<customerData, String> customers_col_ticketNumber;

    @FXML
    private TableColumn<customerData, String> customers_col_time;

    @FXML
    private Label customers_date;

    @FXML
    private Button customers_deleteBtn;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private Label customers_genre;

    @FXML
    private Label customers_movieTitle;

    @FXML
    private TextField customers_search;

    @FXML
    private Label customers_ticketNumber;

    @FXML
    private Label customers_time;

    @FXML
    private Label dashboard_AvailMovies;

    @FXML
    private Label dashboard_SoldTicket;

    @FXML
    private Label dashboard_TotalEarn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button minimize;

    @FXML
    private Button signout;

    @FXML
    private Label username;

    @FXML
    private AnchorPane topForm;

    private Image image;

    private double x = 0;
    private double y = 0;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
     
    
    //AVAILABLE MOVIES FORM

    private SpinnerValueFactory<Integer> spinner1;
    private SpinnerValueFactory<Integer> spinner2;
    
    private float price1 = 0;
    private float price2 = 0;
    private float total = 0;
    private int qty1 = 0;
    private int qty2 = 0;
    
    
    public void searchCustomer(){
    
        FilteredList<customerData> filter = new FilteredList<>(custList, e -> true);
                
        customers_search.textProperty().addListener((observable, oldValue, newValue) ->{
            
            filter.setPredicate(predicateCustomer -> {
            
                if (newValue.isEmpty() || newValue == null){
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
                
                if (predicateCustomer.getId().toString().contains(searchKey)){
                    return true;
                }else if(predicateCustomer.getTitle().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCustomer.getDate().toString().contains(searchKey)){
                    return true;
                }else{                
                return false;
                }
            });        
        });
        
        SortedList<customerData> sort = new SortedList<>(filter);
        
        sort.comparatorProperty().bind(customers_tableView.comparatorProperty());
        customers_tableView.setItems(sort);
        
    }
    
    
    public ObservableList<customerData> customerList(){
        
        ObservableList<customerData> customerL = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM customer";
        
        connect = database.connectDB();
        
        try{
            
            customerData customerD;
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
            
                customerD = new customerData(result.getInt("id")
                        , result.getString("type")
                        , result.getString("movieTitle")
                        , result.getDouble("total")
                        , result.getDate("date")
                        , result.getTime("time"));
                
                customerL.add(customerD);
                
            }
        
        }catch (Exception e){e.printStackTrace();}
        return customerL;
    }
    
    
    private ObservableList<customerData> custList;
    public void showCustomerList(){
    
        custList = customerList();
        
        customers_col_ticketNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        customers_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customers_col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        
        customers_tableView.setItems(custList);
        
    }
    
    public void selectCustomerList(){
        
        customerData custD = customers_tableView.getSelectionModel().getSelectedItem();
        int num = customers_tableView.getSelectionModel().getFocusedIndex();
        
        if ((num - 1) < -1){
            return;
        }
        
        customers_ticketNumber.setText(String.valueOf(custD.getId()));
        customers_movieTitle.setText(custD.getTitle());
        customers_date.setText(String.valueOf(custD.getDate()));
        customers_time.setText(String.valueOf(custD.getTime()));
    }
    
    public void deleteCustomer(){
        
        String sql = "DELETE FROM customer WHERE id = '" + customers_ticketNumber.getText() + "'";
        
        connect = database.connectDB();
        
        try{
            
            Alert alert;
        
            statement = connect.createStatement();
            
            if  (customers_ticketNumber.getText().isEmpty()
                    || customers_movieTitle.getText().isEmpty()
                    || customers_date.getText().isEmpty()
                    || customers_time.getText().isEmpty()){
            
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the customer first");
                alert.showAndWait();
                
            }else{
            
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to delete " 
                        + customers_movieTitle.getText() + "?");
                    
                    Optional <ButtonType> option = alert.showAndWait();
                    
                    if(option.get() == ButtonType.OK){
                    
                        statement.executeUpdate(sql);
                        
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully removed");
                        alert.showAndWait();
                        
                        showCustomerList();
                        clearCustomer();
                        
                    }else{
                        return;
                    }               
            }
        
        }catch (Exception e){e.printStackTrace();}
    }
    
    public void clearCustomer(){
        customers_ticketNumber.setText("");
        customers_movieTitle.setText("");
        customers_date.setText("");
        customers_time.setText("");
    }
    
    
    public void receipt(){
    
        
    }
    
    public void buy(){
        
        //CREATE TABLES FOR CUSTOMER AND CUSTOMER INFO
        
        String sql = "INSERT INTO customer (type, movieTitle, total, date, time) VALUES (?,?,?,?,?)";

        
        connect = database.connectDB();
        String type = "";
        
        if(price1 > 0 && price2 == 0){       
            type = "Special Class";       
        }else if(price2 > 0 && price1 == 0){
            type = "Normal Class"; 
        }else if(price2 > 0 && price1 > 0){
            type = "Special & Normal Class";
        }
        
        Date date = new Date();
        java.sql.Date setDate = new java.sql.Date(date.getTime());
        
        try{
            
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, type);
            prepare.setString(2, availMovies_title.getText());
            prepare.setString(3, String.valueOf(total));
            prepare.setDate(4, setDate);
            prepare.setTimestamp(5, new java.sql.Timestamp(date.getTime()));
            
            Alert alert;
            
            //IF THE USER SELECTED A MOVIE
            if(availMovies_imageView.getImage() == null 
                    || availMovies_title.getText().isEmpty()){
                
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a movie first");
                alert.showAndWait();
                
            }else if(price1 == 0 && price2 == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please indicate the quantity of ticket you want to purchase");
                alert.showAndWait();
                
            }else{
                
                prepare.executeUpdate();
                
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully purchase!");
                alert.showAndWait();
                
                String sql1 = "SELECT * FROM customer";
                
                prepare = connect.prepareStatement(sql1);
                result = prepare.executeQuery();
                
                int num = 0;
                
                while(result.next()){
                    //GET THE LAST ID INSERTED
                    num = result.getInt("id");
                }
                
                String sql2 = "INSERT INTO customer_info (customer_id,type,total,movieTitle) VALUES (?,?,?,?)";
                
                prepare = connect.prepareStatement(sql2);
                prepare.setString(1, String.valueOf(num));
                prepare.setString(2, type);
                prepare.setString(3, String.valueOf(total));
                prepare.setString(4, availMovies_title.getText());
                prepare.execute();     
                
                clearPurchaseTicketInfo();
                        
            }
        
        }catch (Exception e){e.printStackTrace();}
    }
    
    public void clearPurchaseTicketInfo(){
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        
        availMovies_specialClass_quantity.setValueFactory(spinner1);
        availMovies_normalClass_quantity.setValueFactory(spinner2);
        
        availMovies_specialClass_price.setText("$0.0");
        availMovies_normalClass_price.setText("$0.0");
        availMovies_total.setText("$0.0");
        
        availMovies_imageView.setImage(null);
        availMovies_title.setText("");
    }
    
    public void showSpinnerValue(){
    
        spinner1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        spinner2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        
        availMovies_specialClass_quantity.setValueFactory(spinner1);
        availMovies_normalClass_quantity.setValueFactory(spinner2);    
               
    }
    
    public void getSpinnerValue(MouseEvent event){
    
        qty1 = availMovies_specialClass_quantity.getValue();
        qty2 = availMovies_normalClass_quantity.getValue();
        
        price1 = (qty1 * 25);
        price2 = (qty2 * 10);
        
        total = (price1 + price2);
        
        availMovies_specialClass_price.setText("$" + String.valueOf(price1));
        availMovies_normalClass_price.setText("$" + String.valueOf(price2));
        
        availMovies_total.setText("$" + String.valueOf(total));
    }
    
    
    public ObservableList<moviesData> availableMoviesList(){
    
            ObservableList<moviesData> listAvMovies = FXCollections.observableArrayList();
            
           String sql = "SELECT * FROM movie";
        
        connect = database.connectDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            moviesData movD;
            
            while(result.next()){
            
                movD = new moviesData(result.getInt("id")
                        , result.getString("movieTitle")
                        , result.getString("genre")
                        , result.getString("duration")
                        , result.getString("image")
                        , result.getDate("date"));
                
                listAvMovies.add(movD);
                
            }
        
        }catch (Exception e){e.printStackTrace();}
        return listAvMovies;     
    }
    
    private ObservableList<moviesData> availableMoviesList;
    public void showAvailableMovies(){
    
        availableMoviesList = availableMoviesList();
        
        availMovies_col_movieTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        availMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availMovies_col_showingDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        availMovies_tableView.setItems(availableMoviesList);
    }
    
    public void selectAvailableMovies(){
    
        moviesData movD = availMovies_tableView.getSelectionModel().getSelectedItem();
        int num = availMovies_tableView.getSelectionModel().getSelectedIndex();
        
        if((num -1) < -1){
            return;
        }
        
        availMovies_movieTitle.setText(movD.getTitle());
        availMovies_genre.setText(movD.getGenre());
        availMovies_date.setText(String.valueOf(movD.getDate()));
        
        getData.path = movD.getImage();
        getData.title = movD.getTitle();
    
    }
    
    public void selectMovie(){
        
        Alert alert;
        
        if(availMovies_movieTitle.getText().isEmpty()
           || availMovies_genre.getText().isEmpty()
           || availMovies_date.getText().isEmpty()){
        
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a movie first");
            alert.showAndWait();            
        }else{
        
            String uri = "file:" + getData.path;

            image = new Image(uri,133,196, false, true);

            availMovies_imageView.setImage(image);

            availMovies_title.setText(getData.title);

            //CLEAR TEXT
            availMovies_movieTitle.setText("");
            availMovies_genre.setText("");
            availMovies_date.setText("");
        }
    }
    

    //SEARCH BAR
    public void searchAddMovies() {

        FilteredList<moviesData> filter = new FilteredList<>(listAddMovies, e -> true);

        addMovies_search.textProperty().addListener((observable, oldValue, newValue) -> {

            filter.setPredicate(predicateMoviesData -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String keySearch = newValue.toLowerCase();

                if (predicateMoviesData.getTitle().toLowerCase().contains(keySearch)) {
                    return true;
                } else if (predicateMoviesData.getGenre().toLowerCase().contains(keySearch)) {
                    return true;
                } else if (predicateMoviesData.getDuration().toLowerCase().contains(keySearch)) {
                    return true;
                } else if (predicateMoviesData.getDate().toString().contains(keySearch)) {
                    return true;
                }

                return false;

            });

        });

        SortedList<moviesData> sortData = new SortedList<>(filter);
        sortData.comparatorProperty().bind(addMovies_tableView.comparatorProperty());

        addMovies_tableView.setItems(sortData);

    }

    //IMPORT MOVIE POSTER
    public void importImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*png", "*jpg"));

        Stage stage = (Stage) addMovies_form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {

            image = new Image(file.toURI().toString(), 127, 186, false, true);
            addMovies_imageView.setImage(image);

            //TO GET THE IMAGE PATH
            getData.path = file.getAbsolutePath();
        }
    }

    //ID
    public void movieId() {
        String sql = "SELECT count(id) FROM movie";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                getData.movieId = result.getInt("count(id)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAddMovies() {

        String sql1 = "SELECT * FROM movie WHERE movieTitle = '"
                + addMovies_movieTitle.getText() + "'";

        connect = database.connectDB();

        Alert alert;

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql1);

            if (result.next()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText(addMovies_movieTitle.getText() + " was already exist!");
                alert.showAndWait();

            } else {

                if (addMovies_movieTitle.getText().isEmpty()
                        || addMovies_genre.getText().isEmpty()
                        || addMovies_duration.getText().isEmpty()
                        || addMovies_imageView.getImage() == null
                        || addMovies_date.getValue() == null) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all the blank fields");
                    alert.showAndWait();

                } else {

                    String sql
                            = "INSERT INTO movie (id,movieTitle,genre,duration,image,date) VALUES (?,?,?,?,?,?)";

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    movieId();

                    String mID = String.valueOf(getData.movieId + 1);

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, mID);
                    prepare.setString(2, addMovies_movieTitle.getText());
                    prepare.setString(3, addMovies_genre.getText());
                    prepare.setString(4, addMovies_duration.getText());
                    prepare.setString(5, uri);
                    prepare.setString(6, String.valueOf(addMovies_date.getValue()));

                    prepare.execute();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully add new movie!");
                    alert.showAndWait();

                    clearAddMoviesList();
                    showAddMoviesList();

                }
            }

            //IF HAVE THE MOVIE TITLE THAT ALREADY EXIST
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //UPDATE THE MOVIES
    public void updateAddMovies() {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE movie SET movieTitle = '" + addMovies_movieTitle.getText()
                + "', genre = '" + addMovies_genre.getText()
                + "', duration = '" + addMovies_duration.getText()
                + "', image = '" + uri + "', date = '" + addMovies_date.getValue()
                + "' WHERE id = '" + String.valueOf(getData.movieId) + "'";

        connect = database.connectDB();

        try {

            statement = connect.createStatement();

            Alert alert;

            if (addMovies_movieTitle.getText().isEmpty()
                    || addMovies_genre.getText().isEmpty()
                    || addMovies_duration.getText().isEmpty()
                    || addMovies_imageView.getImage() == null
                    || addMovies_date.getValue() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the movie first");
                alert.showAndWait();

            } else {

                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully update " + addMovies_movieTitle.getText());
                alert.showAndWait();

                showAddMoviesList();
                clearAddMoviesList();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public void deleteAddMovies() {
        String sql = "DELETE FROM movie WHERE movieTitle = '"
                + addMovies_movieTitle.getText() + "'";

        connect = database.connectDB();

        try {

            statement = connect.createStatement();

            Alert alert;

            if (addMovies_movieTitle.getText().isEmpty()
                    || addMovies_genre.getText().isEmpty()
                    || addMovies_duration.getText().isEmpty()
                    || addMovies_date.getValue() == null
                    || addMovies_imageView.getImage() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the movie first");
                alert.showAndWait();

            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete " + addMovies_movieTitle.getText() + "?");
                alert.showAndWait();

                Optional<ButtonType> option = alert.showAndWait();

                statement.executeUpdate(sql);

                showAddMoviesList();
                clearAddMoviesList();

                if (ButtonType.OK.equals(option.get())) {

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted");
                    alert.showAndWait();
                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CLEAR
    public void clearAddMoviesList() {
        addMovies_movieTitle.setText("");
        addMovies_genre.setText("");
        addMovies_imageView.setImage(null);
        addMovies_date.setValue(null);
        addMovies_duration.setText("");
    }

    //FUNCTION OF ADDMOVIES
    public ObservableList<moviesData> addMoviesList() {

        ObservableList<moviesData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM movie";

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            moviesData movD;

            while (result.next()) {
                movD = new moviesData(result.getInt("id"),
                        result.getString("movieTitle"),
                        result.getString("genre"), result.getString("duration"),
                        result.getString("image"), result.getDate("date"));

                listData.add(movD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<moviesData> listAddMovies;

    public void showAddMoviesList() {
        listAddMovies = addMoviesList();

        addMovies_col_movie.setCellValueFactory(new PropertyValueFactory<>("title"));
        addMovies_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        addMovies_col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        addMovies_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addMovies_tableView.setItems(listAddMovies);
    }

    public void selectAddMoviesList() {
        moviesData movD = addMovies_tableView.getSelectionModel().getSelectedItem();
        int num = addMovies_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        getData.path = movD.getImage();

        getData.movieId = movD.getId();

        addMovies_movieTitle.setText(movD.getTitle());
        addMovies_genre.setText(movD.getGenre());
        addMovies_duration.setText(movD.getDuration());

        String getDate = String.valueOf(movD.getDate());

        String uri = "file:" + movD.getImage();

        image = new Image(uri, 127, 186, false, true);
        addMovies_imageView.setImage(image);

        addMovies_date.setValue(movD.getDate().toLocalDate());

    }

    //LOGOUT
    public void signout() {
        signout.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {

                x = event.getSceneX();
                y = event.getSceneY();

            });

            root.setOnMousePressed((MouseEvent event) -> {

                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);

            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //GILID SWITCHING FORMS
    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addMovies_form.setVisible(false);
            availMovies_form.setVisible(false);
            customers_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color:#ae2d3c;");
            addMovies_btn.setStyle("-fx-background-color:transparent");
            availMovies_btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addMovies_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(true);
            availMovies_form.setVisible(false);
            customers_form.setVisible(false);

            addMovies_btn.setStyle("-fx-background-color:#ae2d3c;");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            availMovies_btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");
            
            showAddMoviesList();

        } else if (event.getSource() == availMovies_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availMovies_form.setVisible(true);
            customers_form.setVisible(false);

            availMovies_btn.setStyle("-fx-background-color:#ae2d3c;");
            addMovies_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            customers_btn.setStyle("-fx-background-color:transparent");
            
            showAvailableMovies();

        }else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            addMovies_form.setVisible(false);
            availMovies_form.setVisible(false);
            customers_form.setVisible(true);

            customers_btn.setStyle("-fx-background-color:#ae2d3c;");
            addMovies_btn.setStyle("-fx-background-color:transparent");
            availMovies_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            
            showCustomerList();

        }

    }

    //GET USERNAME AND DISPLAY
    public void displayUsername() {
        username.setText(getData.username);
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) topForm.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayUsername();
      
        showAvailableMovies();
        showSpinnerValue();
        
        showCustomerList();
    }

}
