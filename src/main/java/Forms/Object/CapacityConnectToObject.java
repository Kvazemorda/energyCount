package Forms.Object;

import Forms.MainForm;
import Forms.Object.Capacity.CapacityCreateNew;
import Forms.Service.DialogWindow;
import Forms.Service.DialogWindowYesOrNo;
import Service.CapacityObjectDAOImpl;
import Service.Exception.DoubleSourceConsumerException;
import Service.ObjectOnPlaceDAOImpl;
import Service.PlaceDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hibernate.PropertyValueException;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.PlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс создает панель со списком объектов и списком подключенных к нему мощностей.
 * @see CapacityConnectToObject#createFormTypeCapacity() - метод создает форму для подключения
 * новых мощностей к выбранному из списка объекту
 */
public class CapacityConnectToObject {
    private BorderPane borderPane = new BorderPane();
    private Label resourceConnectLabel = new Label("Подключенные ресурсы к объекту");
    private Button connectResourceButton = new Button("Подключить к объекту ресурс"),
            disconnectResourceButton = new Button("Отключить ресурс от объекта"),
            deleteObjectButton;
    private ListView<CapacitySourceObjectEntity> capacityObjectEntityListView = new ListView<>();
    private ListView<ObjectOnPlaceEntity> listObjectOnPlace = new ListView<>();
    private ComboBox<PlaceEntity> comboBoxPlace = new ComboBox<>();
    private GridPane gridPane = new GridPane();
    private PlaceEntity placeEntity = new PlaceEntity();
    private ObjectOnPlaceEntity objectOnPlaceEntity = new ObjectOnPlaceEntity();
    private String cssLabel = "-fx-padding: 3px,3px,3px,3px;";
    private HashMap<Integer,CapacityCreateNew> mapCapacityForConnect;
    private CapacitySourceObjectEntity capacitySourceObjectEntity = new CapacitySourceObjectEntity();

    /**
     * Создается основной бордер этого класса слева добавляется лист объектов, вверху список площадок
     * в центре добавляется панель в виде таблицы, где первый столбец список подклченных мощностей и кнопка отключения
     * мощности. Вторрым столбцом создается форма для подключения новых мощностей
     * @return BorderPane
     */
    public BorderPane createCapacityConnectToObject(){
        resourceConnectLabel.setStyle(cssLabel);
        listObjectOnPlace.setStyle(cssLabel);
        capacityObjectEntityListView.setStyle(cssLabel);

        VBox vBox = new VBox();
        vBox.getChildren().add(resourceConnectLabel);
        vBox.getChildren().add(capacityObjectEntityListView);
        vBox.setStyle(cssLabel);
        gridPane.add(vBox,0,0);
        gridPane.add(disconnectResourceButton,0, 2);
        gridPane.add(createFormTypeCapacity(),1,0);
        gridPane.setStyle("-fx-hgap: 5px;");

        HBox hBox = new HBox(comboBoxPlace, getButtonDeletedObject());
        borderPane.setLeft(listObjectOnPlace);
        borderPane.setTop(hBox);
        borderPane.setCenter(gridPane);
        borderPane.setAlignment(gridPane, Pos.CENTER_RIGHT);

        createComboBoxPlace();
        createListObjectOnPlace();
        disconnectCapacityFromObject();
        createListConnectedCapacity();
        changeDate();
        return borderPane;
    }

    /**
     * Создается выпадающий список с доступными площадками.
     * @see PlaceDAOImpl#findALLPlace() - запрос на выбор всех доступных площадок
     */
    private void createComboBoxPlace(){
        PlaceDAOImpl placeDAO = new PlaceDAOImpl();
        comboBoxPlace.setItems(FXCollections.observableArrayList(placeDAO.findALLPlace()));
        comboBoxPlace.getSelectionModel().selectFirst();
        placeEntity = comboBoxPlace.getValue();
        comboBoxPlace.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlaceEntity>() {
            public void changed(ObservableValue<? extends PlaceEntity> observable, PlaceEntity oldValue, PlaceEntity newValue) {
                placeEntity = newValue;
                createListObjectOnPlace();
            }
        });
    }

    /**
     * Создается лист подключенных мощностей к объекту
     * @see CapacityObjectDAOImpl#getCapacityConnectedToObject(vankor.EnergyDepartment.ObjectOnPlaceEntity) -
     * запрос на выбор всех подключенных мощностей к объекту
     */
    private void createListConnectedCapacity(){
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
        //очищаю лист перед повторным заполнением при вызове метода
        capacityObjectEntityListView.getItems().clear();
        capacityObjectEntityListView.setItems(FXCollections.observableArrayList(
                capacityObjectDAO.getCapacityConnectedToObject(objectOnPlaceEntity)));
        capacityObjectEntityListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue)->{
            try {
                capacitySourceObjectEntity = capacityObjectEntityListView.getSelectionModel().getSelectedItem();
            }catch (NullPointerException e){
                System.out.println(e);
            }
        });
    }

    /**
     * Создает список всех объектов на площадке
     * @See ObjectOnPlaceDAOImpl#findAllObjectOnPlace(PlaceEntity) - выбираю все объеткы установленные на площадке
     */
    private void createListObjectOnPlace(){
        ObjectOnPlaceDAOImpl objectOnPlaceDAO = new ObjectOnPlaceDAOImpl();
        //очищаю лист перед повторным заполнением при вызове метода
        listObjectOnPlace.getItems().clear();
        listObjectOnPlace.setItems(FXCollections.observableArrayList(objectOnPlaceDAO.findAllObjectOnPlace(placeEntity)));
        listObjectOnPlace.getSelectionModel().selectFirst();
        objectOnPlaceEntity = listObjectOnPlace.getSelectionModel().getSelectedItem();
        listObjectOnPlace.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try{
                objectOnPlaceEntity = listObjectOnPlace.getSelectionModel().getSelectedItem();
                /**
                 * После выбора объекта повторно создается лист подключенных к нему мощностей
                 */
                createListConnectedCapacity();
            }catch (NullPointerException e){
                System.out.println(e);
            }
        });
    }

    /**
     * Кнопка отключения мощности от объекта.
     * @link CapacityObjectEntity#setDateUnInstall(Date) - К объекту Мощность добавляется его дата отключения
     * @link CapacityObjectDAOImpl#disconnectTypeREsourceFromObject - запрос в базу на сохранение в базе даты отключения мощности
     */
    private void disconnectCapacityFromObject(){
        disconnectResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if(!capacityObjectEntityListView.getSelectionModel().isEmpty()){
                CapacitySourceObjectEntity capacitySourceObjectEntity = capacityObjectEntityListView.getSelectionModel().getSelectedItem();
                capacitySourceObjectEntity.setDateUnInstall(MainForm.currentDate);
                CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
                capacityObjectDAO.disconnectCapacityFromObject(capacitySourceObjectEntity);
                /**
                 * После сохранения даты отключения мощности от объекта, обновляется лист подключенных мощностей
                 */
                createListConnectedCapacity();
            }
        });
    }

    /**
     * Создание панели с подключение мощности от различных типов ресурсов
     * @return GridPane
     */
    private GridPane createFormTypeCapacity() {
        GridPane gridPaneFormAddTypeResourceByObject = new GridPane();
        /**
         * Создается карта int, CapacityCreateNew. При нажатии на +, добавляется новая форма подключения новой мощности.
         * Запихал все в мапу, чтобы при удалении формы приваратить ее в null. Другой способ пока не придумал.
         * Если форму не переводить в null, то вылетают ошибки что не заполнены пустые поля в удаленых формах.
         */
        mapCapacityForConnect = new HashMap<>();
        VBox boxAddCapacityToObject = new VBox();

        Button buttonAddFormCapacityToObject = new Button("+");
        Button buttonRemoveFormCapacityToObject = new Button("-");
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(buttonAddFormCapacityToObject);
        borderPane.setRight(buttonRemoveFormCapacityToObject);
        gridPaneFormAddTypeResourceByObject.add(boxAddCapacityToObject, 0, 0);
        gridPaneFormAddTypeResourceByObject.add(borderPane, 0, 1);
        gridPaneFormAddTypeResourceByObject.add(connectResourceButton, 0, 2);
        gridPaneFormAddTypeResourceByObject.setStyle(ObjectWithResourceConnected.cssDefault);
        /**
         * При первичном создании панели добавления мощностей, если в боксе нет ни одной формы для добавления мощности к
         * объекту, то создается новая форма добавления мощности.
         */
        if (boxAddCapacityToObject.getChildren().size() == 0) {
            CapacityCreateNew capacityCreateNew = new CapacityCreateNew();
            boxAddCapacityToObject.getChildren().add(capacityCreateNew.createFormAddTypeCapacityToObject());
            mapCapacityForConnect.put(boxAddCapacityToObject.getChildren().size() - 1, capacityCreateNew);
        }
        buttonAddFormCapacityToObject.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CapacityCreateNew capacityCreateNew = new CapacityCreateNew();
            boxAddCapacityToObject.getChildren().add(capacityCreateNew.createFormAddTypeCapacityToObject());
            mapCapacityForConnect.put(boxAddCapacityToObject.getChildren().size() - 1, capacityCreateNew);
        });
        buttonRemoveFormCapacityToObject.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int i = boxAddCapacityToObject.getChildren().size();
            if (i > 0) {
                /**
                 * При нажантии кнопки на "-" если количество форм больше 0, то удаляется последняя созданнная форма
                 */
                // как блин удалить панель которая была создана циклом???
                // при удалении панели вылетает nullException потомучто я просто удаленую панель объвляю nullом
                boxAddCapacityToObject.getChildren().remove(i-1);
                try{
                    mapCapacityForConnect.get(i - 1).setGridPane(null);
                }
                catch (NullPointerException e){
                    System.out.println(e);
                }
                mapCapacityForConnect.remove(i - 1);
            }
        });
        clickOnButtonConnectToResource();
        return gridPaneFormAddTypeResourceByObject;
    }

    /**
     * Нажатием на кнопку "Подключить к объекту ресурс" ресурс добавляется в базу с датой указанной в клендаре формы
     */
    public void clickOnButtonConnectToResource(){
        connectResourceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int boxSize = mapCapacityForConnect.size();
            if (boxSize > 0) {
                try {
                    //тестирую поля, чтобы не были пустыми или не в том формате
                    for(Map.Entry<Integer,CapacityCreateNew> pair: mapCapacityForConnect.entrySet()){
                        CapacityCreateNew capacityCreateNew = pair.getValue();

                        double test = Double.parseDouble(capacityCreateNew.getCapacityTextField().getText());
                        if (test == 0 || test != 0) {
                        } else {
                            throw new NumberFormatException();
                        }
                        if (capacityCreateNew.getTypeResourceEntityComboBox().getSelectionModel().isEmpty()) {
                            throw new NullPointerException();
                        }
                        boolean source = capacityCreateNew.getCheckBoxSource().isSelected();
                        boolean consumer = capacityCreateNew.getCheckBoxConsumer().isSelected();
                        if (source == false && consumer == false) {
                            throw new NullPointerException();
                        }
                        if (source == true && consumer == true) {
                            throw new DoubleSourceConsumerException();
                        }
                        if (capacityCreateNew.getTFDescription().getText().equals("")){
                            throw new NullPointerException();
                        }
                    }
                    for(Map.Entry<Integer,CapacityCreateNew> pair: mapCapacityForConnect.entrySet()){
                        CapacityCreateNew capacityCreateNew = pair.getValue();
                            double capacity = Double.parseDouble(capacityCreateNew.getCapacityTextField()
                                    .getText());
                            TypeResourceEntity typeResourceEntity = capacityCreateNew.getTypeResourceEntityComboBox()
                                    .getValue();
                            boolean source = capacityCreateNew.getCheckBoxSource().isSelected();
                            boolean consumer = capacityCreateNew.getCheckBoxConsumer().isSelected();
                            String description = capacityCreateNew.getTFDescription().getText();
                            CapacitySourceObjectEntity capacitySourceObjectEntity =
                                    new CapacitySourceObjectEntity(capacity, typeResourceEntity, source,
                                            consumer, objectOnPlaceEntity, MainForm.currentDate, description);
                        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();
                        capacityObjectDAO.connectCapacityToObject(capacitySourceObjectEntity);
                    }
                    createListConnectedCapacity();
                } catch (NumberFormatException exp) {
                    System.out.println(exp);
                    String message = "В поле \"производительность\" должно быть число";
                    DialogWindow dialogWindow = new DialogWindow(message);
                } catch (NullPointerException e) {
                    System.out.println(e);
                    String message = "Поля не должно быть пустыми";
                    DialogWindow dialogWindow = new DialogWindow(message);
                } catch (DoubleSourceConsumerException e2) {
                    String message = "Один тип ресурса может быть либо источником либо потребителем";
                    DialogWindow dialogWindow = new DialogWindow(message);
                } catch (PropertyValueException e) {
                    System.out.println(e);
                    String message = "Укажи площадку на которой установлен объект";
                    DialogWindow dialogWindow = new DialogWindow(message);
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    String message = "Укажи объект к которму добавляешь ресурс ";
                    DialogWindow dialogWindow = new DialogWindow(message);
                }
            }
        });
    }

    /**
     * Изменение даты календаря MainForm.datePicker, обновляет список подклченных мощностей на текущую дату
     */
    public void changeDate(){
        MainForm.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            createListConnectedCapacity();
        });
    }

    private Button getButtonDeletedObject(){
        deleteObjectButton = new Button("",new ImageView("file:src/main/Icons/korzina.png"));
        deleteObjectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            DialogWindowYesOrNo dialogWindowYesOrNo = new DialogWindowYesOrNo("Удалить объект " +
                    objectOnPlaceEntity.getName() + "?");
            if(DialogWindowYesOrNo.yes == 1){
                ObjectOnPlaceDAOImpl objectOnPlaceDAO = new ObjectOnPlaceDAOImpl();
                objectOnPlaceEntity.setDeleteObject(true);
                objectOnPlaceDAO.deleteObjectOnPlace(objectOnPlaceEntity);
                createListObjectOnPlace();
                DialogWindowYesOrNo.yes = 0;
            }
        });

        return deleteObjectButton;
    }

}