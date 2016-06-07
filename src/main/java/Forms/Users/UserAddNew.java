package Forms.Users;

import Forms.Service.DialogWindow;
import Service.PlaceDAOImpl;
import Service.UsersDAOImp;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.PropertyValueException;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UsersEntity;

import java.beans.PropertyVetoException;


public class UserAddNew {
    public Group root;
    public Scene scene;
    public Stage stage;
    public HBox buttonHBox;
    public BorderPane borderPane;
    public Label nameLabel, mailLabel, userNamePCLabel, placeLabel;
    public TextField nameTf, mailTf, userNamePCTf;
    public Button saveNewUserButton;
    public ComboBox<PlaceEntity> placeComboBox;
    public PlaceEntity placeEntity;
    public GridPane gridPane;

    public UserAddNew() {
        nameLabel = new Label("Имя пользователя:");
        nameTf = new TextField();
        mailLabel = new Label("Почта пользователя:");
        mailTf = new TextField();
        userNamePCLabel = new Label("Имя пользователя Windows:");
        userNamePCTf = new TextField();
        placeLabel = new Label("Место работы:");
        placeComboBox = new ComboBox();
        selectComboBox();
        saveNewUserButton = new Button("Сохранить нового \n пользователя");
        buttonHBox = new HBox(saveNewUserButton);
        buttonHBox.setPadding(new Insets(10,0,0,0));
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        clickSaveButton();

        gridPane = new GridPane();
        gridPane.add(nameLabel,0,0);
        gridPane.add(mailLabel,0,1);
        gridPane.add(userNamePCLabel,0,2);
        gridPane.add(placeLabel,0,3);

        gridPane.add(nameTf,1,0);
        gridPane.add(mailTf,1,1);
        gridPane.add(userNamePCTf,1,2);
        gridPane.add(placeComboBox,1,3);
        gridPane.setHgap(7);
        gridPane.setVgap(7);
        gridPane.setPadding(new Insets(5,0,0,5));

        borderPane = new BorderPane();
        borderPane.setLeft(gridPane);
        borderPane.setBottom(buttonHBox);
        borderPane.setAlignment(buttonHBox, Pos.CENTER_RIGHT);

        root = new Group(borderPane);
        scene = new Scene(root, 325, 190);
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.isAlwaysOnTop();
        stage.setTitle("Добавление нового пользователя");
        stage.showAndWait();
    }

    public void clickSaveButton(){
        saveNewUserButton.setOnMouseClicked(value ->{
            try {
                UsersEntity usersEntity = new UsersEntity(nameTf.getText(), mailTf.getText(), placeEntity, userNamePCTf.getText());
                UsersDAOImp usersDAOImp = new UsersDAOImp();
                usersDAOImp.saveNewUser(usersEntity);
            }catch(PropertyValueException e){
                DialogWindow dialogWindow = new DialogWindow("Не все поля заполнены");
            }
        });
    }

    public void selectComboBox(){
        PlaceDAOImpl placeDAO = new PlaceDAOImpl();
        placeComboBox.getItems().clear();
        placeComboBox.setItems(FXCollections.observableArrayList(placeDAO.findALLPlace()));
        placeComboBox.onActionProperty().setValue(v -> {
            placeEntity =  placeComboBox.getSelectionModel().getSelectedItem();
        });
        }
}
