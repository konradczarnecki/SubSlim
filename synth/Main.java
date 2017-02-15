package synth;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    Controller controller;
    Synth synth;

    double offsetX = 0;
    double offsetY = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("synth.fxml"));

        AnchorPane root = loader.load();
        primaryStage.setTitle("Synth v 0.1");
        primaryStage.setScene(new Scene(root, 1200, 740));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        root.setBackground(new Background(new BackgroundFill(Color.valueOf("rgb(51,51,51)"),null,null)));

        controller = loader.getController();

        ImageView back = controller.getBack();

        back.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                offsetX = primaryStage.getX() - event.getScreenX();
                offsetY = primaryStage.getY() - event.getScreenY();
            }
        });

        back.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + offsetX);
                primaryStage.setY(event.getScreenY() + offsetY);
            }
        });

        Synth synth = new Synth();
        controller.setSynth(synth);
        controller.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
