package UI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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

    /**
     *Tìm chuỗi con chung dài nhất
     * @param str1
     * @param str2
     * @return
     */
    public static String getLongestCommonSubstring(String str1, String str2) {
        //Note this longest[][] is a standard auxialry memory space used in Dynamic
        //programming approach to save results of subproblems.
        //These results are then used to calculate the results for bigger problems
        int[][] longest = new int[str2.length() + 1][str1.length() + 1];
        int min_index = 0, max_index = 0;

        //When one string is of zero length, then longest common substring length is 0
        for(int idx = 0; idx < str1.length() + 1; idx++) {
            longest[0][idx] = 0;
        }

        for(int idx = 0; idx < str2.length() + 1; idx++) {
            longest[idx][0] = 0;
        }

        for(int i = 0; i <  str2.length(); i++) {
            for(int j = 0; j < str1.length(); j++) {

                int tmp_min = j, tmp_max = j, tmp_offset = 0;

                if(str2.charAt(i) == str1.charAt(j)) {
                    //Find length of longest common substring ending at i/j
                    while(tmp_offset <= i && tmp_offset <= j &&
                            str2.charAt(i - tmp_offset) == str1.charAt(j - tmp_offset)) {

                        tmp_min--;
                        tmp_offset++;

                    }
                }
                //tmp_min will at this moment contain either < i,j value or the index that does not match
                //So increment it to the index that matches.
                tmp_min++;

                //Length of longest common substring ending at i/j
                int length = tmp_max - tmp_min + 1;
                //Find the longest between S(i-1,j), S(i-1,j-1), S(i, j-1)
                int tmp_max_length = Math.max(longest[i][j], Math.max(longest[i+1][j], longest[i][j+1]));

                if(length > tmp_max_length) {
                    min_index = tmp_min;
                    max_index = tmp_max;
                    longest[i+1][j+1] = length;
                } else {
                    longest[i+1][j+1] = tmp_max_length;
                }


            }
        }
        return str1.substring(min_index, max_index >= str1.length() - 1 ? str1.length() - 1 : max_index + 1);
    }

    /**
     *Ham click search
     * @param e
     */
    public void hamclick(ActionEvent e){
        String input = new String();
        int dem = 0;
        String tempWord = new String();
        input = textsearch.getText().toLowerCase();
        double lengInput = input.length() * 0.2;
        Map<String,String> dictionary = new HashMap<>();
        for (int i = 0;i< newWords.numberWords;i++){
            dictionary.put(newWords.words[i].getWord_target() ,newWords.words[i].getWord_explain());
        }
        if (dictionary.containsKey(input)){
            String line = dictionary.get(input);
            line = line.replace("<br/>" , "\n");
            line = line.replace("@" , " ");
            textMeaning.setText(line);
        }
        else {
            for(int i= 0 ;i < newWords.numberWords;i++){
                if(newWords.words[i].getWord_target().length() > input.length()-lengInput && newWords.words[i].getWord_target().length() < input.length()+lengInput){
                    int temp = getLongestCommonSubstring(input,newWords.words[i].getWord_target()).length();
                    if(temp > dem){
                        dem = temp;
                        tempWord = getLongestCommonSubstring(input,newWords.words[i].getWord_target());
                    }
                }
            }

            textsearch.setText(tempWord);
        }
    }

    /**
     * Ham nhan enter
     * @param event
     * @throws IOException
     */
    public void handle(KeyEvent event) throws IOException {
        int dem = 0;
        String tempWord = new String();
        Map<String,String> dictionary = new HashMap<>();
        for (int i = 0;i< newWords.numberWords;i++){
            dictionary.put(newWords.words[i].getWord_target() ,newWords.words[i].getWord_explain());
        }
        if (event.getCode().equals(KeyCode.ENTER)){
            String input = new String();
            input = textsearch.getText().toLowerCase();
            double lengInput = input.length() * 0.2;
            if (dictionary.containsKey(input)){
                String line = dictionary.get(input);
                line = line.replace("<br/>" , "\n");
                line = line.replace("@" , " ");
                textMeaning.setText(line);
            }
            else {
                for(int i= 0 ;i < newWords.numberWords;i++){
                    if(newWords.words[i].getWord_target().length() > input.length()-lengInput && newWords.words[i].getWord_target().length() < input.length()+lengInput){
                        int temp = getLongestCommonSubstring(input,newWords.words[i].getWord_target()).length();
                        if(temp > dem){
                            dem = temp;
                            tempWord = getLongestCommonSubstring(input,newWords.words[i].getWord_target());
                        }
                    }
                }
                textsearch.setText(tempWord);
            }
        }
    }

    /**
     * Ham thay doi man hinh
     * @param e
     * @throws IOException
     */
    public void changeSceneAdmin(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/admin.fxml"));
        Parent adminView = loader.load();
        Scene scene = new Scene(adminView);
        adminControler admin = new adminControler();
        stage.setScene(scene);
    }

    /**
     * Hàm initialize
     * @param url
     * @param resourceBundle
     */
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
        /**
         * click vào listview
         */
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
        /**
         * Bắt sự kiện nhập từng từ để gợi ý
         */
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
        /**
         * âm thanh
         */
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
