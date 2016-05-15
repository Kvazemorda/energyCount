package Forms.Object;

import Service.RegionDAOImpl;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.RegionEntity;

public class RegionAddNew {
    Group root;
    Stage stage;
    Scene scene;
    BorderPane borderPane;
    VBox vBox;
    Label title, nameLabel;
    TextField nameTF;
    Button addNewRegionButton;

    public RegionAddNew() {
        title = new Label("Добавляем новый регион");
        title.setFont(Font.font(15));
        nameLabel = new Label("Укажи имя региона");
        nameTF = new TextField();
        addNewRegionButton = new Button("Сохранить регион");
        addNewRegionButton.setStyle("-fx-padding: 5");
        clickOnAddNewRegionButton();
        vBox = new VBox(nameLabel,nameTF,addNewRegionButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(vBox);
        borderPane.setAlignment(title, Pos.CENTER);
        borderPane.setAlignment(vBox, Pos.CENTER);
        borderPane.setStyle("-fx-padding: 10");

        root = new Group(borderPane);
        scene = new Scene(root, 235, 130);
        stage = new Stage();
        stage.setScene(scene);
        stage.isAlwaysOnTop();
        stage.show();
    }

    private void clickOnAddNewRegionButton(){
        addNewRegionButton.onActionProperty().setValue(v -> {
            if(!nameTF.getText().isEmpty()){
                RegionEntity regionEntity = new RegionEntity(nameTF.getText());
                RegionDAOImpl regionDAO = new RegionDAOImpl();
                regionDAO.commitRegion(regionEntity);
                PlaceAddNewOnRegion.addRegionComboBox();
            }
        });
    }
}
