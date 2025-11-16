package ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ConsolePane extends BorderPane {
  private final TextArea outArea = new TextArea();
  private final PrintStream ps = new PrintStream(
      new TextAreaOutputStream(outArea, StandardCharsets.UTF_8),
      true, StandardCharsets.UTF_8);

  public ConsolePane() {
    outArea.setEditable(false);
    outArea.setWrapText(true);
    setCenter(outArea);

    Button clear = new Button("Очистить вывод");
    clear.setOnAction(e -> outArea.clear());

    HBox top = new HBox(10, new Label("Вывод:"), clear);
    top.setPadding(new Insets(8));
    setTop(top);

    setPadding(new Insets(8));
  }

  public PrintStream getPrintStream() {
    return ps;
  }

  public TextArea getArea() {
    return outArea;
  }
}
