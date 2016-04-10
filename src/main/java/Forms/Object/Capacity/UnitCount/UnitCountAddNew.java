package Forms.Object.Capacity.UnitCount;

import Forms.Service.DialogWindow;
import Service.UnitCountDAOImpl;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity.UnitCountEntity;

/**
 * Класс создает новое окно с полями для ввода данных узла учета, номер и модель и сохраняет узел учета в базе
 */
public class UnitCountAddNew {
    private Stage stage;
    private Scene scene;
    private Group root;
    private String number, model;
    private Label labelNumber, labelModel;
    private TextField textFieldNumber, textFieldModel;
    private Button buttonAddNewUnitCount;
    private UnitCountEntity unitCountEntity;

    public UnitCountAddNew() {
        createFormAddNewUnitCount();
    }

    private void createFormAddNewUnitCount(){
        root = new Group();
        root.getChildren().add(createField());
        root.setAutoSizeChildren(true);
        scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("Добавление нового узла учета");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createField(){
        labelNumber = new Label("Номер узла учета");
        textFieldNumber = new TextField();
        labelModel = new Label("Модель узла учета");
        textFieldModel = new TextField();
        VBox vBoxButton = new VBox(getButtonAddNewUnitCount());
        vBoxButton.setStyle("-fx-padding: 5");
        VBox vBox = new VBox(labelNumber, textFieldNumber, labelModel, textFieldModel, vBoxButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 15");
        return vBox;
    }

    /**
     * После клика на кнопку "Сохранить узел учета", проверяется текстовое поле модели и номер УУ на NotNull.
     * Если все true, то сохраняет новый УУ в Базе
     * @return Button
     */
    private Button getButtonAddNewUnitCount(){
        buttonAddNewUnitCount = new Button("Сохранить узел учета");
        buttonAddNewUnitCount.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            UnitCountDAOImpl unitCountDAO = new UnitCountDAOImpl();
                if (!textFieldNumber.getText().equals("")) {
                    model = textFieldModel.getText();

                    if (!textFieldModel.getText().equals("")) {
                        number = textFieldNumber.getText();
                        unitCountEntity = new UnitCountEntity(number, model);
                        unitCountDAO.saveUnitCount(unitCountEntity);
                        UnitCountConnectToCapacity.getListViewUnitCount();
                    }
                }else{
                    DialogWindow dialogWindow = new DialogWindow("Поля не должны быть пустыми");
                }
        });

        return buttonAddNewUnitCount;
    }


}
