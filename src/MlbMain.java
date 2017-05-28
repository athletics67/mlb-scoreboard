import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by craigcalvert on 5/23/17.
 */

public class MlbMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./MlbView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("MLB Scoreboard");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

} // end MlbMain
