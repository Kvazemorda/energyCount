package Forms.Object.Capacity;

import Service.CapacityObjectDAOImpl;
import Service.ContractDAOImpl;
import Service.TypeResourceDAOImpl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

// добавить в грид панель наверно, выбор контракта
    public GridPane createFormAddTypeCapacityToObject(){
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
        gridPane.add(labelTypeResource,1,0);
        gridPane.add(labelCapacity,2,0);
        gridPane.add(labelDescription,3,0);
        gridPane.add(labelSource,4,0);
        gridPane.add(labelConsumer,5,0);
        gridPane.add(contractEntityComboBox(),0,1);
        gridPane.add(createComboBoxResource(),1,1);
        gridPane.add(capacityTextField,2,1);
        gridPane.add(TFDescription,3,1);
        gridPane.add(borderPaneSource,4,1);
        gridPane.add(borderPaneConsumer,5,1);
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

    public ComboBox<ContractEntity> contractEntityComboBox(){
        ContractDAOImpl contractDAO = new ContractDAOImpl();
        ComboBox<ContractEntity> contractEntityComboBox = new ComboBox<>();
        contractEntityComboBox.setItems(FXCollections.observableArrayList(contractDAO.getAllContracts()));
        contractEntityComboBox.onActionProperty().setValue(event ->{
            contractEntity = contractEntityComboBox.getSelectionModel().getSelectedItem();
        });
        return contractEntityComboBox;
    }

    public CheckBox getCheckBoxConsumer() {
        return checkBoxConsumer;
    }

    public void setCheckBoxConsumer(CheckBox checkBoxConsumer) {
        this.checkBoxConsumer = checkBoxConsumer;
    }

    public CheckBox getCheckBoxSource() {
        return checkBoxSource;
    }

    public void setCheckBoxSource(CheckBox checkBoxSource) {
        this.checkBoxSource = checkBoxSource;
    }

    public TextField getCapacityTextField() {
        return capacityTextField;
    }

    public void setCapacityTextField(TextField capacityTextField) {
        this.capacityTextField = capacityTextField;
    }

    public ComboBox<TypeResourceEntity> getTypeResourceEntityComboBox() {
        return typeResourceEntityComboBox;
    }

    public void setTypeResourceEntityComboBox(ComboBox<TypeResourceEntity> typeResourceEntityComboBox) {
        this.typeResourceEntityComboBox = typeResourceEntityComboBox;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public TextField getTFDescription() {
        return TFDescription;
    }

    public void setTFDescription(TextField TFDescription) {
        this.TFDescription = TFDescription;
    }

    public TypeResourceEntity getTypeResourceEntity() {
        return typeResourceEntity;
    }

    public void setTypeResourceEntity(TypeResourceEntity typeResourceEntity) {
        this.typeResourceEntity = typeResourceEntity;
    }

    public PlaceEntity getPlaceEntity() {
        return placeEntity;
    }

    public void setPlaceEntity(PlaceEntity placeEntity) {
        this.placeEntity = placeEntity;
    }

    public ContractEntity getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(ContractEntity contractEntity) {
        this.contractEntity = contractEntity;
    }
}
