package com.codetreatise.config;

import com.codetreatise.bean.station.CityGateStationEntity;
import com.codetreatise.view.FxmlView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private CityGateStationEntity cityGateStationEntity;
    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public SpringFXMLLoader getSpringFXMLLoader() {
        return springFXMLLoader;
    }

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public CityGateStationEntity getCityGateStationEntity() {
        return cityGateStationEntity;
    }

    public void setCityGateStationEntity(CityGateStationEntity cityGateStationEntity) {
        this.cityGateStationEntity = cityGateStationEntity;
    }

    public void switchScene(final FxmlView view) {
        try {
            Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
            show(viewRootNodeHierarchy, view.getTitle());
        }
        catch (Exception e){
            e.printStackTrace();
            switchScene(FxmlView.STATION);
        }
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);
        //scene.getStylesheets().add("/styles/Styles.css");

        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo2.png")));
        primaryStage.setOnCloseRequest(event -> {
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("توجه");
                alert.setHeaderText("اطمینان از خروج");
                alert.setContentText("آیا برای خارج شدن از برنامه اطمینان دارید؟");
                ButtonType okButton = new ButtonType("بله", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("خیر", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(okButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == okButton) {
                    Platform.exit();
                }  else {
                    event.consume();
                }
            }

        });
//        primaryStage.setMaximized(true);

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit("Unable to show scene for title" + title, exception);
        }
    }

    private Scene prepareScene(Parent rootnode) {
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
//        Platform.exit();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
