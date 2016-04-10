package Forms.Object.Capacity.UnitCount;

import Forms.Service.DialogWindow;
import Forms.MainForm;
import Service.ActInstallUnitCountDAOImp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class UnitCountUnInstall {
    private Stage stage;
    private Scene scene;
    private Group root;
    private Label title, numberLabel, modelLabel, objectLabel, dateLabel;
    private String number, model, object;
    private TextField textFieldLastCount;
    private Button buttonUnInstall;
    public static String cssBorder, cssLabel;
    private ActInstallCountEntity actInstallCountEntity;

    public UnitCountUnInstall(ActInstallCountEntity actInstallCountEntity) {
        this.actInstallCountEntity = actInstallCountEntity;
        createBorderPane();
    }

    public void createFormUnInstallUnitCount(){
        root = new Group();
        scene = new Scene(root,300,300);
        stage = new Stage();
        stage.setTitle("Снятие узла учета");
        stage.setScene(scene);
        root.getChildren().add(createBorderPane());
        stage.show();
    }

    private BorderPane createBorderPane(){
        numberLabel = new Label(number);
        modelLabel = new Label(model);
        objectLabel = new Label(object);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        dateLabel = new Label(simpleDateFormat.format(MainForm.currentDate));
        cssLabel = "-fx-padding: 3px,3px,0px,3px;";

        VBox box = new VBox();
        title = new Label("Дата снятия");
        buttonUnInstall = new Button("Снять узел учета");
        textFieldLastCount = new TextField();
        cssBorder = "-fx-padding: 10px, 12px; -fx-background-color: #f4f4f4;";
        Label labelLastCount = new Label("Конечное показание");
        box.getChildren().add(objectLabel);
        box.getChildren().add(modelLabel);
        box.getChildren().add(numberLabel);
        box.getChildren().add(labelLastCount);
        box.getChildren().add(textFieldLastCount);
        box.getChildren().add(title);
        box.getChildren().add(dateLabel);
        box.getChildren().add(new Label());
        box.getChildren().add(buttonUnInstall);

        title.setStyle(cssLabel);
        buttonUnInstall.setStyle(cssLabel);
        labelLastCount.setStyle(cssLabel);
        numberLabel.setStyle(cssLabel);
        modelLabel.setStyle(cssLabel);
        objectLabel.setStyle(cssLabel);

        box.setStyle(cssBorder);
        dateLabel.setStyle(cssLabel);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(box);
        borderPane.setStyle(cssBorder);
        borderPane.setMinSize(300,300);
        buttonUnInstallListener();
        return borderPane;
    }

    private void buttonUnInstallListener(){
        buttonUnInstall.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ActInstallUnitCountDAOImp actInstallUnitCountDAOImp = new ActInstallUnitCountDAOImp();
            try{
                if (textFieldLastCount.getText() != "") {
                    double lastCount = Double.parseDouble(textFieldLastCount.getText());
                    actInstallCountEntity.setDateUnInstall(MainForm.currentDate);
                    actInstallCountEntity.setLastCountValue(lastCount);
                    actInstallUnitCountDAOImp.unInstallUnitCount(actInstallCountEntity);
                    MainForm.journalAddNewCountOrValue.createObjectOnPlaceForm();
                    stage.close();
                }
            }
            catch (NumberFormatException e){
                String text = "Конечное показание должно быть число";
                DialogWindow dialogWindow = new DialogWindow(text);
            }
        });
    }

    public void setCssBorder(String cssBorder) {
        this.cssBorder = cssBorder;
    }

    public void setCssLabel(String cssLabel) {
        this.cssLabel = cssLabel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public static String getCssBorder() {
        return cssBorder;
    }

    public static String getCssLabel() {
        return cssLabel;
    }

    public ActInstallCountEntity getActInstallCountEntity() {
        return actInstallCountEntity;
    }

    public void setActInstallCountEntity(ActInstallCountEntity actInstallCountEntity) {
        this.actInstallCountEntity = actInstallCountEntity;
    }
}
