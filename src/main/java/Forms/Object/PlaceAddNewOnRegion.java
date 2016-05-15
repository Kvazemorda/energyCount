package Forms.Object;

import Service.PlaceDAOImpl;
import Service.RegionDAOImpl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.RegionEntity;

public class PlaceAddNewOnRegion {
    Group root;
    Stage stage;
    Scene scene;
    BorderPane borderPane;
    VBox vBox;
    Label title, nameLabel, regionLabel;
    TextField nameTF;
    Button addNewRegionButton, commitPlaceButton;
    public static ComboBox<RegionEntity> regionEntityComboBox;
    public static RegionEntity regionEntity;

    public PlaceAddNewOnRegion() {
        regionEntity = new RegionEntity();
        regionEntityComboBox = new ComboBox<>();
        title = new Label("Добавляем новую площадку");
        title.setFont(Font.font(15));
        nameLabel = new Label("Укажи имя площадки");
        nameTF = new TextField();
        regionLabel = new Label("Выбери регион");
        addNewRegionButton = new Button("+");
        clickOnAddNewRegion();
        HBox hBox = new HBox(addRegionComboBox(), addNewRegionButton);
        hBox.setSpacing(5);
        commitPlaceButton = new Button("Сохранить площадку");
        commitPlaceButton.setStyle("-fx-padding: 5");
        clickOnCommitPlaceButton();
        vBox = new VBox(nameLabel, nameTF, regionLabel, hBox, commitPlaceButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setAlignment(title, Pos.CENTER);
        borderPane.setAlignment(vBox, Pos.CENTER);
        borderPane.setStyle("-fx-padding: 10");

        root = new Group(borderPane);
        scene = new Scene(root, 230, 203);
        stage = new Stage();
        stage.setScene(scene);
        stage.isAlwaysOnTop();
        stage.show();
    }

    private void clickOnCommitPlaceButton(){
        commitPlaceButton.onActionProperty().setValue(v -> {
            if(!nameTF.getText().isEmpty()&& regionEntity != null){
                PlaceEntity placeEntity = new PlaceEntity(regionEntity, nameTF.getText());
                PlaceDAOImpl placeDAO = new PlaceDAOImpl();
                placeDAO.commitPlace(placeEntity);
                ObjectAddNewOnPlace.createComboBoxPlace();
            }
        });
    }
    public static ComboBox<RegionEntity> addRegionComboBox(){
        RegionDAOImpl regionDAO = new RegionDAOImpl();
        regionEntityComboBox.getItems().clear();
        regionEntityComboBox.setItems(FXCollections.observableArrayList(regionDAO.getAllRegion()));
        regionEntityComboBox.onActionProperty().setValue(v ->{
            regionEntity = regionEntityComboBox.getSelectionModel().getSelectedItem();
        });

        return regionEntityComboBox;
    }
    private void clickOnAddNewRegion(){
        addNewRegionButton.onActionProperty().setValue(v ->{
            RegionAddNew regionAddNew = new RegionAddNew();
        });

    }
}
