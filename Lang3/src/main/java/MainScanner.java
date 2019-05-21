import io.Reader;
import parser.Scanner;
import parser.Token;
import parser.TokenType;
import system.Tools;

public class MainScanner {
    public static void main (String[] args) {

        // получение исходного текста
        String text;
        try {
            text = Reader.getDate("test.txt");
        } catch (Exception e) {
            System.out.println("Неверны тип файла");
            return;
        }


        Scanner scanner = new Scanner(text);
        Token token; // лексема
        try {
            do {
                token = scanner.nextScanner();
                System.out.println(token);
            } while (token.getType() != TokenType.EOF);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}