package Forms.Service;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogWindowYesOrNo{

    private String message;
    protected Group root;
    public static Stage stage;
    protected Scene scene;
    protected BorderPane borderPane;
    protected VBox vBox;
    private Button buttonYes = new Button("ДА"),
            buttonNo = new Button("НЕТ");
    public static int yes = 0;

    public DialogWindowYesOrNo(String message) {
        String cssDialogWindow = "-fx-padding: 10px;";

        Label label = new Label(message);
        HBox hBox = new HBox(buttonNo, new Label(" "), buttonYes);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        vBox = new VBox(label, new Label(), hBox);
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
        stage.initStyle(StageStyle.UTILITY);

        buttonNo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            yes = 0;
            stage.close();
        });
        buttonYes.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            yes = 1;
            stage.close();
        });

        stage.showAndWait();

    }
}
