/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Club;
import entite.Disponibiliteterrain;
import entite.Terrain;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import service.ClubService;
import service.DiponibiliteTerrainService;
import service.TerrainService;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class ManageDisponibiliteTerrainController implements Initializable {

    @FXML
    private TableView<Disponibiliteterrain> terrainsTable;

    @FXML
    private TableColumn<Disponibiliteterrain, Date> dateAffichage;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp1;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp2;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp3;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp4;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp5;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp6;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp7;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp8;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp9;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp10;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp11;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp12;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp13;
    @FXML
    private TableColumn<Disponibiliteterrain, Integer> tmp14;
    @FXML
    private TableColumn<Disponibiliteterrain, String> nomTerrain;
    @FXML
    private ChoiceBox<String> nomTerrainInterface;
    private List<Terrain> terrainsInterface = new ArrayList<Terrain>(0);
    ObservableList<Disponibiliteterrain> dispoTerrainList = FXCollections.observableArrayList();
    Disponibiliteterrain disponibiliteterrain = null;

    @FXML
    private ChoiceBox<String> nomClubInterface;
    private List<Club> clubsInterface = new ArrayList<Club>(0);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
        chargeListClub();
        nomClubInterface.getItems().add(0, "All");
        List<String> nomsClub = new ArrayList<>();
        nomsClub = clubsInterface.stream().map(Club::getClubName).collect(Collectors.toList());
          if (nomsClub.size() != 0) {
            nomClubInterface.getItems().addAll(nomsClub);
        } else {
            nomClubInterface.getItems().add("Acun Club !");
        }

    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddViewTerrain(MouseEvent event) {
    }

    @FXML
    private void refreshTerrainTable() {
        DiponibiliteTerrainService ts = new DiponibiliteTerrainService();
        String clubNamess = nomClubInterface.getValue();

        try {

            if (clubNamess != null && clubNamess.length() != 0 && !clubNamess.equals("All")) {
                dispoTerrainList.clear();
                System.out.println("clubNamess : " + clubNamess);
                dispoTerrainList.addAll(ts.readAll());
                terrainsTable.setItems(dispoTerrainList);

            } else {
                dispoTerrainList.clear();
                dispoTerrainList.addAll(ts.readAll());
                terrainsTable.setItems(dispoTerrainList);

            }

        } catch (Exception e) {
            Logger.getLogger(TableViewTerrainController.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    @FXML
    private void print(MouseEvent event) {
    }

    private void loadDate() {

        refreshTerrainTable();
        DiponibiliteTerrainService ts = new DiponibiliteTerrainService();

        dateAffichage.setCellValueFactory(new PropertyValueFactory<>("date"));
        tmp1.setCellValueFactory(new PropertyValueFactory<>("temps1"));
        tmp2.setCellValueFactory(new PropertyValueFactory<>("temps2"));
        tmp3.setCellValueFactory(new PropertyValueFactory<>("temps3"));
        tmp4.setCellValueFactory(new PropertyValueFactory<>("temps4"));
        tmp5.setCellValueFactory(new PropertyValueFactory<>("temps5"));
        tmp6.setCellValueFactory(new PropertyValueFactory<>("temps6"));
        tmp7.setCellValueFactory(new PropertyValueFactory<>("temps7"));
        tmp8.setCellValueFactory(new PropertyValueFactory<>("temps8"));
        tmp9.setCellValueFactory(new PropertyValueFactory<>("temps9"));
        tmp10.setCellValueFactory(new PropertyValueFactory<>("temps10"));
        tmp11.setCellValueFactory(new PropertyValueFactory<>("temps11"));
        tmp12.setCellValueFactory(new PropertyValueFactory<>("temps12"));
        tmp13.setCellValueFactory(new PropertyValueFactory<>("temps13"));
        tmp14.setCellValueFactory(new PropertyValueFactory<>("temps14"));

        System.out.print("test list :" + dispoTerrainList);

        //user.setCellValueFactory(new PropertyValueFactory<>("user"));
        //emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //add cell of button edit 
        Callback<TableColumn<Disponibiliteterrain, String>, TableCell<Disponibiliteterrain, String>> cellFoctory = (TableColumn<Disponibiliteterrain, String> param) -> {
            // make cell containing buttons
            final TableCell<Disponibiliteterrain, String> cell = new TableCell<Disponibiliteterrain, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {

                                disponibiliteterrain = terrainsTable.getSelectionModel().getSelectedItem();
                                ts.delete(disponibiliteterrain.getIdDisponibiliteTerrain());

                                refreshTerrainTable();

                            } catch (Exception ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            disponibiliteterrain = terrainsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("addTerrain.fxml"));
                            try {
                                loader.load();

                            } catch (IOException ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddTerrainController addTerrainController = loader.getController();
                            addTerrainController.setUpdate(true);
                            // addTerrainController.setTextField(terrain.getIdTerrain(), terrain.getName(), terrain.getStatus(), terrain.getClub().getClubName());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            refreshTerrainTable();
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        //editTerrain.setCellFactory(cellFoctory);
        terrainsTable.setItems(dispoTerrainList);

    }

    @FXML
    private void getByClubName(MouseEvent event) {
    }

    void chargeListClub() {
        ClubService cd = new ClubService();

        // Club cs = new Club();
        // cs.setName("All");
        clubsInterface = cd.readAll();
        //clubsInterface.add(cs);

    }

    void chargeListTerrain(String clubName) {
        TerrainService ts = new TerrainService();

        // Club cs = new Club();
        // cs.setName("All");
        terrainsInterface = ts.readByClubName(clubName);
        //clubsInterface.add(cs);

    }

}
