package Forms.Object.Capacity.UnitCount.JournalCount;

import Forms.MainForm;
import Forms.Object.ObjectWithResourceConnected;
import Forms.Service.DialogWindow;
import Service.*;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JournalAddNewCountOrValue {

    private ComboBox<PlaceEntity> comboBox = new ComboBox<>();
    private TypeResourceEntity typeResourceEntity;
    private PlaceEntity placeEntity;
    private GridPane gridPane = new GridPane();
    private HBox hBox = new HBox();
    private VBox boxSource = new VBox(),
            boxConsumer = new VBox(),
            vBoxBalance = new VBox();
    public static Set<ValueResourceZeroCapacity> valueResourceZero;
    public static Set<ValueResourceWithoutUnitCount> valueOtherMethod;
    public static Set<ValueResourceWithUnitCount> valueUnitCount;
    public static Label labelSourceValue, labelConsumerValue, labelBalanceResource, labelSource, labelConsumer;
    private ScrollPane scrollPane = new ScrollPane();
    private Button buttonAddNewCount;
    private BorderPane borderPaneSource,borderPaneConsumer;

    public BorderPane createPane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(createListTypeResource());
        borderPane.setTop(topPane());
        borderPane.setAlignment(gridPane, Pos.TOP_CENTER);
        borderPane.setCenter(centerGridPane());
        borderPane.setBottom(vBoxBalance);
        borderPane.setAlignment(vBoxBalance, Pos.BOTTOM_CENTER);

        valueOtherMethod = new HashSet<>();
        valueUnitCount = new HashSet<>();
        valueResourceZero = new HashSet<>();

        changeDate();
        return borderPane;
    }

    //Создаем лист ресурсов
    private ListView<TypeResourceEntity> createListTypeResource(){
        final TypeResourceDAOImpl typeResourceDAO = new TypeResourceDAOImpl();
        final ListView<TypeResourceEntity> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(typeResourceDAO.findAllTypeResource()));
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            typeResourceEntity = listView.getSelectionModel().getSelectedItem();
            valueOtherMethod.clear();
            valueResourceZero.clear();
            valueUnitCount.clear();
            createObjectOnPlaceForm();
        });
        return listView;
    }

    //Создаем комбобокс площадок
    private ComboBox<PlaceEntity> createComboBoxPlaces(){
        PlaceDAOImpl placeDAO = new PlaceDAOImpl();
        final ArrayList<PlaceEntity> placeEntities = (ArrayList) placeDAO.findALLPlace();
        comboBox.setItems(FXCollections.observableArrayList(placeEntities));
        comboBox.getSelectionModel().selectFirst();
        placeEntity = comboBox.getSelectionModel().getSelectedItem();
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            placeEntity = comboBox.getSelectionModel().getSelectedItem();
            createObjectOnPlaceForm();
        });
        return comboBox;
    }

    private GridPane topPane(){
        hBox.getChildren().add(createComboBoxPlaces());
        buttonAddNewCount = new Button("Добавить новые показания");
        gridPane.add(hBox, 1, 1);
        gridPane.add(buttonAddNewCount, 2, 1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        return gridPane;
    }
    //панель с перечнем объектов источников и потребителей
    private ScrollPane centerGridPane(){
        labelBalanceResource = new Label(String.valueOf(ValueResourceWithUnitCount.balanceResource));
        final String cssDefault = "" + "-fx-padding: 5px, 5px;"
                + "-fx-border-color: #b0b4b6;"
                + "-fx-border-insets: 5;"
                + "-fx-border-width: 0;";

        boxSource.setStyle(cssDefault);
        boxConsumer.setStyle(cssDefault);
        vBoxBalance.getChildren().add(new Label("Баланс"));
        vBoxBalance.getChildren().add(labelBalanceResource);
        vBoxBalance.setAlignment(Pos.CENTER_RIGHT);
        vBoxBalance.setStyle("-fx-padding: 10");

        GridPane gridPane1 = new GridPane();
        gridPane1.add(boxSource, 0, 1);
        gridPane1.add(boxConsumer,1,1);
        gridPane1.setMinSize(150, 70);

        scrollPane.setContent(gridPane1);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        return scrollPane;
    }

    //создаем формы объектов с узлами учета и без них, размещаем их в центре
    public void createObjectOnPlaceForm() {
        buttonAddNewCountListener();
        valueOtherMethod.clear();
        valueUnitCount.clear();
        valueResourceZero.clear();
        ValueResourceWithUnitCount.balanceResource = 0;
        ValueResourceWithUnitCount.countValueConsumer = 0;
        ValueResourceWithUnitCount.countValueSource = 0;
        labelSourceValue = new Label();
        labelConsumerValue = new Label();
        boxSource.getChildren().clear();
        boxSource.getChildren().add(createTitleSource());
        boxConsumer.getChildren().clear();
        boxConsumer.getChildren().add(createTitleConsumer());
        ObjectOnPlaceDAOImpl objectOnPlaceDAO = new ObjectOnPlaceDAOImpl();
        //Выбираю лист объектов подключенных к мощностям
        List<ObjectOnPlaceEntity> objectOnPlaceEntities = objectOnPlaceDAO.getObjectOnPlaceConnectedResource(placeEntity);
        for(ObjectOnPlaceEntity objectOnPlaceEntity: objectOnPlaceEntities) {
            ObjectWithResourceConnected objectWithResourceConnected = new ObjectWithResourceConnected(typeResourceEntity, objectOnPlaceEntity);

            borderPaneSource = objectWithResourceConnected.createObjectWithResourceSource();
            if (objectWithResourceConnected.capacitySourceConnect == true){
                boxSource.getChildren().add(borderPaneSource);
            }
//            borderPaneConsumer = objectWithResourceConnected.createObjectWithResourceConsumer();
            if (objectWithResourceConnected.capacityConsumerConnect == true){
                boxConsumer.getChildren().add(objectWithResourceConnected.borderPaneConsumer);
            }
        }
    }

    private GridPane createTitleConsumer(){
        String cssLabel = "-fx-padding: 4px,4px;";
        String cssBorder = "-fx-padding: 5px, 8px;";

        labelConsumer = new Label("Потребитель");
        labelConsumerValue.setStyle(cssLabel);
        labelConsumer.setStyle(cssLabel);
        GridPane consumerGridPane = new GridPane();
        consumerGridPane.add(labelConsumer,0,0);
        consumerGridPane.add(labelConsumerValue,2,0);
        consumerGridPane.setStyle(cssBorder);
        return consumerGridPane;
    }

    private GridPane createTitleSource(){
        String cssLabel = "-fx-padding: 4px,4px;";
        String cssBorder = "-fx-padding: 5px, 8px;";

        labelSource = new Label("Источник");
        labelSource.setStyle(cssLabel);
        labelSourceValue.setStyle(cssLabel);
        GridPane sourceGridPane = new GridPane();
        sourceGridPane.add(labelSource, 0, 0);
        sourceGridPane.add(labelSourceValue,2,0);
        sourceGridPane.setStyle(cssBorder);

        return sourceGridPane;
    }
    private void buttonAddNewCountListener(){
        buttonAddNewCount.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(!labelBalanceResource.getText().equals("0.0")){
                DialogWindow dialogWindow = new DialogWindow("Баланс должен быть равен 0");
            }else{
                JournalUnitCountDAOImpl journalUnitCountDAO = new JournalUnitCountDAOImpl();
                journalUnitCountDAO.writeCountToJournal(valueUnitCount);
                JournalFillDAOImp journalFillDAOImp = new JournalFillDAOImp();
                journalFillDAOImp.writeCountToJournal(valueResourceZero);
                JournalOtherMethodDAOImpl journalOtherMethodDAO = new JournalOtherMethodDAOImpl();
                journalOtherMethodDAO.writeValueToJournal(valueOtherMethod);
            }
        });
    }

    public void changeDate(){
        MainForm.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            createObjectOnPlaceForm();
        });
    }
}
