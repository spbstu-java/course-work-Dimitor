package ui;

import lab03.Dictionary;
import lab03.SortedDictionary;
import lab03.TranslatorUI;
import lab03.FileReadException;
import lab03.InvalidFileFormatException;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;

public class Lab03Pane extends VBox {
    private final ConsolePane console = new ConsolePane();
    private File dictFile;
    private File textFile;
    private final File defaultDir;

    public Lab03Pane() {
        setSpacing(10);
        setPadding(new Insets(10));

        defaultDir = new File(System.getProperty("user.dir"));

        Button pickDict = new Button("Выбрать словарь...");
        Label dictLbl = new Label("Словарь: не выбран");

        Button pickText = new Button("Выбрать текст...");
        Label textLbl = new Label("Текст: не выбран");

        pickDict.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Выбор файла словаря");
            if (defaultDir.exists())
                fc.setInitialDirectory(defaultDir);
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text files", "*.txt"),
                    new FileChooser.ExtensionFilter("All files", "*.*"));
            dictFile = fc.showOpenDialog(getScene().getWindow());
            dictLbl.setText("Словарь: " + (dictFile == null ? "не выбран" : dictFile.getName()));
        });

        pickText.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Выбор файла для перевода");
            if (defaultDir.exists())
                fc.setInitialDirectory(defaultDir);
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text files", "*.txt"),
                    new FileChooser.ExtensionFilter("All files", "*.*"));
            textFile = fc.showOpenDialog(getScene().getWindow());
            textLbl.setText("Текст: " + (textFile == null ? "не выбран" : textFile.getName()));
        });

        TextArea manual = new TextArea();
        manual.setPromptText("Или введите текст для перевода вручную...");
        manual.setPrefRowCount(6);

        Button translate = new Button("Перевести");
        translate.setOnAction(e -> runTask(manual.getText()));

        GridPane files = new GridPane();
        files.setHgap(10);
        files.setVgap(10);
        files.addRow(0, pickDict, dictLbl);
        files.addRow(1, pickText, textLbl);

        getChildren().addAll(files, new Label("Ручной ввод:"), manual, translate, console);
    }

    private void runTask(String manualText) {
        PrintStream out = console.getPrintStream();
        try {
            SortedDictionary dict = new SortedDictionary();

            if (dictFile != null) {
                try {
                    dict.load(dictFile.getAbsolutePath());
                } catch (InvalidFileFormatException | FileReadException ex) {
                    out.println("Ошибка словаря: " + ex.getMessage());
                    return;
                }
            }

            String text = (textFile != null)
                    ? Files.readString(textFile.toPath())
                    : (manualText == null ? "" : manualText);

            Dictionary dictionary = dict;
            TranslatorUI ui = new TranslatorUI(dictionary);
            ui.translate(text, out);
        } catch (Exception ex) {
            ex.printStackTrace(out);
        }
    }
}
