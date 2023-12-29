package Controller;
import Model.Employer;
import connectionDB.ConnexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployerController implements Initializable {
    public TableView employerTable;
    @FXML
    private Button btnadd;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnupdate;
    @FXML
    private TableColumn<Employer, Integer> idcol;
    @FXML
    private TextField name;
    @FXML
    private TableColumn<Employer, String> namecol;
    @FXML
    private TextField poste;
    @FXML
    private TableColumn<Employer, String> postecol;
    @FXML
    private TextField salaire;
    @FXML
    private TableColumn<Employer, Double> salairecol;
    @FXML
    private AnchorPane table;
    @FXML
    private TextField tele;
    @FXML
    private TableColumn<Employer, String> telecol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table();
        employerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                click((Employer) newSelection);
            }
        });

    }


    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = ConnexionDB.getConnection();

    @FXML
    void add() {
        String tname, ttele, tposte;
        double tsalaire;
        tname = name.getText();
        ttele = tele.getText();
        tposte = poste.getText();

        if (tname.isEmpty() || ttele.isEmpty() || tposte.isEmpty() || salaire.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "veuillez remplir tous les champs", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("REMPLIR LES CHAMPS");
            alert.show();
            return;
        }
        try {
            tsalaire = Double.parseDouble(salaire.getText());
            if (tsalaire < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Salaire ne doit pas etre < 0", ButtonType.OK);
                alert.setTitle("ATTENTION");
                alert.setHeaderText("SALAIRE INF A ZERO");
                alert.show();
                return;
            }
            st = con.prepareStatement("insert into employer(name,tele,poste,salaire)values (?,?,?,?)");
            st.setString(1, tname);
            st.setString(2, ttele);
            st.setString(3, tposte);
            st.setDouble(4, tsalaire);
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajouter employer");
            alert.setHeaderText("ajouter employer");
            alert.setContentText("employer ajouter avec succes");
            alert.showAndWait();
            table();
            name.clear();
            tele.clear();
            poste.clear();
            salaire.clear();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void table() {
        try {
            ObservableList<Employer> employerList = FXCollections.observableArrayList();
            st = con.prepareStatement("SELECT * FROM employer");
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String tele = rs.getString("tele");
                String poste = rs.getString("poste");
                double salaire = rs.getDouble("salaire");
                Employer employer = new Employer(id, name, tele, poste, salaire);
                employerList.add(employer);
            }
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
            telecol.setCellValueFactory(new PropertyValueFactory<>("tele"));
            postecol.setCellValueFactory(new PropertyValueFactory<>("poste"));
            salairecol.setCellValueFactory(new PropertyValueFactory<>("salaire"));

            employerTable.setItems(employerList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete() {
        Employer selectedEmployer = (Employer) employerTable.getSelectionModel().getSelectedItem();
        if (selectedEmployer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez selectionner un employe a supprimer", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("Selectionner un employe");
            alert.show();
            return;
        }
        try {
            int employerId = selectedEmployer.getId();
            st = con.prepareStatement("DELETE FROM employer WHERE id = ?");
            st.setInt(1, employerId);
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supprimer employe");
            alert.setHeaderText("Supprimer employe");
            alert.setContentText("Employe supprime avec succes");
            alert.showAndWait();
            table();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update(){
        Employer selectedEmployer = (Employer) employerTable.getSelectionModel().getSelectedItem();
        String tname, ttele, tposte;
        tname = name.getText();
        ttele = tele.getText();
        tposte = poste.getText();
       if (tname.isEmpty() || ttele.isEmpty() || tposte.isEmpty() || salaire.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "veuillez remplir tous les champs", ButtonType.OK);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("REMPLIR LES CHAMPS");
            alert.show();
            return;
        }
        try {
            double tsalaire = Double.parseDouble(salaire.getText());
            if (tsalaire < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Salaire ne doit pas etre < 0", ButtonType.OK);
                alert.setTitle("ATTENTION");
                alert.setHeaderText("SALAIRE INF A ZERO");
                alert.show();
                return;
            }
            PreparedStatement st = con.prepareStatement("UPDATE employer SET name=?, tele=?, poste=?, salaire=? WHERE id=?");
            st.setString(1, tname);
            st.setString(2, ttele);
            st.setString(3, tposte);
            st.setDouble(4, tsalaire);
            st.setInt(5, selectedEmployer.getId());
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Mettre a jour employer", ButtonType.OK);
            alert.setTitle("Mise a jour");
            alert.setHeaderText("Employer mis a jours avec succes");
            alert.show();
            table();


        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Mettre a jour employer", ButtonType.OK);
            alert.setTitle("Mise a jour");
            alert.setHeaderText("Erreur lors de la mise a jours");
            alert.show();        }
    }

    private void click(Employer selectedEmployer) {
        name.setText(selectedEmployer.getName());
        tele.setText(selectedEmployer.getTele());
        poste.setText(selectedEmployer.getPoste());
        salaire.setText(String.valueOf(selectedEmployer.getSalaire()));
    }
}

