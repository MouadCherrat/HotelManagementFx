package Controller;

import DTO.ReservationDto;
import Model.Employe;
import Model.Room;
import connectionDB.ConnexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class RoomController implements Initializable {

    @FXML
    private Button addbtn;

    @FXML
    private Button deletebtn;

    @FXML
    private Button exit;

    @FXML
    private TextField lnombre_lits;

    @FXML
    private TextField lprice;

    @FXML
    private TextField lroom_number;

    @FXML
    private TextField lstatus;

    @FXML
    private TableColumn<Room, Integer> tId;

    @FXML
    private TableView<Room> tableChambre;

    @FXML
    private TableColumn<Room, Integer> tnombre_lits;

    @FXML
    private TableColumn<Room, Double> tprice;

    @FXML
    private TableColumn<Room, Integer> troom_number;

    @FXML
    private TableColumn<Room, String> tstatus;
    @FXML
    private ToggleButton disponibleToggle;

    @FXML
    private ToggleButton nonDisponibleToggle;

    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = ConnexionDB.getConnection();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            table();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lstatus.setEditable(false);
        disponibleToggle.setOnAction(event -> handleToggleSelection("DISPONIBLE"));
        nonDisponibleToggle.setOnAction(event -> handleToggleSelection("NON_DISPONIBLE"));
        tableChambre.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                click(newSelection);
            }
        });
    }

    private void handleToggleSelection(String status) {
        lstatus.setText(status);



    }


    public void table () throws SQLException {
        ObservableList<Room> roomList = FXCollections.observableArrayList();
        st=con.prepareStatement("select * from ROOM");
        rs=st.executeQuery();
        while(rs.next()){
            int id = rs.getInt(1);
            int nombre_lits = rs.getInt(2);
            Double price = rs.getDouble(3);
            int room_number = rs.getInt(4);
            String status = rs.getString(5);
            Room room = new Room(id,nombre_lits,price,room_number,status);
            roomList.add(room);

        }
        tId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnombre_lits.setCellValueFactory(new PropertyValueFactory<>("nombre_lits"));
        tprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        troom_number.setCellValueFactory(new PropertyValueFactory<>("room_number"));
        tstatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        tableChambre.setItems(roomList);




    }
    @FXML
    void addRoom() {
        try {
            String nombre_litsText = lnombre_lits.getText();
            String priceText = lprice.getText();
            String room_numberText = lroom_number.getText();
            String status = lstatus.getText();

            if (nombre_litsText.isEmpty() || priceText.isEmpty() || room_numberText.isEmpty() || status.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs", ButtonType.OK);
                alert.setTitle("ATTENTION");
                alert.setHeaderText("REMPLIR LES CHAMPS");
                alert.show();
                return;
            }

            int nombre_lits = Integer.parseInt(nombre_litsText);
            double price = Double.parseDouble(priceText);
            int room_number = Integer.parseInt(room_numberText);

            if (nombre_lits > 4) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Le nombre de lits maximum est 4 ", ButtonType.OK);
                alert.setTitle("ATTENTION");
                alert.setHeaderText("BEDS OUT OF RANGE");
                alert.show();
                return;
            }

            st = con.prepareStatement("INSERT INTO ROOM(nombre_lits, price, room_number, status) VALUES (?, ?, ?, ?)");
            st.setInt(1, nombre_lits);
            st.setDouble(2, price);
            st.setInt(3, room_number);
            st.setString(4, status);
            st.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajouter une chambre");
            alert.setHeaderText("Ajouter une chambre");
            alert.setContentText("Chambre ajoutée avec succès");
            alert.showAndWait();

            table();
            lnombre_lits.clear();
            lprice.clear();
            lroom_number.clear();
            lstatus.clear();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez saisir des valeurs numériques valides", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("VALEURS INVALIDES");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void deleteRoom() {
        Room selectedRoom = tableChambre.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez selectionner une chambre a supprimer", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("Selectionner une chambre");
            alert.show();
            return;
        }
        try {
            int roomId = selectedRoom.getId();
            st = con.prepareStatement("delete from ROOM where id = ?");
            st.setInt(1,roomId);
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supprimer chmabre");
            alert.setHeaderText("Supprimer une chambre");
            alert.setContentText("chmabre supprimee avec succes");
            alert.showAndWait();
            table();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void toHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home.fxml"));
        Scene homeScene = new Scene(loader.load());
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.setScene(homeScene);

    }


    public void updateRoom() {
        Room selectedRoom = tableChambre.getSelectionModel().getSelectedItem();
        try {
            String nombre_litsText = lnombre_lits.getText();
            String priceText = lprice.getText();
            String room_numberText = lroom_number.getText();
            String status = lstatus.getText();

            if (nombre_litsText.isEmpty() || priceText.isEmpty() || room_numberText.isEmpty() || status.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs", ButtonType.OK);
                alert.setTitle("ATTENTION");
                alert.setHeaderText("REMPLIR LES CHAMPS");
                alert.show();
                return;
            }

            int nombre_lits = Integer.parseInt(nombre_litsText);
            double price = Double.parseDouble(priceText);
            int room_number = Integer.parseInt(room_numberText);

            if (nombre_lits > 4) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Le nombre de lits maximum est 4 ", ButtonType.OK);
                alert.setTitle("ATTENTION");
                alert.setHeaderText("BEDS OUT OF RANGE");
                alert.show();
                return;
            }

            st = con.prepareStatement("update ROOM set nombre_lits=?,price=?,room_number=?,status=? where id=?");
            st.setInt(1, nombre_lits);
            st.setDouble(2, price);
            st.setInt(3, room_number);
            st.setString(4, status);
            st.setInt(5, selectedRoom.getId());
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Mettre a jour des chambres", ButtonType.OK);
            alert.setTitle("Mise a jour");
            alert.setHeaderText("chambre mise a jours avec succes");
            alert.show();
            table();
            lnombre_lits.clear();
            lprice.clear();
            lroom_number.clear();
            lstatus.clear();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez saisir des valeurs numériques valides", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("VALEURS INVALIDES");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void click(Room selectedRoom) {
        lnombre_lits.setText(String.valueOf(selectedRoom.getNombre_lits()));
        lprice.setText(String.valueOf(selectedRoom.getPrice()));
        lroom_number.setText(String.valueOf(selectedRoom.getRoom_number()));
        lstatus.setText(String.valueOf(selectedRoom.getStatus()));
    }
}
