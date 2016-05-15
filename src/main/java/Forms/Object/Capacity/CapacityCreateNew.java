package Forms.Object.Capacity;

import Forms.MainForm;
import Forms.TypeResourceAddNew;
import Service.CapacityObjectDAOImpl;
import Service.ContractDAOImpl;
import Service.TypeResourceDAOImpl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.Owner.ContractEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.ArrayList;

/**
 * Класс создает форму подключения новой мощности с определенным типом ресурса к объекту.
 * Выбирается тип ресурса из выпадающего списка, указывается мощность оборудования в час, доп.описание мощности
 */
public class CapacityCreateNew {

    private ComboBox<TypeResourceEntity> typeResourceEntityComboBox;
    private ComboBox<CapacitySourceObjectEntity> capacitySourceObjectEntityComboBox;
    private TextField capacityTextField, TFDescription;
    private CheckBox checkBoxSource, checkBoxConsumer;
    private GridPane gridPane;
    private Label labelTypeResource, labelCapacity, labelSource, labelConsumer, labelDescription, labelContract;
    private TypeResourceEntity typeResourceEntity;
    private PlaceEntity placeEntity;
    private ContractEntity contractEntity;
    private ComboBox<ContractEntity> contractEntityComboBox;
    private Button addNewTypeResourceButton;

    public GridPane createFormAddTypeCapacityToObject(){
        addNewTypeResourceButton = new Button("+");
        clickAddNewTypeResourceButton();
        contractEntityComboBox = new ComboBox<>();
        contractEntityComboBox();
        updatesForms();
        capacityTextField = new TextField();
        capacityTextField.setMaxWidth(90);
        TFDescription = new TextField();
        TFDescription.setMaxWidth(100);
        checkBoxSource = new CheckBox();
        BorderPane borderPaneSource = new BorderPane();
        borderPaneSource.setCenter(checkBoxSource);
        checkBoxConsumer = new CheckBox();
        BorderPane borderPaneConsumer = new BorderPane();
        borderPaneConsumer.setCenter(checkBoxConsumer);
        labelContract = new Label("Договор");
        labelTypeResource = new Label("Тип ресурса");
        labelCapacity = new Label("Мощность в час");
        labelDescription = new Label("Описание");
        labelSource = new Label("Источник");
        labelConsumer = new Label("Потребитель");
        gridPane = new GridPane();
        gridPane.add(labelContract,0,0);
        gridPane.add(labelTypeResource,0,2);
        gridPane.add(labelCapacity,2,0);
        gridPane.add(labelDescription,2,2);
        gridPane.add(labelSource,3,0);
        gridPane.add(labelConsumer,3,2);
        gridPane.add(contractEntityComboBox,0,1);
        gridPane.add(createComboBoxResource(),0,3);
        gridPane.add(addNewTypeResourceButton,1,3);
        gridPane.add(capacityTextField,2,1);
        gridPane.add(TFDescription,2,3);
        gridPane.add(borderPaneSource,3,1);
        gridPane.add(borderPaneConsumer,3,3);
        gridPane.setStyle("-fx-padding:2 ; -fx-hgap: 2; -fx-vgap: 2;");
        gridPane.setAlignment(Pos.TOP_CENTER);
        return gridPane;
    }

    public ComboBox<TypeResourceEntity> createComboBoxResource(){
        typeResourceEntityComboBox = new ComboBox<>();
        typeResourceEntity = new TypeResourceEntity();
        final TypeResourceDAOImpl typeResourceDAO = new TypeResourceDAOImpl();
        ArrayList<TypeResourceEntity> listTypeResource = (ArrayList) typeResourceDAO.findAllTypeResource();
        typeResourceEntityComboBox.setItems(FXCollections.observableArrayList(listTypeResource));
        return typeResourceEntityComboBox;
    }

    public ComboBox<CapacitySourceObjectEntity> createComboBoxCapacity(PlaceEntity placeEntity, TypeResourceEntity typeResourceEntity){
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
        ArrayList<CapacitySourceObjectEntity> capacitySourceObjectEntities = (ArrayList) capacityObjectDAO.getCapacityOnPlaceConnectedResource(placeEntity, typeResourceEntity);
        capacitySourceObjectEntityComboBox = new ComboBox<>();
        capacitySourceObjectEntityComboBox.setItems(FXCollections.observableArrayList(capacitySourceObjectEntities));
        return capacitySourceObjectEntityComboBox;
    }

    public void contractEntityComboBox(){
        ContractDAOImpl contractDAO = new ContractDAOImpl();
        contractEntityComboBox.setItems(FXCollections.observableArrayList(contractDAO.getAllContracts()));
        contractEntityComboBox.onActionProperty().setValue(event -> {
            contractEntity = contractEntityComboBox.getSelectionModel().getSelectedItem();
            System.out.println(contractEntity);
        });
    }

    public void updatesForms(){
        MainForm.updateFormsButton.onActionProperty().setValue(v ->{
            contractEntityComboBox();
        });
    }

    public void clickAddNewTypeResourceButton(){
        addNewTypeResourceButton.onActionProperty().setValue(v ->{
            TypeResourceAddNew typeResourceAddNew = new TypeResourceAddNew();
        });
    }
    public CheckBox getCheckBoxConsumer() {
        return checkBoxConsumer;
    }

    public CheckBox getCheckBoxSource() {
        return checkBoxSource;
    }

    public TextField getCapacityTextField() {
        return capacityTextField;
    }

    public ComboBox<TypeResourceEntity> getTypeResourceEntityComboBox() {
        return typeResourceEntityComboBox;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public TextField getTFDescription() {
        return TFDescription;
    }

    public ContractEntity getContractEntity() {
        return contractEntity;
    }
}
