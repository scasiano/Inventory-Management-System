package imsGUI;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class SplashScreenController {
    public static void loadSplashScreen(Stage primaryStage, VBox splashVBox) {
        Stage splashStage = new Stage();
        splashStage.setResizable(false);
        splashStage.initStyle(StageStyle.UNDECORATED);
        Scene splashScene = new Scene(splashVBox, 450, 300);
        splashStage.setScene(splashScene);
        splashStage.show();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2.5), splashVBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0), splashVBox);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeIn.play();
        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });
        fadeOut.setOnFinished((e) -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            splashStage.close();
            ImsGui.showLogin(primaryStage);
        });
        ImsGui.startProcessing();
    }
}
