package synth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Controller controller;
    Synth synth;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("synth.fxml"));

        Parent root = loader.load();
        primaryStage.setTitle("Synth v 0.1");
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.setResizable(false);
        primaryStage.show();





        synth = new Synth();
        controller = loader.getController();
        controller.setSynth(synth);
        controller.initializee();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
