package Forms.Owner;

import Service.LegalDAOImpl;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vankor.EnergyDepartment.Owner.LegalFormEntity;


public class OwnerAddNew {
    private Scene scene;
    private Group group;
    public Stage stage;
    private BorderPane borderPane;
    public VBox baseLayoutVBox;
    private Label nameLabel = new Label("Имя контрагента"),
    fullNameLabel = new Label("Полное имя контрагента"),
            innLabel = new Label("ИНН/"),
            kppLabel = new Label("КПП"),
            legalFormLabel = new Label("Юридическая форма");
    ComboBox<LegalFormEntity> legalFormEntityComboBox;
    Button addNewLegalFormButton, commitNewOwnerButton;
    private TextField nameTF, fullNameTF, innTF, kppTF;
    private LegalFormEntity legalFormEntity;

    public OwnerAddNew() {
        nameTF = new TextField();
        fullNameTF = new TextField();
        innTF = new TextField();
        kppTF = new TextField();
        HBox hBoxLabel = new HBox(innLabel,kppLabel);
        HBox hBoxTF = new HBox(innTF, new Label(" "),kppTF);
        legalFormEntityComboBox = new ComboBox<>();
        setLegalFormEntityComboBox();
        addNewLegalFormButton = new Button("Добавить новую форму");
        HBox hBoxComboBox = new HBox(legalFormEntityComboBox, addNewLegalFormButton);
        commitNewOwnerButton = new Button("Сохранить контрагента");
        commitNewOwnerButton.setStyle("-fx-padding: 15; -fx-font-size: 15");
        baseLayoutVBox = new VBox(nameLabel, nameTF, hBoxLabel,hBoxTF,legalFormLabel,hBoxComboBox, commitNewOwnerButton);
        baseLayoutVBox.setSpacing(10);
        baseLayoutVBox.setStyle("-fx-padding: 10");
        borderPane = new BorderPane();
        borderPane.setCenter(baseLayoutVBox);
        group = new Group(borderPane);
        scene = new Scene(group, 400, 310);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void setLegalFormEntityComboBox(){
        LegalDAOImpl legalDAO = new LegalDAOImpl();
        legalFormEntityComboBox.setItems(FXCollections.observableArrayList(legalDAO.getAllLegalForm()));
        legalFormEntityComboBox.onActionProperty().setValue(event -> {
            legalFormEntity = legalFormEntityComboBox.getSelectionModel().getSelectedItem();
        });
    }
}
