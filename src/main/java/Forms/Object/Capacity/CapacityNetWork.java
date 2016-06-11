package Forms.Object.Capacity;

import Service.CapacityObjectDAOImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.ObjectOnPlaceEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.TypeResourceEntity;

/**
 * Форма подключения мощностей к источнику. У одно источника может быть несколько потребитлей, как и у одного потребителя
 * может быть несколько источников
 */
public class CapacityNetWork {
    private Label sourceCapacityLabel, connectedCapacityLabel, availableCapacityLabel;
    private ComboBox<CapacitySourceObjectEntity> sourceCapacityComboBox;
    private ComboBox<ObjectOnPlaceEntity> objectComboBox;
    private ComboBox<TypeResourceEntity> typeResourceComboBox;
    private ListView<CapacitySourceObjectEntity> connectedCapacityList, availableCapacityList;
    private BorderPane borderPane;
    private Button connectButton, disconnectButton;
    private HBox listsHBox, topHBox;
    private VBox sourceListVBox, connectedListVBox, buttonVBox, availableListVBox;

    public CapacityNetWork() {
        objectComboBox = new ComboBox<>();
        typeResourceComboBox = new ComboBox<>();
        topHBox = new HBox(objectComboBox, typeResourceComboBox);
        topHBox.setAlignment(Pos.CENTER);
        topHBox.setSpacing(10);
        topHBox.setPadding(new Insets(10,0,0,0));

        sourceCapacityLabel = new Label("Источник");
        sourceCapacityComboBox = new ComboBox<>();
        sourceListVBox = new VBox(sourceCapacityLabel, sourceCapacityComboBox);
        sourceListVBox.setSpacing(5);

        connectedCapacityLabel = new Label("Подключенные потребители");
        connectedCapacityList = new ListView<>();
        connectedListVBox = new VBox(connectedCapacityLabel, connectedCapacityList);
        connectedListVBox.setSpacing(5);

        connectButton = new Button("<");
        disconnectButton = new Button(">");
        buttonVBox = new VBox(new Label(),connectButton, disconnectButton);
        buttonVBox.setSpacing(5);

        availableCapacityLabel = new Label("Доступные потребители");
        availableCapacityList = new ListView<>();
        availableListVBox = new VBox(availableCapacityLabel, availableCapacityList);
        availableListVBox.setSpacing(5);

        listsHBox = new HBox(sourceListVBox, connectedListVBox, buttonVBox, availableListVBox);
        listsHBox.setSpacing(10);
        listsHBox.setAlignment(Pos.BASELINE_CENTER);
        listsHBox.setPadding(new Insets(30,0,0,0));

        borderPane = new BorderPane();
        borderPane.setTop(topHBox);
        borderPane.setCenter(listsHBox);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void createComboBox(){
        CapacityObjectDAOImpl capacityObjectDAO = new CapacityObjectDAOImpl();

    }
}
