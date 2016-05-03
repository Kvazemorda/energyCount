package Forms.Owner;

import Forms.Service.DialogWindow;
import Service.LegalDAOImpl;
import Service.OwnerDAOImpl;
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
import vankor.EnergyDepartment.Owner.OwnerEntity;


public class OwnerAddNew {
    private Scene scene;
    private Group group;
    public Stage stage;
    private BorderPane borderPane;
    public VBox baseLayoutVBox;
    private Label
            titleLabel = new Label("Добавление нового потребителя"),
            nameLabel = new Label("Имя потребителя"),
            fullNameLabel = new Label("Полное имя потребителя"),
            innLabel = new Label("ИНН/"),
            kppLabel = new Label("КПП"),
            legalFormLabel = new Label("Юридическая форма");
    static ComboBox<LegalFormEntity> legalFormEntityComboBox;
    Button addNewLegalFormButton, commitNewOwnerButton;
    private TextField nameTF, fullNameTF, innTF, kppTF;
    private static LegalFormEntity legalFormEntity;

    public OwnerAddNew() {
        titleLabel.setStyle("-fx-font-size: 16; -fx-padding: 10");
        nameTF = new TextField();
        fullNameTF = new TextField();
        innTF = new TextField();
        kppTF = new TextField();
        HBox hBoxLabel = new HBox(innLabel,kppLabel);
        HBox hBoxTF = new HBox(innTF, new Label(" "),kppTF);
        legalFormEntityComboBox = new ComboBox<>();
        setLegalFormEntityComboBox();
        addNewLegalFormButton = new Button("Добавить новую форму");
        clickAddNewLegalForm();
        HBox hBoxComboBox = new HBox(legalFormEntityComboBox, addNewLegalFormButton);
        commitNewOwnerButton = new Button("Сохранить контрагента");
        commitNewOwnerButton.setStyle("-fx-padding: 15; -fx-font-size: 15");
        clickCommitNewOwnerButton();
        baseLayoutVBox = new VBox(nameLabel, nameTF, fullNameLabel, fullNameTF, hBoxLabel,hBoxTF,legalFormLabel,hBoxComboBox, commitNewOwnerButton);
        baseLayoutVBox.setSpacing(10);
        baseLayoutVBox.setStyle("-fx-padding: 10");
        borderPane = new BorderPane();
        borderPane.setTop(titleLabel);
        borderPane.setCenter(baseLayoutVBox);
        group = new Group(borderPane);
        scene = new Scene(group, 320, 365);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void setLegalFormEntityComboBox(){
        LegalDAOImpl legalDAO = new LegalDAOImpl();
        legalFormEntityComboBox.setItems(FXCollections.observableArrayList(legalDAO.getAllLegalForm()));
        legalFormEntityComboBox.onActionProperty().setValue(event -> {
            legalFormEntity = legalFormEntityComboBox.getSelectionModel().getSelectedItem();
        });
    }
    private void clickCommitNewOwnerButton(){
        commitNewOwnerButton.onMouseClickedProperty().setValue(v ->{
            if(!nameTF.getText().isEmpty()){
                if(!fullNameTF.getText().isEmpty()){
                    if(!innTF.getText().isEmpty() && !kppTF.getText().isEmpty()){
                        if(legalFormEntity != null){
                            OwnerEntity ownerEntity = new OwnerEntity();
                                ownerEntity.setName(nameTF.getText());
                                ownerEntity.setFullName(fullNameTF.getText());
                                ownerEntity.setInn(innTF.getText());
                                ownerEntity.setKpp(kppTF.getText());
                                ownerEntity.setLegalFormEntity(legalFormEntity);
                            OwnerDAOImpl ownerDAO = new OwnerDAOImpl();
                            ownerDAO.saveOwner(ownerEntity);
                            ContractAddNew.setOwnerEntityComboBox();
                        }else {
                            DialogWindow dialogWindow = new DialogWindow("Укажи юридическую форму");
                        }
                    }else{
                        DialogWindow dialogWindow = new DialogWindow("Укажи ИНН и КПП");
                    }
                }else {
                    DialogWindow dialogWindow = new DialogWindow("Укажи полное имя потребитял");
                }
            }else{
                DialogWindow dialogWindow = new DialogWindow("Укажи имя потребителя");
            }
        });
    }

    private void clickAddNewLegalForm(){
        addNewLegalFormButton.onMouseClickedProperty().setValue(v ->{
            LegalFormAddNew legalFormAddNew = new LegalFormAddNew();
        });
    }
}
