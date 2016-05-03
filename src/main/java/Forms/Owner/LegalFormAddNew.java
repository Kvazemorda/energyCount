package Forms.Owner;

import Forms.Service.DialogWindow;
import Service.LegalDAOImpl;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vankor.EnergyDepartment.Owner.LegalFormEntity;

public class LegalFormAddNew {
    private Scene scene;
    private Group group;
    public Stage stage;
    private BorderPane borderPane;
    public VBox baseLayoutVBox;
    private Label
            titleLabel = new Label("Добавление новой юр. формы"),
            nameLabel = new Label("Короткое название юр. формы"),
            fullNameLabel = new Label("Полное название юр. формы");
    Button commitLegalFormButton;
    private TextField nameTF, fullNameTF;

    public LegalFormAddNew() {
        titleLabel.setStyle("-fx-style: 16; -fx-padding: 10");
        commitLegalFormButton = new Button("Сохранить новую юр. форму");
        commitLegalFormButton.setStyle("-fx-padding: 10");
        clickCommitLegalForm();
        nameTF = new TextField();
        fullNameTF = new TextField();
        baseLayoutVBox = new VBox(nameLabel, nameTF, fullNameLabel, fullNameTF, commitLegalFormButton);
        baseLayoutVBox.setSpacing(10);
        baseLayoutVBox.setStyle("-fx-padding: 10");
        borderPane = new BorderPane();
        borderPane.setTop(titleLabel);
        borderPane.setCenter(baseLayoutVBox);
        group = new Group(borderPane);
        scene = new Scene(group, 200, 220);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void clickCommitLegalForm(){
        commitLegalFormButton.onMouseClickedProperty().setValue(v ->{
            if(!nameTF.getText().isEmpty()){
                if(!fullNameTF.getText().isEmpty()){
                    LegalFormEntity legalFormEntity = new LegalFormEntity();
                    legalFormEntity.setName(nameTF.getText());
                    legalFormEntity.setFullName(fullNameTF.getText());
                    LegalDAOImpl legalDAO = new LegalDAOImpl();
                    legalDAO.saveLegalForm(legalFormEntity);
                    OwnerAddNew.setLegalFormEntityComboBox();
                }else {
                    DialogWindow dialogWindow = new DialogWindow("Укажи полное название юр. формы");
                }
            }else{
                DialogWindow dialogWindow = new DialogWindow("Укажи короткое название юр. формы");
            }

        });
    }
}
