package Controller;

import DTO.ReservationDto;
import connectionDB.ConnexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private TableColumn<ReservationDto, Integer> tId;

    @FXML
    private TableView<ReservationDto> tableReservation;

    @FXML
    private TableColumn<ReservationDto, String> tcheckIn;

    @FXML
    private TableColumn<ReservationDto, String> tcheckOut;

    @FXML
    private TableColumn<ReservationDto, String> tlastName;

    @FXML
    private TableColumn<ReservationDto, Integer> troomNumber;

    @FXML
    private TableColumn<ReservationDto, String> tuserName;
    @FXML
    private TableColumn<ReservationDto, Double> tamount;
    @FXML
    private Button exit;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = ConnexionDB.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table();
    }

    @FXML
    void toHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home.fxml"));
        Scene homeScene = new Scene(loader.load());
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.setScene(homeScene);

    }
    private void table() {
        try {
            ObservableList<ReservationDto> reservationList = FXCollections.observableArrayList();
            st = con.prepareStatement("select r.ID,u.FIRSTNAME,u.LASTNAME, r.check_in_date, r.check_out_date , R2.ROOM_NUMBER ,F.AMOUNT " +
                    "from reservations r " +
                    "inner join users u on r.user_id = u.ID " +
                    "join ROOM R2 on R2.ID = r.ROOM_ID " +
                    "join FACTURE F on r.user_id = F.USER_ID");
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                Date checkin = rs.getDate("check_in_date");
                Date checkout = rs.getDate("check_out_date");
                int roomNumber = rs.getInt("ROOM_NUMBER");
                double amount = rs.getDouble("AMOUNT");


                ReservationDto reservationDto = new ReservationDto(id,firstName, lastName,roomNumber,checkin, checkout ,amount);

                reservationList.add(reservationDto);
            }

            tId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tuserName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tlastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tcheckIn.setCellValueFactory(new PropertyValueFactory<>("check_in_date"));
            tcheckOut.setCellValueFactory(new PropertyValueFactory<>("check_out_date"));
            troomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
            tamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tableReservation.setItems(reservationList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

