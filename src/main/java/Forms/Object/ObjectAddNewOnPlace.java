package Forms.Object;

import Forms.Service.DialogWindow;
import Forms.MainForm;
import Forms.Object.Capacity.CapacityCreateNew;
import Service.CapacityObjectDAOImpl;
import Service.Exception.DoubleSourceConsumerException;
import Service.ObjectOnPlaceDAOImpl;
import Service.PlaceDAOImpl;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ObjectAddNewOnPlace {
    private GridPane gridPane = new GridPane(),
            paneFormAddTypeResourceToEquipment = new GridPane();
    private VBox boxAddTypeResourceToEquipment;
    private Button addFormAddTypeResourceToEquipment, removeFormAddTypeResourceToEquipment;
    private String gridPaneCSS ="-fx-padding: 10;",
            buttonCSS ="-fx-label-padding: 10px;";
    private Map<Integer, CapacityCreateNew> formAddTypeResourceToEquipmentMap;
    private Label nameObject = new Label("Имя нового объекта"),
            placeOnMap = new Label("Позиция на ген. плане"),
            placeLabel = new Label("Площадка");
    private TextField tNameObject = new TextField(),
            tPlaceOnMap = new TextField();
    private Button bAddObject;
    private PlaceEntity placeEntity;
    private ComboBox comboBox;

    public GridPane createFormAddNewObject() {
        String cssLabel = "-fx-padding: 3px,3px,0px,3px;";
        nameObject.setStyle(cssLabel);
        tNameObject.setStyle(cssLabel);
        placeOnMap.setStyle(cssLabel);
        tPlaceOnMap.setStyle(cssLabel);

        GridPane gridPaneAddNewObject = new GridPane();
        gridPaneAddNewObject.setStyle("-fx-hgap: 5px;");
        gridPaneAddNewObject.add(placeLabel, 0, 0);
        gridPaneAddNewObject.add(nameObject, 1, 0);
        gridPaneAddNewObject.add(placeOnMap, 2, 0);
        gridPaneAddNewObject.add(createComboBoxPlace(), 0, 1);
        gridPaneAddNewObject.add(tNameObject, 1, 1);
        gridPaneAddNewObject.add(tPlaceOnMap, 2, 1);
        addNewObject();

        return gridPaneAddNewObject;
    }

    private void addNewObject() {
        bAddObject = new Button("Добавить новый объект");
        bAddObject.setStyle(buttonCSS);
        bAddObject.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int boxSize = boxAddTypeResourceToEquipment.getChildren().size();
            if (boxSize > 0) {
                try {
                    //тестирую поля, чтобы не были пустыми или не в том формате
                    for (int i = 0; i < boxSize; i++) {

                        CapacityCreateNew capacityCreateNew = formAddTypeResourceToEquipmentMap.get(i);
                        double test = Double.parseDouble(capacityCreateNew.getCapacityTextField().getText());
                        if (test == 0 || test != 0) {
                        } else {
                            throw new NumberFormatException();
                        }
                        TypeResourceEntity typeResourceEntity = capacityCreateNew.getTypeResourceEntityComboBox().getValue();
                        if (typeResourceEntity == null) {
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

                    ObjectOnPlaceEntity objectOnPlaceEntity = new ObjectOnPlaceEntity(
                            tNameObject.getText(),
                            tPlaceOnMap.getText(),
                            placeEntity,
                            new Date());

                    if (tNameObject.getText().equals("") || tPlaceOnMap.getText().equals("")) {
                        String message = "Имя объекта и позиция на ГП не должны быть пустыми";
                        DialogWindow dialogWindow = new DialogWindow(message);
                    } else {
                        ObjectOnPlaceDAOImpl objectOnPlaceDAO = new ObjectOnPlaceDAOImpl();
                        objectOnPlaceDAO.saveObjectOnPlace(objectOnPlaceEntity);

                        for (int i = 0; i < boxSize; i++) {
                            CapacityCreateNew capacityCreateNew =
                                    formAddTypeResourceToEquipmentMap.get(i);
                            double capacity = Double.parseDouble(
                                    capacityCreateNew.getCapacityTextField().getText());
                            TypeResourceEntity typeResourceEntity = capacityCreateNew
                                    .getTypeResourceEntityComboBox().getValue();
                            boolean source = capacityCreateNew.getCheckBoxSource().isSelected();
                            boolean consumer = capacityCreateNew
                                    .getCheckBoxConsumer()
                                    .isSelected();
                            String description = capacityCreateNew.getTFDescription().getText();
                            CapacitySourceObjectEntity capacitySourceObjectEntity =
                                    new CapacitySourceObjectEntity(capacity, typeResourceEntity, source,
                                            consumer, objectOnPlaceEntity, MainForm.currentDate, description);

                            CapacityObjectDAOImpl capacityEqEachResourceDAO = new CapacityObjectDAOImpl();
                            capacityEqEachResourceDAO.connectCapacityToObject(capacitySourceObjectEntity);
                        }
                    }
                } catch (NumberFormatException exp) {
                    System.out.println("boxAddTypeResourceToEquipment " +
                            boxAddTypeResourceToEquipment.getChildren().size() +
                            " formAddTypeResourceToEquipmentMap " +
                            formAddTypeResourceToEquipmentMap.size());
                    System.out.println(exp);
                    String message = "В поле \"производительность\" должно быть число";
                    DialogWindow dialogWindow = new DialogWindow(message);

                } catch (NullPointerException e) {
                    System.out.println(e);
                    System.out.println("boxAddTypeResourceToEquipment " +
                            boxAddTypeResourceToEquipment.getChildren().size() +
                            " formAddTypeResourceToEquipmentMap " +
                            formAddTypeResourceToEquipmentMap.size());
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
                    String message = "Наименование объекта и позиция на ген. плане "
                            + "\n"
                            + "не должны быть пустыми";
                    DialogWindow dialogWindow = new DialogWindow(message);
                }
            }

        });
    }

    /**
     * Создается комбобокс площадок
     * @return ComboBox<PlaceEntity>
     */
    private ComboBox<PlaceEntity> createComboBoxPlace() {
        PlaceDAOImpl placeDAO = new PlaceDAOImpl();
        placeDAO.setSession(MainForm.session);
        final ArrayList<PlaceEntity> placeEntities = (ArrayList) placeDAO.findALLPlace();
        comboBox = new ComboBox<PlaceEntity>();
        comboBox.setItems(FXCollections.observableArrayList(placeEntities));
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            placeEntity = (PlaceEntity) comboBox.getSelectionModel().getSelectedItem();
        });
        return comboBox;
    }

    public BorderPane createBorderPane() {
        paneFormAddTypeResourceToEquipment.setStyle("-fx-padding: 5px; -fx-hgap: 5px; -fx-vgap: 5px;");
        gridPaneCSS = "-fx-padding: 10;";
        VBox vBox1 = new VBox();
        vBox1.getChildren().add(createGridPane());
        vBox1.getChildren().add(paneFormAddTypeResourceToEquipment);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox1);
        borderPane.setStyle(gridPaneCSS);
        return borderPane;
    }

    public GridPane createGridPane() {
        gridPane.getChildren().add(createFormAddNewObject());
        gridPane.setStyle(gridPaneCSS);
        connectResourceToCapacity();
        return gridPane;
    }

    public void connectResourceToCapacity(){
        formAddTypeResourceToEquipmentMap = new HashMap<>();
        boxAddTypeResourceToEquipment = new VBox();
        paneFormAddTypeResourceToEquipment.add(boxAddTypeResourceToEquipment, 0, 0);
        boxAddTypeResourceToEquipment.setStyle(ObjectWithResourceConnected.cssDefault);
        addFormAddTypeResourceToEquipment = new Button("+");
        removeFormAddTypeResourceToEquipment = new Button("-");
        HBox hBox = new HBox();
        BorderPane borderPane = new BorderPane();
        hBox.getChildren().add(borderPane);
        borderPane.setLeft(addFormAddTypeResourceToEquipment);
        borderPane.setRight(removeFormAddTypeResourceToEquipment);
        paneFormAddTypeResourceToEquipment.add(borderPane, 0, 1);
        paneFormAddTypeResourceToEquipment.add(bAddObject, 0, 2);
        if (boxAddTypeResourceToEquipment.getChildren().size() == 0) {
            CapacityCreateNew capacityCreateNew = new CapacityCreateNew();
            boxAddTypeResourceToEquipment.getChildren().add(capacityCreateNew.createFormAddTypeCapacityToObject());
            formAddTypeResourceToEquipmentMap.put(boxAddTypeResourceToEquipment.getChildren().size() - 1, capacityCreateNew);
        }
        addFormAddTypeResourceToEquipment.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            CapacityCreateNew capacityCreateNew = new CapacityCreateNew();
            boxAddTypeResourceToEquipment.getChildren().add(capacityCreateNew.createFormAddTypeCapacityToObject());
            formAddTypeResourceToEquipmentMap.put(boxAddTypeResourceToEquipment.getChildren().size() - 1, capacityCreateNew);
        });
        removeFormAddTypeResourceToEquipment.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int i = boxAddTypeResourceToEquipment.getChildren().size();
            if (i > 0) {
                // как блин удалить панель которая была создана циклом???
                // при удалении панели вылетает nullException потомучто я просто удаленую панель объвляю nullом
                boxAddTypeResourceToEquipment.getChildren().remove(i - 1);
                try{
                formAddTypeResourceToEquipmentMap.get(i - 1).setGridPane(null);
                }
                catch (NullPointerException e){
                    System.out.println(e);
                }
                formAddTypeResourceToEquipmentMap.remove(i - 1);
            }
        });
    }

    public GridPane getPaneFormAddTypeResourceToEquipment() {
        return paneFormAddTypeResourceToEquipment;
    }

    public void setPaneFormAddTypeResourceToEquipment(GridPane paneFormAddTypeResourceToEquipment) {
        this.paneFormAddTypeResourceToEquipment = paneFormAddTypeResourceToEquipment;
    }

    public Button getbAddObject() {
        return bAddObject;
    }

    public void setbAddObject(Button bAddObject) {
        this.bAddObject = bAddObject;
    }

    public VBox getBoxAddTypeResourceToEquipment() {
        return boxAddTypeResourceToEquipment;
    }

    public Map<Integer, CapacityCreateNew> getFormAddTypeResourceToEquipmentMap() {
        return formAddTypeResourceToEquipmentMap;
    }

    public TextField gettNameObject() {
        return tNameObject;
    }

    public TextField gettPlaceOnMap() {
        return tPlaceOnMap;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }
}

