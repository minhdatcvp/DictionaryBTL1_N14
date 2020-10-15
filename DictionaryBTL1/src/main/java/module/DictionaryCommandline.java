package module;

import java.io.*;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement{
    private static final File WordData = new File("src/Data/word.txt");
    private static final File addWord = new File("src/Data/dictionaries.txt");
    /**
     * Hàm show file Word.
     * @param
     * @throws IOException
     */
    public static void showAllWords(){
        System.out.println("No"+ "\t" + "| English"+"\t"+"| Vietnamese");
        for (int  i =0;i< newWords.numberWords;i++){
            System.out.println(i +"\t| "+ newWords.words[i].getWord_target()+"\t\t| "+ newWords.words[i].getWord_explain()+"\n");
        }
    }

    public static void dictionaryBasic() throws IOException {
        insertFromCommandline();
        showAllWords();
    }
    public static void dictionaryAdvanced() throws IOException {
        insertFromFile(addWord);
        showAllWords();
        dictionaryLookup();
    }
    public static void dictionarySearcher() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập từ bạn muốn tìm: ");
        String searchWordUp = input.nextLine();
        String searchWord = searchWordUp.toLowerCase();

        for(int i= 0; i < newWords.numberWords ;i++){
            if (newWords.words[i].getWord_target().toLowerCase().contains(searchWord)){
                if(newWords.words[i].getWord_target().contains("[")){
                    String[] childArr = newWords.words[i].getWord_target().split("\\[");
                    System.out.println(childArr[0]);
                }
                else System.out.println(newWords.words[i].getWord_target());

            }

        }
    }

}
