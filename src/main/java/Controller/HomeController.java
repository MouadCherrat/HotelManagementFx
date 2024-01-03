package Controller;

import connectionDB.ConnexionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button clientbtn;

    @FXML
    private Label clientcount;

    @FXML
    private Button employebtn;

    @FXML
    private Label employecount;

    @FXML
    private Button homebtn;

    @FXML
    private Button logout;

    @FXML
    private Button reservationbtn;

    @FXML
    private Label reservationcount;

    @FXML
    private Button rooms;

    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = ConnexionDB.getConnection();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            setClientount();
            setReservationount();
            setEmployeount() ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEmployeount() throws SQLException {
        st = con.prepareStatement("SELECT count(*) FROM employe");
        rs = st.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        employecount.setText(String.valueOf(count));

    }
    public void setClientount() throws SQLException {
        st = con.prepareStatement("SELECT count(*) FROM users");
        rs = st.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        clientcount.setText(String.valueOf(count));

    }
    public void setReservationount() throws SQLException {
        st = con.prepareStatement("SELECT count(*) FROM reservations");
        rs = st.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        reservationcount.setText(String.valueOf(count));
    }



    @FXML
    void exit() throws IOException {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(loginScene);
        }

    }

    @FXML
    void toClient() {

    }

    @FXML
    void toEmploye() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employer.fxml"));
        Scene employeScene = new Scene(loader.load());
        Stage stage = (Stage) employebtn.getScene().getWindow();
        stage.setScene(employeScene);

    }

    @FXML
    void toHome() {

    }

    @FXML
    void toReservation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Reservation.fxml"));
        Scene reservationScene = new Scene(loader.load());
        Stage stage = (Stage) employebtn.getScene().getWindow();
        stage.setScene(reservationScene);

    }

    @FXML
    void toRoom() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Room.fxml"));
        Scene roomscene = new Scene(loader.load());
        Stage stage = (Stage) rooms.getScene().getWindow();
        stage.setScene(roomscene);


    }

}
