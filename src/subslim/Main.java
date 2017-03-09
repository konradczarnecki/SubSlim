package subslim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import subslim.synth.Synth;


public class Main extends Application {

    double offsetX = 0;
    double offsetY = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("synth.fxml"));

        AnchorPane root = loader.load();
        primaryStage.setTitle("SubSlim");
        primaryStage.setScene(new Scene(root, 1200, 620));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        root.setBackground(new Background(new BackgroundFill(Color.valueOf("rgb(51,51,51)"),null,null)));

        Controller controller = loader.getController();

        ImageView background = controller.getBackground();

        background.setOnMousePressed(event -> {

                offsetX = primaryStage.getX() - event.getScreenX();
                offsetY = primaryStage.getY() - event.getScreenY();
        });

        background.setOnMouseDragged(event -> {

                primaryStage.setX(event.getScreenX() + offsetX);
                primaryStage.setY(event.getScreenY() + offsetY);
        });

        Synth synth = new Synth();
        controller.setSynth(synth);
        controller.setPrimaryStage(primaryStage);
        controller.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
