package Forms.Owner.PlaneContract;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.Owner.PlaneContractValueEntity;

import java.util.Date;

public class PlaneContractAddNew {
    VBox basicLayerVBox;
    Label labelTitle = new Label("План потребления");
    TableView<PlaneContractValueEntity> placeEntityTableView;

    public PlaneContractAddNew(){

        TableColumn startDateCol = new TableColumn("Дата начала");
        startDateCol.setPrefWidth(150);
        startDateCol.setResizable(false);
        startDateCol.setCellValueFactory(
                new PropertyValueFactory<PlaneContractValueEntity,TextField>("dateStart")
        );
        TableColumn endDateCol = new TableColumn("Дата окончания");
        endDateCol.setPrefWidth(150);
        endDateCol.setResizable(false);
        endDateCol.setCellValueFactory(
                new PropertyValueFactory<PlaneContractValueEntity, Date>("dateEnd")
        );
        TableColumn value = new TableColumn("Объем потребления");
        value.setPrefWidth(150);
        value.setResizable(false);
        value.setCellValueFactory(
                new PropertyValueFactory<PlaneContractValueEntity, Double>("value")
        );
        ObservableList<PlaneContractValueEntity> data = FXCollections.observableArrayList();
        for (int i = 0; i < 3; i++){
            data.add(new PlaneContractValueEntity());
        }

        placeEntityTableView = new TableView<>();
        placeEntityTableView.setPrefWidth(450);
        placeEntityTableView.setCursor(Cursor.TEXT);
        placeEntityTableView.setTooltip(new Tooltip("Указываем плановые объемы по заявке"));
        placeEntityTableView.getColumns().addAll(startDateCol, endDateCol, value);
        placeEntityTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        placeEntityTableView.setItems(data);


    }

    public VBox createTablePlaneContract(){
        basicLayerVBox = new VBox(labelTitle, placeEntityTableView);


        return basicLayerVBox;
    }
}
