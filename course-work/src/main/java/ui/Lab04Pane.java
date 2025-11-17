package ui;

import lab04.StreamUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lab04Pane extends VBox {
    private final ConsolePane console = new ConsolePane();

    public Lab04Pane() {
        setSpacing(10);
        setPadding(new Insets(10));
        VBox.setVgrow(console, Priority.ALWAYS);
        console.setPrefHeight(420);
        ComboBox<String> methodBox = new ComboBox<>();
        methodBox.getItems().addAll(
                "getAverage",
                "transformToUpperCaseWithPrefix",
                "getSquaresOfUniqueElements",
                "getLastElement",
                "sumEvenNumbers",
                "stringsToMap");
        methodBox.setValue("getAverage");

        TextField args = new TextField();
        args.setPrefWidth(420);
        args.setPromptText("Введите элементы через ;");

        methodBox.valueProperty().addListener((obs, o, n) -> {
            switch (n) {
                case "getAverage":
                case "getSquaresOfUniqueElements":
                    args.setPromptText("Введите числа через ;");
                    break;
                case "transformToUpperCaseWithPrefix":
                case "stringsToMap":
                    args.setPromptText("Введите строки через ;");
                    break;
                case "sumEvenNumbers":
                    args.setPromptText("Введите числа через ;");
                    break;
                default:
                    args.setPromptText("Введите элементы через ;");
                    break;
            }
        });

        Button run = new Button("Вызвать");
        run.setOnAction(e -> runTask(methodBox.getValue(), args.getText().trim()));

        HBox r1 = new HBox(10, new Label("Метод:"), methodBox);
        r1.setAlignment(Pos.CENTER_LEFT);
        HBox r2 = new HBox(10, new Label("Аргументы:"), args, run);
        r2.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(r1, r2, console);
    }

    private void runTask(String method, String rawArgs) {
        PrintStream out = console.getPrintStream();
        PrintStream prevOut = System.out, prevErr = System.err;
        try {
            System.setOut(out);
            System.setErr(out);

            switch (method) {
                case "getAverage": {
                    List<Integer> nums = parseIntList(rawArgs);
                    double res = StreamUtils.getAverage(nums);
                    out.println("Результат: " + res);
                    break;
                }
                case "transformToUpperCaseWithPrefix": {
                    List<String> items = parseStringList(rawArgs);
                    var res = StreamUtils.transformToUpperCaseWithPrefix(items);
                    out.println("Результат: " + res);
                    break;
                }
                case "getSquaresOfUniqueElements": {
                    List<Integer> nums = parseIntList(rawArgs);
                    var res = StreamUtils.getSquaresOfUniqueElements(nums);
                    out.println("Результат: " + res);
                    break;
                }
                case "getLastElement": {
                    List<String> tokens = splitArgs(rawArgs);
                    if (tokens.isEmpty()) {
                        out.println("Результат: null");
                        break;
                    }
                    if (allIntegers(tokens)) {
                        List<Integer> ints = new ArrayList<>();
                        for (String t : tokens)
                            ints.add(parseIntSafe(t, 0));
                        Integer res = StreamUtils.getLastElement(ints);
                        out.println("Результат: " + res);
                    } else {
                        List<String> strs = new ArrayList<>(tokens);
                        String res = StreamUtils.getLastElement(strs);
                        out.println("Результат: " + res);
                    }
                    break;
                }
                case "sumEvenNumbers": {
                    int[] a = parseIntArray(rawArgs);
                    int res = StreamUtils.sumEvenNumbers(a);
                    out.println("Результат: " + res);
                    break;
                }
                case "stringsToMap": {
                    List<String> items = parseStringList(rawArgs);
                    Map<Character, String> res = StreamUtils.stringsToMap(items);
                    out.println("Результат: " + res);
                    break;
                }
                default:
                    out.println("Метод не поддержан: " + method);
            }

        } catch (Exception ex) {
            ex.printStackTrace(out);
        } finally {
            System.setOut(prevOut);
            System.setErr(prevErr);
        }
    }

    private List<String> splitArgs(String raw) {
        List<String> toks = new ArrayList<>();
        if (raw == null || raw.isEmpty())
            return toks;

        StringBuilder cur = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '"') {
                quoted = !quoted;
                continue;
            }
            if (c == ';' && !quoted) {
                toks.add(cur.toString().trim());
                cur.setLength(0);
                continue;
            }
            cur.append(c);
        }
        if (cur.length() > 0)
            toks.add(cur.toString().trim());
        return toks;
    }

    private List<Integer> parseIntList(String raw) {
        List<String> t = splitArgs(raw);
        List<Integer> res = new ArrayList<>();
        for (String s : t)
            res.add(parseIntSafe(s, 0));
        return res;
    }

    private int[] parseIntArray(String raw) {
        List<Integer> list = parseIntList(raw);
        int[] a = new int[list.size()];
        for (int i = 0; i < a.length; i++)
            a[i] = list.get(i);
        return a;
    }

    private List<String> parseStringList(String raw) {
        return splitArgs(raw);
    }

    private int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    private boolean allIntegers(List<String> tokens) {
        for (String t : tokens) {
            try {
                Integer.parseInt(t.trim());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
