package Forms.Owner.PlaneContract;

import Forms.Service.DialogWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.Owner.PlaneContractValueEntity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class PlaneContractAddNew {
    VBox basicLayerVBox;
    HBox insertDataHBox;
    DatePicker startDatePecker, endDatePicker;
    Button addButton, removeButton;
    TextField valueTextField;
    Label labelTitle;
    TableView<PlaneContractValueEntity> planeEntityTableView;
    ObservableList<PlaneContractValueEntity> listPlane = FXCollections.observableArrayList();
    SimpleDateFormat simpleDateFormat;

    public PlaneContractAddNew() {
        labelTitle = new Label("План потребления");
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        TableColumn startDateCol = new TableColumn("Дата начала");
        startDateCol.setPrefWidth(170);
        startDateCol.setResizable(false);
        startDateCol.setCellValueFactory(
                new PropertyValueFactory<PlaneContractValueEntity, Date>("dateStart")
        );
        startDateCol.setCellFactory(column -> {
            return new TableCell<PlaneContractValueEntity, Date>(){
                @Override
                protected void updateItem(Date item, boolean empty){
                    if (item == null || empty){
                        setText(null);
                        setStyle("");
                    }else{
                        setText(simpleDateFormat.format(item));
                    }
                }
            };
        });

        TableColumn endDateCol = new TableColumn("Дата окончания");
        endDateCol.setPrefWidth(170);
        endDateCol.setResizable(false);
        endDateCol.setCellValueFactory(
                new PropertyValueFactory<PlaneContractValueEntity, Date>("dateEnd")
        );
        endDateCol.setCellFactory(column -> {
            return new TableCell<PlaneContractValueEntity, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(simpleDateFormat.format(item));
                    }
                }
            };
        });

        TableColumn value = new TableColumn("Объем потребления");
        value.setPrefWidth(160);
        value.setResizable(false);
        value.setCellValueFactory(
                new PropertyValueFactory<PlaneContractValueEntity, Double>("value")
        );
        planeEntityTableView = new TableView<>();
        planeEntityTableView.setPrefWidth(450);
        planeEntityTableView.maxWidth(450);
        planeEntityTableView.setTooltip(new Tooltip("Указываем плановые объемы по заявке"));
        planeEntityTableView.getColumns().addAll(startDateCol, endDateCol, value);
        planeEntityTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        planeEntityTableView.setItems(listPlane);
        startDatePecker = new DatePicker();
        endDatePicker = new DatePicker();
        valueTextField = new TextField();
        addButton = new Button("Добавить");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            addButtonClicked();
        });
        removeButton = new Button("Удалить");
        removeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            removeButtonClicked();
        });
        insertDataHBox = new HBox(startDatePecker,endDatePicker,valueTextField,addButton,removeButton);

    }
    public VBox createTablePlaneContract(){
        basicLayerVBox = new VBox(labelTitle, planeEntityTableView, insertDataHBox);
        basicLayerVBox.setSpacing(3);
        return basicLayerVBox;
    }

    public void addButtonClicked(){
        double value;
        PlaneContractValueEntity planeContractValueEntity = new PlaneContractValueEntity();
        try{
            value = Double.parseDouble(valueTextField.getText());
            Date dateStart = Date.from(Instant.from(startDatePecker.getValue().atStartOfDay(ZoneId.systemDefault())));
            Date dateEnd = Date.from(Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault())));

            if (dateStart.after(dateEnd)){
                String s = "Дата начала должна быть меньше даты окончания";
                DialogWindow dialogWindow = new DialogWindow(s);
            }else{
                planeContractValueEntity.setValue(value);
                planeContractValueEntity.setDateStart(dateStart);
                planeContractValueEntity.setDateEnd(dateEnd);
                planeContractValueEntity.setValue(value);
                planeEntityTableView.getItems().add(planeContractValueEntity);
            }

        }catch (NumberFormatException e){
            String s = "Планируемый объем должен быть числом";
            DialogWindow dialogWindow = new DialogWindow(s);
        }catch (NullPointerException exp){
            exp.printStackTrace();
            String s = "Поля не должны быть пустыми";
            DialogWindow dialogWindow = new DialogWindow(s);
        }
    }

    public void removeButtonClicked(){
        ObservableList<PlaneContractValueEntity> planeSelected,allPlane;
        allPlane = planeEntityTableView.getItems();
        planeSelected = planeEntityTableView.getSelectionModel().getSelectedItems();
        planeSelected.forEach(allPlane::remove);
    }

    public VBox getBasicLayerVBox() {
        return basicLayerVBox;
    }

    public void setBasicLayerVBox(VBox basicLayerVBox) {
        this.basicLayerVBox = basicLayerVBox;
    }

    public ObservableList<PlaneContractValueEntity> getListPlane() {
        return listPlane;
    }
}
