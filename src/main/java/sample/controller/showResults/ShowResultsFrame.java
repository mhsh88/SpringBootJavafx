package sample.controller.showResults;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.base.BaseFrame;

import java.io.IOException;

public class ShowResultsFrame extends Application implements BaseFrame {


    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/showResult/showResults.fxml"));
    Parent root1;
    Stage stage;

    public ShowResultsFrame() {
//        try{
//            root1 = (Parent) fxmlLoader.load();
//         stage = new Stage();
//        stage.setScene(new Scene(root1, 500, 400));
//        stage.setTitle("نتایج محاسبات");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/sample/view/base/logo2.png"));
        primaryStage.getIcons().add(icon);
//        stage = primaryStage;
//        stage.setScene(new Scene(root1, 500, 400));
//        stage.setTitle("نتایج محاسبات");
        show();
    }

    @Override
    public void show() throws IOException {

        stage.close();
//        ShowResultsController.showResult();
        stage.show();

    }

    @Override
    public void close() throws IOException {
        stage.close();
    }
}
