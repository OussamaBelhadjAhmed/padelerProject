package gui;

import entite.Reclamation;
import service.ReclamationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;

public class UserReclamationController {
    @FXML
    private TableView<Reclamation> table_view;
    @FXML
    private TableColumn<Reclamation, Integer> col_id;
    @FXML
    private TableColumn<Reclamation, Integer> col_idU;
    @FXML
    private TableColumn<Reclamation, String> col_Email;
    @FXML
    private TableColumn<Reclamation, Date> col_date;
    @FXML
    private TableColumn<Reclamation, String> description;
    @FXML
    private TableColumn<Reclamation, String> col_Statut;
    @FXML
    private TableColumn<Reclamation, Void> col_Action;

    private ReclamationService reclamationService;

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TableColumn<Reclamation, String> col_Categorie;

    public UserReclamationController() {
        reclamationService = new ReclamationService();
    }

    public void initialize() {
        // Set cell value factories for table columns
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_idU.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_Statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        col_Categorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));

        categoryComboBox.getItems().addAll("Club", "Terrain", "Matériel","Toutes"); // Remplacez les valeurs par les catégories réelles
        categoryComboBox.setOnAction(event -> filterByCategory());

        col_Statut.setCellFactory(column -> new TableCell<Reclamation, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setAlignment(Pos.CENTER);
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        // Set up action column
        col_Action.setCellFactory(column -> new ButtonCell());

        // Set default value for the "Statut" column
        col_Statut.setCellFactory(column -> new TableCell<Reclamation, String>() {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null || item.isEmpty()) {
            setText("En attente");
        } else {
            setText(item);
        }
        setAlignment(Pos.CENTER);
    }
});

        // Enable row selection and handle double-click event
        table_view.setRowFactory(tv -> {
            TableRow<Reclamation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Reclamation selectedReclamation = row.getItem();
                    showDetails(selectedReclamation);
                }
            });
            return row;
        });

        // Populate table with data
        List<Reclamation> reclamationList = reclamationService.readAll();
        table_view.setItems(FXCollections.observableArrayList(reclamationList));
    }

    private void showDetails(Reclamation reclamation) {
        DetailsDialog dialog = new DetailsDialog(reclamation);
        dialog.showAndWait();
    }

    private void filterByCategory() {
    String selectedCategory = categoryComboBox.getValue();

    if (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("Toutes")) {
        // Afficher toutes les réclamations si aucune catégorie n'est sélectionnée ou si "Toutes" est sélectionnée
        List<Reclamation> reclamationList = reclamationService.readAll();
        table_view.setItems(FXCollections.observableArrayList(reclamationList));
    } else {
        // Filtrer les réclamations en fonction de la catégorie sélectionnée
        List<Reclamation> filteredList = table_view.getItems().stream()
                .filter(reclamation -> reclamation.getCategorie().equals(selectedCategory))
                .collect(Collectors.toList());
        table_view.setItems(FXCollections.observableArrayList(filteredList));
    }
}


    private class ButtonCell extends TableCell<Reclamation, Void> {

        private final Button updateButton = new Button("Reponse");
        private final Button deleteButton = new Button("Delete");

        ButtonCell() {
            updateButton.setStyle("-fx-background-color: #378b29; -fx-text-fill: white;");
            deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

            updateButton.setOnAction(event -> {
                Reclamation reclamation = getTableView().getItems().get(getIndex());
                UpdateDialog dialog = new UpdateDialog(reclamation);
                dialog.showAndWait();
                if (dialog.isSuccess()) {
                    reclamationService.update(reclamation);
                    List<Reclamation> reclamationList = reclamationService.readAll();
                    table_view.getItems().clear();
                    table_view.getItems().addAll(reclamationList);
                    System.out.println("Reclamation updated successfully!");
                }
            });

            deleteButton.setOnAction(event -> {
                Reclamation reclamation = getTableView().getItems().get(getIndex());
                DeleteDialog dialog = new DeleteDialog(reclamation);
                dialog.showAndWait();
                if (dialog.isConfirmed()) {
                    reclamationService.delete(reclamation.getId());
                    getTableView().getItems().remove(reclamation);
                    System.out.println("Reclamation deleted successfully!");
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox buttonBox = new HBox(5);
                buttonBox.setAlignment(Pos.CENTER);
                buttonBox.getChildren().addAll(updateButton, deleteButton);
                setGraphic(buttonBox);
            }
        }
    }
}
