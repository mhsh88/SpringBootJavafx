package sample.controller.heaterController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.base.BaseFrame;

import java.io.IOException;

public class HeaterFrame extends Application implements BaseFrame {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/heater/heater.fxml"));
    Parent root1;
    Stage stage;

    public HeaterFrame() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/sample/view/base/logo2.png"));
        primaryStage.getIcons().add(icon);
        try {
            root1 = (Parent) fxmlLoader.load();
            stage = primaryStage;
            stage.setScene(new Scene(root1, 600 , 400));
            stage.setTitle("اطلاعات گرم کن");
            HeaterController heaterController = (HeaterController) fxmlLoader.getController();
            heaterController.setStage(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        show();
    }


    public void show() throws IOException {
        if(root1 == null & stage == null){
            try {
                start(new Stage());
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stage.close();
        stage.show();
    }

    @Override
    public void close() throws IOException {
        stage.close();
    }
}
