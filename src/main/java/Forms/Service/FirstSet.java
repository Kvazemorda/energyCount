package Forms.Service;
import Forms.MainForm;
import Service.Messages.PropertyMsg;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FirstSet{
    public Stage stage;
    public Scene scene;
    public Group root;
    private GridPane gridPane;
    private Label urlLabel, userLabel, passwordLabel, recipientLabel, connectedLabel;
    private TextField urlTf, userTf, passwordTf, recipientTf;
    private Button saveSetButton, checkConnect;
    private BorderPane borderPane;
    private VBox boxButton;

    public FirstSet() {
        createGrid();
        root = new Group(borderPane);
        scene = new Scene(root, 380, 190);
        stage = new Stage();
        stage.setScene(scene);
        stage.titleProperty().setValue("Настройки подключения к почте");
        stage.alwaysOnTopProperty();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void createGrid(){
        gridPane = new GridPane();
        urlLabel = new Label("URL: ");
        userLabel = new Label("User: ");
        passwordLabel = new Label("Password: ");
        recipientLabel = new Label("Recipient: ");
        urlTf = new TextField();
        urlTf.minWidth(300);
        urlTf.setText(PropertyMsg.url);
        userTf = new TextField();
        userTf.minWidth(300);
        userTf.setText(PropertyMsg.user);
        passwordTf = new TextField();
        passwordTf.setText(PropertyMsg.password);
        passwordTf.minWidth(300);
        recipientTf = new TextField();
        recipientTf.setText(PropertyMsg.recipientEmail);
        recipientTf.minWidth(300);
        connectedLabel = new Label();
        gridPane.add(urlLabel,0,0);
        gridPane.add(userLabel,0,1);
        gridPane.add(passwordLabel,0,2);
        gridPane.add(recipientLabel,0,3);
        gridPane.add(connectedLabel,0,4);
        gridPane.minWidth(250);
        gridPane.add(urlTf,1,0);
        gridPane.add(userTf,1,1);
        gridPane.add(passwordTf,1,2);
        gridPane.add(recipientTf,1,3);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.getColumnConstraints().add(new ColumnConstraints(80));
        gridPane.getColumnConstraints().add(new ColumnConstraints(280));
        gridPane.setPadding(new Insets(5, 5, 5, 5));


        saveSetButton = new Button("OK");
        clickSaveButton();
        checkConnect = new Button("Проверить соединение");
        clickCheckConnect();
        boxButton = new VBox(checkConnect, saveSetButton);
        boxButton.setSpacing(7);
        boxButton.setAlignment(Pos.BASELINE_RIGHT);
        borderPane = new BorderPane();
        borderPane.setLeft(gridPane);
        borderPane.setBottom(boxButton);
        borderPane.setAlignment(boxButton, Pos.BASELINE_RIGHT);
    }
    private void clickSaveButton(){
        saveSetButton.setOnMouseClicked(Event -> {
        });
    }

    private void clickCheckConnect(){
        checkConnect.setOnMouseClicked(Event ->{
            //проверка на подключение, не уверен в функции
            if(MainForm.myExchangeService.service.isPreAuthenticate()){
                connectedLabel.setText("Почта подключена");
            }
        });
    }
}
