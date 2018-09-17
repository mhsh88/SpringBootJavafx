package sample.controller.afterHeater;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.base.BaseFrame;

import java.io.IOException;

public class AfterHeaterFrame extends Application implements BaseFrame {

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/afterHeater/afterHeater.fxml"));
    Parent root1;
    Stage stage;

    public AfterHeaterFrame() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/sample/view/base/logo2.png"));
        primaryStage.getIcons().add(icon);
        root1   = (Parent) fxmlLoader.load();
            stage = primaryStage;

            stage.setScene(new Scene(root1, 500, 400));
            stage.setTitle("خط لوله بعد از گرم کن");

        stage = primaryStage;
        AfterHeaterController controller = (AfterHeaterController) fxmlLoader.getController();
        controller.setStage(stage);

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

    public void close() throws IOException{
        stage.close();
    }
}
