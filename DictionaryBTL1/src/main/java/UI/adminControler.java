package UI;
import module.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static module.DictionaryManagement.dictionaryExportToFile;
import static module.DictionaryManagement.newWords;
public class adminControler implements Initializable {
    @FXML
    private TableView<Word> table;
    @FXML
    private TableColumn<Word , String>  englishWord;
    @FXML
    private TableColumn<Word , String> vietnamWords;
    @FXML
    private TextField textEnglish;
    @FXML
    private TextField textVietnam;
    private ObservableList<Word> wordsList;

    private static final File addWord = new File("src/main/java/Data/dictionaries.txt");
    private void alertAdd(ActionEvent event){
    }
    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/sample.fxml"));
        Parent adminView = loader.load();
        Scene scene = new Scene(adminView);
        stage.setScene(scene);
    }
    public void addWords(ActionEvent e) throws IOException {
        if (textEnglish.getText() != null){
            Word wordsnew = new Word();
            for (int  i = 0 ;i < newWords.numberWords ;i++){
                if (textEnglish.getText().toLowerCase().equals(newWords.words[i].getWord_target())){
                    Alert add = new Alert(Alert.AlertType.ERROR);
                    Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
                    add.setTitle("Notify");
                    add.setContentText("The word you want to add is already in the dictionary!");
                    add.setHeaderText(null);
                    add.showAndWait();
                    break;
                }
                else {
                    if (!textEnglish.getText().equals(newWords.words[i].getWord_target())&& i==newWords.numberWords-1){
                        wordsnew.setWord_target(textEnglish.getText());
                        wordsnew.setWord_explain(textVietnam.getText());
                        wordsList.add(wordsnew);
                        newWords.numberWords++;
                        newWords.words[newWords.numberWords-1] = new Word();
                        newWords.words[newWords.numberWords-1].setWord_target(textEnglish.getText());
                        newWords.words[newWords.numberWords-1].setWord_explain(textVietnam.getText());
                        dictionaryExportToFile(addWord);
                        Alert add = new Alert(Alert.AlertType.CONFIRMATION);
                        Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
                        add.setTitle("Notify");
                        add.setContentText("Added new words");
                        add.setHeaderText(null);
                        add.showAndWait();
                        textEnglish.setText(null);
                        textVietnam.setText(null);

//                        System.out.println(newWords.words[newWords.numberWords-1].getWord_target());
                        break;
                    }
                }
            }
        }
        else {
            Alert add = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
            add.setTitle("Notify");
            add.setContentText("Adding new words has not been successful!");
            add.setHeaderText(null);
            add.showAndWait();
        }
    }
    public void deleteWords(ActionEvent e) throws IOException {
        Word selected = table.getSelectionModel().getSelectedItem();
            wordsList.remove(selected);
            int index = 0 ;
            for (int i = 0 ;i < newWords.numberWords;i++){
                if(newWords.words[i].getWord_target().equals(selected.getWord_target())){
                    index = i;
                }
            }
            if(index != -1){
                for (int i = index;i < newWords.numberWords;i++){
                    newWords.words[i] = new Word();
                    newWords.words[i] = newWords.words[i+1];
                }
                newWords.words[newWords.numberWords] = null;
                newWords.numberWords--;
            }
            dictionaryExportToFile(addWord);
            Alert add = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
            add.setTitle("Notify");
            add.setContentText("Deleted word successfully!");
            add.setHeaderText(null);
            add.showAndWait();
    }
    public void editWords(ActionEvent event) throws IOException {
        if (textEnglish.getText() != null){
            Word wordsnew = new Word();
            for (int  i = 0 ;i < newWords.numberWords;i++){
                if (textEnglish.getText().toLowerCase().equals(newWords.words[i].getWord_target())){
                    wordsList.remove(newWords.words[i]);
                    int index = 0 ;
                    for (int j = 0 ;j < newWords.numberWords;j++){
                        if(newWords.words[i].getWord_target().equals(newWords.words[j].getWord_target())){
                            index = j;
                        }
                    }
                    if(index != -1){
                        for (int j = index;j < newWords.numberWords;j++){
                            newWords.words[j] = new Word();
                            newWords.words[j] = newWords.words[j+1];
                        }
                        newWords.words[newWords.numberWords] = null;
                        newWords.numberWords--;
                    }
                    wordsnew.setWord_target(textEnglish.getText());
                    wordsnew.setWord_explain(textVietnam.getText());
                    wordsList.add(wordsnew);
                    newWords.numberWords++;
                    newWords.words[newWords.numberWords-1] = new Word();
                    newWords.words[newWords.numberWords-1].setWord_target(textEnglish.getText());
                    newWords.words[newWords.numberWords-1].setWord_explain(textVietnam.getText());
                    dictionaryExportToFile(addWord);
                    Alert add = new Alert(Alert.AlertType.CONFIRMATION);
                    Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
                    add.setTitle("Notify");
                    add.setContentText("Successful word correction");
                    add.setHeaderText(null);
                    add.showAndWait();
                    textEnglish.setText(null);
                    textVietnam.setText(null);
                    break;
                }
                else {
                    if (!textEnglish.getText().equals(newWords.words[i].getWord_target())&& i==newWords.numberWords-1){
                        Alert add = new Alert(Alert.AlertType.ERROR);
                        Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
                        add.setTitle("Notify");
                        add.setContentText("The word you want to edit is not in the dictionary!");
                        add.setHeaderText(null);
                        add.showAndWait();
                        break;
                    }
                }
            }
        }
        else {
            Alert add = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) add.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("../image/notify.png").toString()));
            add.setTitle("Notify");
            add.setContentText("Enter the word to edit!");
            add.setHeaderText(null);
            add.showAndWait();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textEnglish.setText(null);
        textVietnam.setText(null);
        wordsList = FXCollections.observableArrayList();
        for (int i = 1; i< newWords.numberWords;i++){
            wordsList.add(newWords.words[i]);
        }
        englishWord.setCellValueFactory(new PropertyValueFactory<>("word_target"));
        vietnamWords.setCellValueFactory(new PropertyValueFactory<>("word_explain"));
        table.setItems(wordsList);
    }
}
