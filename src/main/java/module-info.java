module fr.univamu.randomtree {
  requires javafx.controls;
  requires javafx.fxml;


  opens fr.univamu.visualizer to javafx.fxml;
  exports fr.univamu.visualizer;
}