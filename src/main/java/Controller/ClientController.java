package Controller;

import Model.Client;
import Model.Employe;
import connectionDB.ConnexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    public TableView ClientTable;
    public Button delete;
    public Button exit;

    @FXML
    private TableColumn<Client,String> temail;

    @FXML
    private TableColumn<Client,String> tfirstname;

    @FXML
    private TableColumn<Client,Integer> tid;

    @FXML
    private TableColumn<Client,String> tlastname;

    @FXML
    private TableColumn<Client,String> tpassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }

    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = ConnexionDB.getConnection();

    private void table() {
        try {
            ObservableList<Client> clienttable = FXCollections.observableArrayList();
            st = con.prepareStatement("SELECT * FROM users");
            rs = st.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String password = rs.getString("password");
                Client client = new Client(id, email, firstname, lastname, password);
                clienttable.add(client);
            }
            tid.setCellValueFactory(new PropertyValueFactory<>("id"));
            temail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tfirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tlastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tpassword.setCellValueFactory(new PropertyValueFactory<>("password"));

            ClientTable.setItems(clienttable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void DeleteClient() {
        Client selectedClient = (Client) ClientTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez selectionner un client a supprimer", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("Selectionner un client");
            alert.show();
            return;
        }
        try {
            int clientId = selectedClient.getId();
            st = con.prepareStatement("DELETE FROM users WHERE id = ?");
            st.setInt(1, clientId);
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supprimer client");
            alert.setHeaderText("Supprimer client");
            alert.setContentText("Client supprime avec succes");
            alert.showAndWait();
            table();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void toHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home.fxml"));
        Scene homeScene = new Scene(loader.load());
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.setScene(homeScene);

    }
}
