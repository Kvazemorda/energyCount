package Forms;

import Service.TypeResourceDAOImpl;
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
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;


public class TypeResourceAddNew {
    Group root;
    Stage stage;
    Scene scene;
    BorderPane borderPane;
    VBox vBox;
    Label title, nameLabel;
    TextField nameTF;
    Button addNewTypeResourceButton;

    public TypeResourceAddNew() {
        title = new Label("Добавляем новый тип ресурса");
        title.setFont(Font.font(15));
        nameLabel = new Label("Укажи имя ресурса");
        nameTF = new TextField();
        addNewTypeResourceButton = new Button("Сохранить ресурс");
        addNewTypeResourceButton.setStyle("-fx-padding: 5");
        clickOnAddNewTypeResourceButton();
        vBox = new VBox(nameLabel,nameTF,addNewTypeResourceButton);
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

    private void clickOnAddNewTypeResourceButton(){
        addNewTypeResourceButton.onActionProperty().setValue(v -> {
            if(!nameTF.getText().isEmpty()){
                TypeResourceEntity typeResourceEntity = new TypeResourceEntity(nameTF.getText());
                TypeResourceDAOImpl typeResourceDAO = new TypeResourceDAOImpl();
                typeResourceDAO.commitTypeResource(typeResourceEntity);
            }
        });
    }
}
