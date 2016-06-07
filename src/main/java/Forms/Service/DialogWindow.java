package Forms.Service;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Всплывающее окно, сообщает пользователю о не верно указанных данных при работе с приложениями
 */
public class DialogWindow {
    private String message;
    protected Group root;
    public Stage stage;
    public Scene scene;
    protected BorderPane borderPane;
    protected VBox vBox;

    public DialogWindow(String message) {
        this.message = message;
        createDialogWindow();
    }

    protected void createDialogWindow(){
        String cssDialogWindow = "-fx-padding: 25px;";

        Label label = new Label(message);

        vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle(cssDialogWindow);

        root = new Group();
        root.setStyle(cssDialogWindow);
        root.getChildren().add(vBox);
        root.minHeight(130);

        scene = new Scene(root);

        stage = new Stage();
        stage.resizableProperty().setValue(false);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

}
