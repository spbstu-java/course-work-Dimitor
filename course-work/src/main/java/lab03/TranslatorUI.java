package lab03;

import java.io.PrintStream;

public class TranslatorUI {
    private final Dictionary dictionary;

    public TranslatorUI(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String translate(String text) {
        if (text == null || text.isBlank())
            return "";
        return dictionary.translate(text);
    }

    public void translate(String text, PrintStream out) {
        out.println("Исходный текст: " + (text == null ? "" : text));
        out.println("Перевод: " + translate(text));
    }
}
