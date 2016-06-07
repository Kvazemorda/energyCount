package Forms.Object.Capacity;

import Service.CapacityObjectDAOImpl;
import Service.TypeResourceDAOImpl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

public class CapacityAddRoot {
    Group root;
    public Stage stage;
    Scene scene;
    BorderPane borderPane;
    VBox vBox;
    Label title, resourceLabel, capacityLabel;
    Button okButton;
    public static PlaceEntity placeEntity;
    public CapacitySourceObjectEntity capacitySourceObjectEntity;
    private static TypeResourceEntity typeResourceEntity;
    public ComboBox<TypeResourceEntity> typeResourceEntityComboBox;
    public ComboBox<CapacitySourceObjectEntity> capacityRoot;

    public CapacityAddRoot(PlaceEntity placeEntity) {
        typeResourceEntityComboBox = new ComboBox<>();
        capacityRoot = new ComboBox<>();
        this.placeEntity = placeEntity;
        typeResourceEntityComboBox = new ComboBox<>();
        capacityRoot = new ComboBox<>();
        title = new Label("Выбери откуда новый источник получает ресурсы");
        title.setFont(Font.font(15));
        resourceLabel = new Label("Тип ресурса основного источника");
        capacityLabel = new Label("Мощность основного источника");
        okButton = new Button("ОК");
        okButton.setStyle("-fx-padding: 5");
        clickOkButton();
        vBox = new VBox(resourceLabel, typeResourceEntityComboBox(), capacityLabel, capacityRoot, okButton);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setAlignment(title, Pos.CENTER);
        borderPane.setAlignment(vBox, Pos.CENTER_LEFT);
        borderPane.setStyle("-fx-padding: 10");

        root = new Group(borderPane);
        scene = new Scene(root, 370, 203);
        stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        stage.show();
    }

    private void clickOkButton(){
        okButton.onActionProperty().setValue(v -> {
            if(capacitySourceObjectEntity != null){

            }
        });
    }
    public ComboBox<TypeResourceEntity> typeResourceEntityComboBox(){
        TypeResourceDAOImpl typeResourceDAO = new TypeResourceDAOImpl();
        typeResourceEntityComboBox.getItems().clear();
        typeResourceEntityComboBox.setItems(FXCollections.observableArrayList(typeResourceDAO.findAllTypeResource()));
        typeResourceEntityComboBox.onActionProperty().setValue(v ->{
            typeResourceEntity = typeResourceEntityComboBox.getSelectionModel().getSelectedItem();
            capacityRoot();
        });

        return typeResourceEntityComboBox;
    }
    public ComboBox<CapacitySourceObjectEntity> capacityRoot(){
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
        capacityRoot.getItems().clear();
        capacityRoot.setItems(FXCollections.observableArrayList(capacityObjectDAO.getCapacityOnPlaceConnectedResource(placeEntity, typeResourceEntity)));
        capacityRoot.onActionProperty().setValue(v ->{
            capacitySourceObjectEntity = capacityRoot.getSelectionModel().getSelectedItem();
        });

        return capacityRoot;
    }

}