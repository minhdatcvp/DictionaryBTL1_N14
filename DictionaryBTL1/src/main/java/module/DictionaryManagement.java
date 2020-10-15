package module;
import java.io.*;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    public static Dictionary newWords = new Dictionary();
    private static final File WordData = new File("src/Data/word.txt");
    private static final File addWord = new File("src/Data/dictionaries.txt");

    public static void insertFromCommandline(){
        Scanner input = new Scanner(System.in);
        newWords.numberWords = Integer.parseInt(input.nextLine());
        for (int i =0 ;i < newWords.numberWords ;i++ ) {
            newWords.words[i] = new Word();
            String newTarget = input.nextLine();
            newWords.words[i].setWord_target(newTarget);
            String newExplain = input.nextLine();
            newWords.words[i].setWord_target(newTarget);
            newWords.words[i].setWord_explain(newExplain);
        }
    }

    /**
     * ham insertfile
     * @param fileWord
     * @throws IOException
     */
    public static void insertFromFile(File fileWord) throws IOException {
        FileReader readWord = new FileReader(fileWord);
        BufferedReader readData = new BufferedReader(readWord);
        String line;
        int stt = 0;
        while ((line = readData.readLine()) != null) {
            String[] arrwords = line.split("\t");
            newWords.words[stt] = new Word();
            newWords.words[stt].setWord_target(arrwords[0]);
            newWords.words[stt].setWord_explain(arrwords[1]);
            stt++;
        }
        newWords.numberWords = stt;
        readWord.close();
        readData.close();
    }
    /**
     * Hàm dịch.
     * @throws IOException
     */
    public static void dictionaryLookup() throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập từ bạn muốn thêm: ");
        String searchWordUp = input.nextLine();
        String searchWord = searchWordUp.toLowerCase();
        int count = 0;
        for(int i= 0; i < newWords.numberWords ;i++){
            if(newWords.words[i].getWord_target().contains("[")){
                String[] childWordArr = newWords.words[i].getWord_target().split("\\[");
                if(childWordArr[0].toLowerCase().equals(searchWord+" ")){
                    System.out.println(newWords.words[i].getWord_explain());
                    count++;
                }
                else {
                    if(!childWordArr[0].equals(searchWord) && count == 0 && i==newWords.numberWords-1){
                        System.out.println("Can not found result");
                    }
                }
            }
            else {
                if(newWords.words[i].getWord_target().toLowerCase().equals(searchWord)){
                    System.out.println(newWords.words[i].getWord_explain());
                    count++;
                }
                else {
                    if(!newWords.words[i].getWord_target().equals(searchWord) && count==0&&i==newWords.numberWords-1){
                        System.out.println("Can not found result");
                    }
                }
            }
        }
    }

    /**
     * hàm add words
     */
    public static void addWords() throws IOException {
        // nhập từ và nghĩa muốn thêm
        Scanner input = new Scanner(System.in);
        System.out.print("Nhập từ bạn muốn thêm: ");
        String searchWordUp = input.nextLine();
        String engAddWord = searchWordUp.toLowerCase();
        // thêm từ và nghĩa vào file
        for(int i= 0; i < newWords.numberWords ;i++){
            if(newWords.words[i].getWord_target().contains("[")){
                String[] childWordArr = newWords.words[i].getWord_target().split("\\[");
                if(childWordArr[0].toLowerCase().equals(engAddWord+" ")){
                    System.out.println("Từ bạn muốn thêm đã có trong từ điển.");
                    break;
                }
                else {
                    if(!childWordArr[0].equals(engAddWord) && i==newWords.numberWords-1){
                        System.out.print("Thêm nghĩa của từ: ");
                        String vnAddWord = input.nextLine();
                        newWords.numberWords++;
                        newWords.words[newWords.numberWords-1] = new Word();
                        newWords.words[newWords.numberWords-1].setWord_target(engAddWord);
                        newWords.words[newWords.numberWords-1].setWord_explain(vnAddWord);
                        System.out.println("Đã thêm!");
                        break;
                    }
                }
            }
            else {
                if(newWords.words[i].getWord_target().toLowerCase().equals(engAddWord)){
                    System.out.println("Từ bạn muốn thêm đã có trong từ điển.");
                    break;
                }
                else {
                    if(!newWords.words[i].getWord_target().equals(engAddWord) && i==newWords.numberWords-1){
                        System.out.print("Thêm nghĩa của từ: ");
                        String vnAddWord = input.nextLine();
                        newWords.numberWords++;
                        newWords.words[newWords.numberWords-1] = new Word();
                        newWords.words[newWords.numberWords-1].setWord_target(engAddWord);
                        newWords.words[newWords.numberWords-1].setWord_explain(vnAddWord);
                        System.out.println("Đã thêm!");
                        break;
                    }
                }
            }
        }

    }

    /**
     * hàm edit words
     */
    public static void editWords() throws IOException {
        Scanner input  = new Scanner(System.in);

        System.out.print("Nhập từ bạn muốn sửa nghĩa: ");
        String searchWordUp = input.nextLine();
        String engAddWord = searchWordUp.toLowerCase();

        for(int i= 0; i < newWords.numberWords ;i++){
            if(newWords.words[i].getWord_target().contains("[")){
                String[] childWordArr = newWords.words[i].getWord_target().split("\\[");
                if(childWordArr[0].toLowerCase().equals(engAddWord+" ")){
                    System.out.print("Nhập nghĩa muốn sửa: ");
                    String vnAddWord = input.nextLine();
                    newWords.words[i].setWord_explain(vnAddWord);
                    System.out.println("Đã sửa xong!");
                    break;
                }
                else {
                    if(!childWordArr[0].equals(engAddWord) && i==newWords.numberWords-1){
                        System.out.print("Không tìm thấy từ cần sửa.");
                        break;
                    }
                }
            }
            else {
                if(newWords.words[i].getWord_target().toLowerCase().equals(engAddWord)){
                    System.out.print("Nhập nghĩa muốn sửa: ");
                    String vnAddWord = input.nextLine();
                    newWords.words[i].setWord_explain(vnAddWord);
                    System.out.println("Đã sửa xong!");
                    break;
                }
                else {
                    if(!newWords.words[i].getWord_target().equals(engAddWord) && i == newWords.numberWords-1){
                        System.out.print("Không tìm thấy từ cần sửa.");
                        break;
                    }
                }
            }
        }

    }

    /**
     * ham del words
     */
    public static void deleteWords() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.print("Nhập từ bạn muốn xóa: ");
        String searchWordUp = input.nextLine();
        String engAddWord = searchWordUp.toLowerCase();
        int index = -1;
        for (int i = 0; i < newWords.numberWords; i++) {
            if (newWords.words[i].getWord_target().contains("[")) {
                String[] childWordArr = newWords.words[i].getWord_target().split("\\[");
                if (childWordArr[0].toLowerCase().equals(engAddWord + " ")) {
                    index = i;
                    break;
                } else {
                    if (!childWordArr[0].equals(engAddWord) && i == newWords.numberWords - 1) {
                        System.out.print("Không tìm thấy từ cần xóa.");
                        break;
                    }
                }
            } else {
                if (newWords.words[i].getWord_target().toLowerCase().equals(engAddWord)) {
                    index = i;
                    break;
                } else {
                    if (!newWords.words[i].getWord_target().equals(engAddWord) && i == newWords.numberWords - 1) {
                        System.out.print("Không tìm thấy từ cần xóa.");
                        break;
                    }
                }
            }
        }
        if(index != -1){
            for (int i = index;i < newWords.numberWords;i++){
                newWords.words[i] = new Word();
                newWords.words[i] = newWords.words[i+1];
            }
            newWords.words[newWords.numberWords] = null;
            newWords.numberWords--;
            System.out.println("Đã xóa!");

        }
    }
    /**
     * ghi vao file
     */
    public static void dictionaryExportToFile(File fileWord) throws IOException {
        FileWriter fw = new FileWriter(fileWord);
        for (int i = 0;i< newWords.numberWords;i++){
            fw.write(newWords.words[i].getWord_target()+"\t"+newWords.words[i].getWord_explain()+"\r\n");
        }
        fw.close();
    }
}
