package sample.controller.stationController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.view.base.BaseFrame;

import java.awt.*;
import java.io.IOException;

public class StationFrame extends Application implements BaseFrame {
//    private static final StationFrame instance = new StationFrame();

    //private constructor to avoid client applications to use constructor

//    public static StationFrame getInstance(){
//        return instance;
//    }

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/station/sample.fxml"));
    Parent root1;
    Stage stage;
    @FXML
    TextField nitrogenTextField = new TextField();

    public StationFrame() {
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/sample/view/base/logo2.png"));
        primaryStage.getIcons().add(icon);

//        getFrame().setIconImage(
//                new ImageIcon(getClass().getClassLoader().getResource("PATH/TO/YourImage.png"))
//        );
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        run(primaryStage);
        show();
    }

    @Override
    public void show() throws IOException {

        stage.close();
        stage.show();

    }

    @Override
    public void close() throws IOException {
        stage.close();

    }

    public void run(Stage stage) throws IOException {
        root1 = (Parent) fxmlLoader.load();
//        stage = new Stage();
        this.stage = stage;
        this.stage.setScene(new Scene(root1, 1000, 500));
        this.stage.setTitle("نرم افزار محاسبه مصرف گاز ایستگاه تقلیل فشار گاز");
        this.stage.getScene().getStylesheets().add(getClass().getResource("/sample/view/station/sample.css").toExternalForm());
//        Image icon = new Image(getClass().getResourceAsStream("/sample/view/base/logo.png"));
//        this.stage.getIcons().add(icon);


    }

    public Stage getStage(){
        return stage;
    }

//    @Override
//    public void stop(){
//
//        System.out.println("Stage is closing");
//        // Save file
//    }

}
