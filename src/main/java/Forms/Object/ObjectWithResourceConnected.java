package Forms.Object;

import Forms.Object.Capacity.UnitCount.JournalCount.JournalAddNewCountOrValue;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceWithUnitCount;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceWithoutUnitCount;
import Forms.Object.Capacity.UnitCount.JournalCount.ValueResourceZeroCapacity;
import Forms.Object.Capacity.UnitCount.UnitCountConnectToCapacity;
import Service.ActInstallUnitCountDAOImp;
import Service.CapacityObjectDAOImpl;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас создает два BorderPane (Источник и Потребитель) с данными об узлах учета и с кнопкой
 * подключения новых узлов учета.
 */
public class ObjectWithResourceConnected {
    private ObjectOnPlaceEntity objectOnPlaceEntity;
    private TypeResourceEntity typeResourceEntity = new TypeResourceEntity();
    private VBox unitCountSourceBox = new VBox(),
            unitCountConsumerBox = new VBox();
    private Button buttonSource = new Button("+"),
            buttonConsumer = new Button("+"),
            buttonDeleteObject = new Button();
    public boolean
            capacitySourceConnect = false,
            capacityConsumerConnect = false;
    public static String cssDefault;
    private ArrayList<CapacitySourceObjectEntity> sourceObjectEntities, consumerObjectEntities;

    public ObjectWithResourceConnected(TypeResourceEntity typeResourceEntity, ObjectOnPlaceEntity objectOnPlaceEntity) {
        this.typeResourceEntity = typeResourceEntity;
        this.objectOnPlaceEntity = objectOnPlaceEntity;
        addNewUnitCount();
        cssDefault = "" + "-fx-padding: 5px, 5px;"
                + "-fx-border-color: #b0b4b6;"
                + "-fx-border-insets: 5;"
                + "-fx-border-width: 1;"
                + "-fx-border-style: dashed;";
        sourceObjectEntities = new ArrayList<>();
        consumerObjectEntities = new ArrayList<>();
    }

    // Создается бордер узлов учета источника. В центре заполняется unitCountSourceBox
    public BorderPane createObjectWithResourceSource() {
        Label label = new Label(objectOnPlaceEntity.getName());
        label.setStyle("-fx-padding: 7px");
        BorderPane borderPaneTop = new BorderPane();
        borderPaneTop.setLeft(label);
        borderPaneTop.setRight(buttonSource);
        borderPaneTop.setStyle("-fx-padding: 6");
        unitCountSourceBox.getChildren().clear();
        createCapacitySourceConnectedByObject();
        BorderPane objectBorderPane = new BorderPane();
        objectBorderPane.setTop(borderPaneTop);
        objectBorderPane.setCenter(unitCountSourceBox);
        objectBorderPane.setStyle(cssDefault);
        return objectBorderPane;
    }
    public BorderPane createObjectWithResourceConsumer() {
        Label label = new Label(objectOnPlaceEntity.getName());
        label.setStyle("-fx-padding: 5px");
        BorderPane borderPaneTop = new BorderPane();
        borderPaneTop.setLeft(label);
        borderPaneTop.setRight(buttonConsumer);
        borderPaneTop.setStyle("-fx-padding: 6");
        unitCountConsumerBox.getChildren().clear();
        createCapacityConsumerConnectedByObject();
        BorderPane objectBorderPane = new BorderPane();
        objectBorderPane.setTop(borderPaneTop);
        objectBorderPane.setCenter(unitCountConsumerBox);
        objectBorderPane.setStyle(cssDefault);
        return objectBorderPane;
    }
// unitCountSourceBox заполняется узлами учета если на текущую дату есть действующий акт ввода УУ
    public void createCapacitySourceConnectedByObject() {
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
        List<CapacitySourceObjectEntity> capacitySourceObjectEntitySet = capacityObjectDAO.getCapacityResourceConnectedToObjectSource(objectOnPlaceEntity, typeResourceEntity);
        if (capacitySourceObjectEntitySet.size() > 0){
            capacitySourceConnect = true;
        }
        for (CapacitySourceObjectEntity capacitySourceObjectEntity : capacitySourceObjectEntitySet) {
            ActInstallUnitCountDAOImp actInstallUnitCountDAOImp = new ActInstallUnitCountDAOImp();
            ActInstallCountEntity actInstallCountEntity = actInstallUnitCountDAOImp.getActInstallCount(capacitySourceObjectEntity);
            ValueResourceWithUnitCount unitCountItem = new ValueResourceWithUnitCount(capacitySourceObjectEntity);
            unitCountItem.setActInstallCountEntity(actInstallCountEntity);
            if (actInstallCountEntity != null){
                unitCountItem.setUnitCountEntity(actInstallCountEntity.getUnitCountByUnitCount());
                unitCountItem.source = true;
                unitCountSourceBox.getChildren().add(unitCountItem.createPaneUnitCount());
                JournalAddNewCountOrValue.valueUnitCount.add(unitCountItem);
            }else{if(capacitySourceObjectEntity.getCapacity() > 0 ){
                ValueResourceWithoutUnitCount valueResourceWithoutUnitCount = new ValueResourceWithoutUnitCount(capacitySourceObjectEntity);
                valueResourceWithoutUnitCount.source = true;
                unitCountSourceBox.getChildren().add(valueResourceWithoutUnitCount.createForm());
                JournalAddNewCountOrValue.valueOtherMethod.add(valueResourceWithoutUnitCount);
                sourceObjectEntities.add(capacitySourceObjectEntity);
                }else{
                ValueResourceZeroCapacity valueResourceZeroCapacity = new ValueResourceZeroCapacity(capacitySourceObjectEntity);
                valueResourceZeroCapacity.source = true;
                unitCountSourceBox.getChildren().add(valueResourceZeroCapacity.createForm());
                JournalAddNewCountOrValue.valueResourceZero.add(valueResourceZeroCapacity);

            }
            }
        }
    }

    public void createCapacityConsumerConnectedByObject() {
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
        List<CapacitySourceObjectEntity> capacitySourceObjectEntitySet = capacityObjectDAO.getCapacityResourceConnectedToObjectConsumer(objectOnPlaceEntity, typeResourceEntity);
        if (capacitySourceObjectEntitySet.size() > 0){
            capacityConsumerConnect = true;
        }
        for (CapacitySourceObjectEntity capacitySourceObjectEntity : capacitySourceObjectEntitySet) {
            ActInstallUnitCountDAOImp actInstallUnitCountDAOImp = new ActInstallUnitCountDAOImp();
            ActInstallCountEntity actInstallCountEntity = actInstallUnitCountDAOImp.getActInstallCount(capacitySourceObjectEntity);
            ValueResourceWithUnitCount unitCountItem = new ValueResourceWithUnitCount(capacitySourceObjectEntity);
            if (actInstallCountEntity != null){
                unitCountItem.setUnitCountEntity(actInstallCountEntity.getUnitCountByUnitCount());
                unitCountConsumerBox.getChildren().add(unitCountItem.createPaneUnitCount());
                JournalAddNewCountOrValue.valueUnitCount.add(unitCountItem);
            }else{if(capacitySourceObjectEntity.getCapacity() > 0 ){
                ValueResourceWithoutUnitCount valueResourceWithoutUnitCount = new ValueResourceWithoutUnitCount(capacitySourceObjectEntity);
                unitCountConsumerBox.getChildren().add(valueResourceWithoutUnitCount.createForm());
                JournalAddNewCountOrValue.valueOtherMethod.add(valueResourceWithoutUnitCount);
                consumerObjectEntities.add(capacitySourceObjectEntity);
                }else{
                ValueResourceZeroCapacity valueResourceZeroCapacity = new ValueResourceZeroCapacity(capacitySourceObjectEntity);
                valueResourceZeroCapacity.source = false;
                unitCountConsumerBox.getChildren().add(valueResourceZeroCapacity.createForm());
                JournalAddNewCountOrValue.valueResourceZero.add(valueResourceZeroCapacity);
            }
            }
        }
    }

    public void addNewUnitCount(){
        buttonSource.addEventHandler(MouseEvent.MOUSE_CLICKED,event ->{
            UnitCountConnectToCapacity unitCountConnetToCapacity = new UnitCountConnectToCapacity(objectOnPlaceEntity, typeResourceEntity, sourceObjectEntities);
        });
        buttonConsumer.addEventHandler(MouseEvent.MOUSE_CLICKED,event ->{
            UnitCountConnectToCapacity unitCountConnectToCapacity = new UnitCountConnectToCapacity(objectOnPlaceEntity, typeResourceEntity, consumerObjectEntities);
        });
    }


    public ObjectOnPlaceEntity getObjectOnPlaceEntity() {
        return objectOnPlaceEntity;
    }

    public void setObjectOnPlaceEntity(ObjectOnPlaceEntity objectOnPlaceEntity) {
        this.objectOnPlaceEntity = objectOnPlaceEntity;
    }

    public TypeResourceEntity getTypeResourceEntity() {
        return typeResourceEntity;
    }

    public void setTypeResourceEntity(TypeResourceEntity typeResourceEntity) {
        this.typeResourceEntity = typeResourceEntity;
    }

    public VBox getUnitCountSourceBox() {
        return unitCountSourceBox;
    }

    public void setUnitCountSourceBox(VBox unitCountSourceBox) {
        this.unitCountSourceBox = unitCountSourceBox;
    }

    public VBox getUnitCountConsumerBox() {
        return unitCountConsumerBox;
    }

    public void setUnitCountConsumerBox(VBox unitCountConsumerBox) {
        this.unitCountConsumerBox = unitCountConsumerBox;
    }
}
