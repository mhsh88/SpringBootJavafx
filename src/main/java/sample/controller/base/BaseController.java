package sample.controller.base;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class BaseController implements Initializable {
    private Stage stage;

    public abstract void setOnShow();

    public static void showAlert(String title, String info, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(info);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        stage.addEventHandler(WindowEvent.WINDOW_SHOWING, new  EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent window)
            {
                //Your code
                setOnShow();
            }
        });
        this.stage = stage;
    }

}