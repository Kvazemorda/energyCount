package Forms.Object.Capacity;

import Service.CapacityObjectDAOImpl;
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
    private Label labelTypeResource, labelCapacity, labelSource, labelConsumer, labelDescription;
    private TypeResourceEntity typeResourceEntity;
    private PlaceEntity placeEntity;


    public GridPane createFormAddTypeCapacityToObject(){
        capacityTextField = new TextField();
        capacityTextField.setMaxWidth(100);
        TFDescription = new TextField();
        checkBoxSource = new CheckBox();
        BorderPane borderPaneSource = new BorderPane();
        borderPaneSource.setCenter(checkBoxSource);
        checkBoxConsumer = new CheckBox();
        BorderPane borderPaneConsumer = new BorderPane();
        borderPaneConsumer.setCenter(checkBoxConsumer);
        labelTypeResource = new Label("Тип ресурса");
        labelCapacity = new Label("Мощность в час");
        labelDescription = new Label("Описание");
        labelSource = new Label("Источник");
        labelConsumer = new Label("Потребитель");
        gridPane = new GridPane();
        gridPane.add(labelTypeResource,0,0);
        gridPane.add(labelCapacity,1,0);
        gridPane.add(labelDescription,2,0);
        gridPane.add(labelSource,3,0);
        gridPane.add(labelConsumer,4,0);
        gridPane.add(createComboBoxResource(),0,1);
        gridPane.add(capacityTextField,1,1);
        gridPane.add(TFDescription,2,1);
        gridPane.add(borderPaneSource,3,1);
        gridPane.add(borderPaneConsumer,4,1);
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
}
