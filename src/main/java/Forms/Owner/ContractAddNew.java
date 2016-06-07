package Forms.Owner;

import Forms.Object.Capacity.CapacityCreateNew;
import Forms.Owner.PlaneContract.PlaneContractAddNew;
import Forms.Service.DialogWindow;
import Service.ContractDAOImpl;
import Service.OwnerDAOImpl;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.Owner.ContractEntity;
import vankor.EnergyDepartment.Owner.OwnerEntity;
import vankor.EnergyDepartment.Owner.PlaneContractValueEntity;

import java.util.HashSet;
import java.util.Set;


public class ContractAddNew {

    private BorderPane borderPaneBasicForm;
    private PlaneContractAddNew planeContractAddNew;
    private Label labelTitle = new Label("Создать контракт с потребителем"),
            labelNumberContract = new Label("№ Контракта");
    private TextField numberContractTextField;
    private static ComboBox<OwnerEntity> ownerEntityComboBox;
    private Button addOwnerButton, commitContractButton;
    private static OwnerEntity owner;

    public ContractAddNew() {
        labelTitle.setStyle("-fx-padding: 10px; -fx-font-size: 18");
        planeContractAddNew = new PlaneContractAddNew();
        commitContractButton = new Button("Сохранить контракт и план");
        commitContractButton.setStyle("-fx-padding: 15; -fx-font-size: 15");
        clickOnCommitContactButton();
        numberContractTextField = new TextField();
        numberContractTextField.setMaxWidth(250);
        ownerEntityComboBox = new ComboBox<>();
        setOwnerEntityComboBox();
        addOwnerButton = new Button("Доавить потребителя");
        clickOnAddOwnerButton();
        HBox ownerHBox = new HBox(ownerEntityComboBox, addOwnerButton);
        VBox vBoxCenter = new VBox(labelNumberContract, numberContractTextField, ownerHBox,
                planeContractAddNew.createTablePlaneContract(), commitContractButton);
        vBoxCenter.setStyle("-fx-padding: 8px");
        vBoxCenter.setSpacing(3);
        borderPaneBasicForm = new BorderPane();
        borderPaneBasicForm.setTop(labelTitle);
        borderPaneBasicForm.setLeft(vBoxCenter);
    }

    public static void setOwnerEntityComboBox(){
        OwnerDAOImpl ownerDAO = new OwnerDAOImpl();
        ownerDAO.getAllOwner();
        ownerEntityComboBox.setItems(FXCollections.observableArrayList(ownerDAO.getAllOwner()));
        ownerEntityComboBox.onActionProperty().setValue(v ->{
            owner = ownerEntityComboBox.getSelectionModel().getSelectedItem();
        });
    }

    public BorderPane getBorderPaneBasicForm() {
        return borderPaneBasicForm;
    }

    public void setBorderPaneBasicForm(BorderPane borderPaneBasicForm) {
        this.borderPaneBasicForm = borderPaneBasicForm;
    }
    public void clickOnCommitContactButton(){
        commitContractButton.setOnMouseClicked(event ->{
            ContractEntity contractEntity = new ContractEntity();
            if (!numberContractTextField.getText().isEmpty()){
                contractEntity.setContract(numberContractTextField.getText());
                if(!planeContractAddNew.getListPlane().isEmpty()){
                    for(int i = 0; i < planeContractAddNew.getListPlane().size(); i++){
                        planeContractAddNew.getListPlane().get(i).setContractEntity(contractEntity);
                    }
                    Set<PlaneContractValueEntity> set = new HashSet<>(planeContractAddNew.getListPlane());
                    contractEntity.setPlaneContractValueEntitySet(set);
                    if(owner != null){
                        contractEntity.setOwnerEntity(owner);
                        ContractDAOImpl contractDAO = new ContractDAOImpl();
                        contractDAO.commitNewContract(contractEntity);
                        CapacityCreateNew.contractEntityComboBox();
                    }else{
                        DialogWindow dialogWindow = new DialogWindow("Выбери контрагента");
                    }
                }else{
                    DialogWindow dialogWindow = new DialogWindow("Укажи планируемые объемы");
                }
            }else{
                DialogWindow dialogWindow = new DialogWindow("Номер контракта не должен быть пустым");
            }

        });
    }
    public void clickOnAddOwnerButton(){
        addOwnerButton.setOnMouseClicked(event -> {
            OwnerAddNew ownerAddNew = new OwnerAddNew();
        });
    }
}
