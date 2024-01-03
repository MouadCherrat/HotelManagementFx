package Controller;

import connectionDB.ConnexionDB;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField tusername;
    public PasswordField tpassword;
    public Button btnconn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    btnconn.setOnAction(actionEvent -> login());
    }
    public void login() {
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = ConnexionDB.getConnection();
        try {
            st = con.prepareStatement("SELECT * FROM admin WHERE username =? AND password = ?");
            st.setString(1, tusername.getText());
            st.setString(2, tpassword.getText());
            rs = st.executeQuery();
            if (rs.next()) {
                loadHomeView();
            }
            else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "username ou password incorrect", ButtonType.OK);
            alert.show();
        }
        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadHomeView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Home.fxml"));
        Scene homeScene = new Scene(loader.load());
        Stage stage = (Stage) btnconn.getScene().getWindow();
        stage.setScene(homeScene);
    }
}
