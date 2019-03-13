import diagram.Diagram;
import diagram.MyDiagram;
import io.Reader;
import parser.Scanner;
import parser.Token;
import parser.TokenType;
import system.Tools;

public class MainDiagram {
    public static void main(String[] args) {

        String text = Tools.getSourceText();

        Scanner scanner = new Scanner(text);
        MyDiagram diagram = new MyDiagram(scanner);

        diagram.program();

        Token token = scanner.nextScanner();
        if (token.getType() == TokenType.EOF)
            System.out.println("Синтаксических ошибок не обнаружено!");
        else
            diagram.printError("Лишний текст в конце программы");
    }
}
