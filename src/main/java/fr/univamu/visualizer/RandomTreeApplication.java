package fr.univamu.visualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RandomTreeApplication extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(RandomTreeApplication.class.getResource("random-tree-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), Controller.WIDTH, Controller.HEIGHT);
    stage.setTitle("Random tree");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}