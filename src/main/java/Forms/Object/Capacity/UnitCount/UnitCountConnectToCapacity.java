package Forms.Object.Capacity.UnitCount;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Forms.Service.DialogWindowYesOrNo;
import Service.ActInstallUnitCountDAOImp;
import Service.CapacityObjectDAOImpl;
import Service.Exception.NextCalibrationIsToday;
import Service.UnitCountDAOImpl;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity.UnitCountEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Класс подключения узла учета к мощности установленной на объекте.
 */
public class UnitCountConnectToCapacity {
    private Stage stage;
    private Scene scene;
    private Group root;
    private GridPane gridPane;
    private ObjectOnPlaceEntity objectOnPlaceEntity;
    private TypeResourceEntity typeResourceEntity;
    private static UnitCountEntity unitCountEntity;
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private ActInstallCountEntity actInstallCountEntity;
    private Button buttonConnectToObject, buttonCreateUnitCount, buttonDeletedUnitCount;
    private VBox vBoxObjectAndCapacity, vBoxLabelInformation, vBoxListUnitCount;
    private Label labelUnitCount, labelObjectName, labelDateNextCalibration, labelDescription, labelFirstCount;
    private DatePicker datePickerNextCalibration;
    private Date dateNextCalibration;
    private TextField textFieldFirstCount, textFieldDescription;
    private ListView<CapacitySourceObjectEntity> listViewCapacity;
    private static ListView<UnitCountEntity> listViewUnitCount;

    public UnitCountConnectToCapacity(ObjectOnPlaceEntity objectOnPlaceEntity, TypeResourceEntity typeResourceEntity) {
        this.objectOnPlaceEntity = objectOnPlaceEntity;
        this.typeResourceEntity = typeResourceEntity;
        listViewCapacity = new ListView<>();
        listViewUnitCount = new ListView<>();
        getListViewUnitCount();
        getListViewCapacity();
        createForm();
    }

    public void createForm(){
        root = new Group();
        root.getChildren().add(createBorderPane());
        root.setStyle("-fx-background-color: #bcbcbc");
        scene = new Scene(root, 700, 458);
        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Подключение узела учета к объекту");
        stage.setScene(scene);
        stage.show();
        getButtonConnectUnitCountToObject();
    }

    /**
     * Создается основная панель
     * @link UnitCount#vBoxObjectAndCapacity - объект и подключенные мощности
     * @link UnitCount#vBoxListUnitCount - список узлов учета
     * @return BorderPane
     */
    private BorderPane createBorderPane(){
        labelObjectName = new Label(objectOnPlaceEntity.getName());
        labelObjectName.setStyle("-fx-background-color: #9fbfec");
        vBoxObjectAndCapacity = new VBox(labelObjectName, listViewCapacity);
        labelUnitCount = new Label("Доступные узлы учета");
        HBox hBox = new HBox(labelUnitCount, getButtonDeletedUnitCount());
        vBoxListUnitCount = new VBox(listViewUnitCount, getButtonCreateUnitCount());
        labelFirstCount = new Label("Начальное показание");
        gridPane = new GridPane();
        gridPane.add(labelObjectName, 0, 0);
        gridPane.add(vBoxObjectAndCapacity, 0, 1);
        gridPane.add(hBox, 1, 0);
        gridPane.add(vBoxListUnitCount, 1, 1);
        gridPane.add(labelFirstCount,2,0);
        gridPane.add(getVBoxLabelInformation(),2,1);
        gridPane.setStyle("-fx-padding:5 ; -fx-hgap: 5; -fx-vgap: 5;");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setStyle("-fx-background-color: #bcbcbc");
        buttonDeletedUnitCount.setAlignment(Pos.CENTER_RIGHT);
        return borderPane;
    }

    private void getListViewCapacity(){
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
        listViewCapacity.setItems(FXCollections.observableArrayList(capacityObjectDAO
                .getCapacityWithoutUnitCount(objectOnPlaceEntity, typeResourceEntity)));
        listViewCapacity.getSelectionModel().selectFirst();
        capacitySourceObjectEntity = listViewCapacity.getSelectionModel().getSelectedItem();
        listViewCapacity.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            capacitySourceObjectEntity = listViewCapacity.getSelectionModel().getSelectedItem();
        });
    }

    public static void getListViewUnitCount(){
        UnitCountDAOImpl unitCountDAO = new UnitCountDAOImpl();
        listViewUnitCount.setItems(FXCollections.observableArrayList(unitCountDAO.getNotConnectUnitCount()));
        listViewUnitCount.getSelectionModel().selectFirst();
        unitCountEntity = listViewUnitCount.getSelectionModel().getSelectedItem();
        listViewUnitCount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            unitCountEntity = listViewUnitCount.getSelectionModel().getSelectedItem();
        });
    }

    private HBox getButtonConnectUnitCountToObject(){
        buttonConnectToObject = new Button("Подключить узел учета");
        buttonConnectToObject.setStyle("-fx-border-color: gray");
        buttonConnectToObject.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            double firstCount = 0;
            String description = "";
            try {
                if(textFieldFirstCount.getText() != ""){
                    firstCount = Double.parseDouble(textFieldFirstCount.getText());
                }
                if (dateNextCalibration.equals(MainForm.currentDate)){
                    throw new NextCalibrationIsToday();
                }
                description = textFieldDescription.getText();
                actInstallCountEntity = new ActInstallCountEntity(MainForm.currentDate,
                        dateNextCalibration, firstCount, new Date(),
                        unitCountEntity, capacitySourceObjectEntity,description);
                ActInstallUnitCountDAOImp actInstallUnitCountDAOImp = new ActInstallUnitCountDAOImp();
                actInstallUnitCountDAOImp.saveActInstallCount(actInstallCountEntity);
            } catch (NumberFormatException e){
                String text = "Начальное покзаание должно быть числом";
                DialogWindow dialogWindow = new DialogWindow(text);
            } catch (NextCalibrationIsToday nextCalibrationIsToday) {
                String text = "Дата следующей поверки должна" + '\n' +
                        " быть больше даты установки";
                DialogWindow dialogWindow = new DialogWindow(text);
            } catch(Exception e){
                String text = "Поля не должны быть пустыми";
                DialogWindow dialogWindow = new DialogWindow(text);
            }
            getListViewUnitCount();
            getListViewCapacity();
            getDatePickerNextCalibration();
            textFieldFirstCount.setText("");
            textFieldDescription.setText("");
        });

        HBox hBox = new HBox(buttonConnectToObject);
        hBox.setStyle("-fx-alignment: center-right; -fx-padding: 5;");
        return hBox;
    }

    private HBox getButtonCreateUnitCount(){
        buttonCreateUnitCount = new Button("Создать новый узел учета");
        buttonCreateUnitCount.setStyle("-fx-border-color: gray");
        buttonCreateUnitCount.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            UnitCountAddNew unitCountAddNew = new UnitCountAddNew();
        });
        HBox hBox = new HBox(buttonCreateUnitCount);
        hBox.setStyle("-fx-alignment: center-right; -fx-padding: 5");
        return hBox;
    }

    private VBox getVBoxLabelInformation(){
        textFieldFirstCount = new TextField();
        labelDateNextCalibration = new Label("Дата следующей поверки");
        labelDescription = new Label("Доп. иформация по подключению");
        textFieldDescription = new TextField();
        vBoxLabelInformation = new VBox(textFieldFirstCount,labelDateNextCalibration,
                getDatePickerNextCalibration(), labelDescription, textFieldDescription, getButtonConnectUnitCountToObject());
        return vBoxLabelInformation;
    }

    private DatePicker getDatePickerNextCalibration(){
        datePickerNextCalibration = new DatePicker(LocalDate.now());
        LocalDate localDate = datePickerNextCalibration.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        dateNextCalibration = Date.from(instant);
        datePickerNextCalibration.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate localDate1 = datePickerNextCalibration.getValue();
            Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            dateNextCalibration = Date.from(instant1);
        });
        datePickerNextCalibration.setMinWidth(194);
        return datePickerNextCalibration;
    }

    private Button getButtonDeletedUnitCount(){
        buttonDeletedUnitCount = new Button("",new ImageView("file:src/main/Icons/korzina.png"));
        buttonDeletedUnitCount.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            DialogWindowYesOrNo dialogWindowYesOrNo = new DialogWindowYesOrNo("Удалить узел учета " +
                    unitCountEntity.getNumber() + "?");
            if(DialogWindowYesOrNo.yes == 1) {
                UnitCountDAOImpl unitCountDAO = new UnitCountDAOImpl();
                unitCountEntity.setDeletedUnitCount(true);
                unitCountDAO.deleteUnitCount(unitCountEntity);
                getListViewUnitCount();
            }
        });
        return buttonDeletedUnitCount;
    }
}
