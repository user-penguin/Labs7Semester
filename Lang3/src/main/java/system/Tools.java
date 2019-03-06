package system;

import io.Reader;

import javax.swing.*;

public class Tools {

    static String getPathToFile () {
        JFileChooser fileopen = new JFileChooser();
        fileopen.showDialog(null, "Выберите файл");
        String path = fileopen.getSelectedFile().getPath();
        return path;
    }

    public static String getSourceText () {
        String text;
        String path = getPathToFile();
        try {
            text = Reader.getDate(path);
        } catch (Exception e) {
            System.out.println("Неверны тип файла");
            return "";
        }
        return text;
    }
}
