package sample.controller.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.base.BaseFrame;

import java.io.IOException;

public class RunFrame extends Application implements BaseFrame {

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/run/run.fxml"));
    Parent root1;
    Stage stage;

    public RunFrame() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/sample/view/base/logo2.png"));
        primaryStage.getIcons().add(icon);
        try {
            root1 = (Parent) fxmlLoader.load();
            stage = primaryStage;
            stage.setScene(new Scene(root1, 500, 400));
            stage.setTitle("اطلاعات ران ها");

            RunController runController = (RunController) fxmlLoader.getController();
            runController.setStage(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        show();
    }

    @Override
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
    public void close() {
        stage.close();
    }
}
