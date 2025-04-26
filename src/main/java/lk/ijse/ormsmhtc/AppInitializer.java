package lk.ijse.ormsmhtc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/AdminHomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SMHTC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        initializeDatabase();
        launch(args);
    }

    private static void initializeDatabase() {

    }

}
