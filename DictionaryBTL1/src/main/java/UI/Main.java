package UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;

import static module.DictionaryManagement.insertFromFile;
public class Main extends Application {
    private static final File addWord = new File("src/main/java/Data/dictionaries.txt");
    public void start(Stage primaryStage) throws Exception{
        insertFromFile(addWord);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        Scene scene = new Scene(root,800,600);
        scene.getStylesheets().add(getClass().getResource("/Style/style.css").toExternalForm());
        primaryStage.setTitle("Dictionary");
        primaryStage.getIcons().add(new Image("https://previews.123rf.com/images/jacekkita/jacekkita1709/jacekkita170900003/85778225-logo-online-dictionary-concept-laptop-as-book.jpg"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

