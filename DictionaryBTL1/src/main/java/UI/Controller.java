package UI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import static module.DictionaryManagement.newWords;
public class Controller implements Initializable{
    @FXML
    private TextField textsearch;
    @FXML
    private TextArea textMeaning;
    @FXML
    private Button buttonSearch;
    @FXML
    private ListView<String> listWords;
    @FXML
    private Pane sound;
    public void hamclick(ActionEvent e){
        String input = new String();
        input = textsearch.getText().toLowerCase();
        Map<String,String> dictionary = new HashMap<>();
        for (int i = 0;i< newWords.numberWords;i++){
            dictionary.put(newWords.words[i].getWord_target() ,newWords.words[i].getWord_explain());
        }
        if (dictionary.containsKey(input)){
            String line = dictionary.get(input);
            line = line.replace("<br/>" , "\n");
            line = line.replace("@" , " ");
            textMeaning.setText(line);
            textsearch.setText(input);
        }
    }
    public void handle(KeyEvent event) throws IOException {
        Map<String,String> dictionary = new HashMap<>();
        for (int i = 0;i< newWords.numberWords;i++){
            dictionary.put(newWords.words[i].getWord_target() ,newWords.words[i].getWord_explain());
        }
        if (event.getCode().equals(KeyCode.ENTER)){
            String input = new String();
            input = textsearch.getText().toLowerCase();
            if (dictionary.containsKey(input)){
                String line = dictionary.get(input);
                line = line.replace("<br/>" , "\n");
                line = line.replace("@" , " ");
                textMeaning.setText(line);
                textsearch.setText(input);
            }
        }
    }
    public void changeSceneAdmin(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/admin.fxml"));
        Parent adminView = loader.load();
        Scene scene = new Scene(adminView);
        adminControler admin = new adminControler();
        stage.setScene(scene);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map<String,String> dictionary = new HashMap<>();
        for (int i = 0;i< newWords.numberWords;i++){
            dictionary.put(newWords.words[i].getWord_target() ,newWords.words[i].getWord_explain());
        }
        if (textsearch.getText().trim().isEmpty()){
            textMeaning.setText("Enter English words in the search box");
        }
        listWords.getItems().addAll(dictionary.keySet());
        listWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String item = listWords.getSelectionModel().getSelectedItem();
                if (dictionary.containsKey(item)){
                    String line = dictionary.get(item);
                    line = line.replace("<br/>" , "\n");
                    line = line.replace("@" , " ");
                    textMeaning.setText(line);
                    textsearch.setText(item);
                }
            }
        });
        textMeaning.setWrapText(true);
        textsearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (textsearch.getText().trim().isEmpty()){
                    textMeaning.setText("Enter English words in the search box");
                }
                ArrayList<String> matchWords = new ArrayList<>(dictionary.keySet());
                String input = textsearch.getText().toLowerCase();
                matchWords.removeIf(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return !s.startsWith(input);
                    }
                });
                listWords.getItems().clear();
                listWords.getItems().addAll(matchWords);
            }
        });
        sound.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Audio audio = Audio.getInstance();
                InputStream sound = null;
                if (textsearch.getText().trim().isEmpty()){
                    try {
                        sound = audio.getAudio("Enter English words in the search box", "en");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        audio.play(sound);
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        sound = audio.getAudio(textsearch.getText(), "en");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        audio.play(sound);
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
