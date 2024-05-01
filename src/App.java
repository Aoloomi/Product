import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;

import java.sql.DriverManager;


@Log4j
public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("App Started");
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("view/ProductForm.fxml"))
        );
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product");
        primaryStage.show();
    }
}