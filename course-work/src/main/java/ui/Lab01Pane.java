package ui;

import lab01.*;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.PrintStream;

public class Lab01Pane extends VBox {
    private final ConsolePane console = new ConsolePane();
    private String currentLocation = "Деревня";

    public Lab01Pane() {
        setSpacing(10);
        setPadding(new Insets(10));

        VBox.setVgrow(console, Priority.ALWAYS);
        console.setPrefHeight(420);

        TextField name = new TextField("Герой");
        name.setPromptText("Имя героя");

        ComboBox<String> strategy = new ComboBox<>();
        strategy.getItems().addAll("WalkingStrategy", "HorseRidingStrategy", "FlyingStrategy", "TeleportationStrategy");
        strategy.setValue("WalkingStrategy");

        ComboBox<String> toBox = new ComboBox<>();
        toBox.getItems().addAll("Деревня", "Лес", "Гора", "Замок");
        toBox.setValue("Замок");

        Button run = new Button("Переместиться");
        run.setOnAction(e -> runTask(name.getText().trim(), toBox.getValue(), strategy.getValue()));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Имя:"), name, new Label("Стратегия:"), strategy);
        grid.addRow(1, new Label("Куда:"), toBox);

        HBox row = new HBox(10, run);
        row.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(grid, row, console);
    }

    private MovementStrategy makeStrategy(String name) {
        return switch (name) {
            case "HorseRidingStrategy" -> new HorseRidingStrategy();
            case "FlyingStrategy" -> new FlyingStrategy();
            case "TeleportationStrategy" -> new TeleportationStrategy();
            default -> new WalkingStrategy();
        };
    }

    private void runTask(String heroName, String destination, String strategyName) {
        PrintStream out = console.getPrintStream();

        if (destination == null || destination.isEmpty()) {
            out.println("Выберите пункт назначения.");
            return;
        }

        if (destination.equals(currentLocation)) {
            out.println("Вы уже находитесь в пункте \"" + destination + "\".");
            return;
        }

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                PrintStream prevOut = System.out, prevErr = System.err;
                try {
                    System.setOut(out);
                    System.setErr(out);

                    String start = currentLocation;
                    Hero hero = new Hero(heroName.isBlank() ? "Герой" : heroName, start);
                    MovementStrategy strat = makeStrategy(strategyName);

                    hero.setMovementStrategy(strat);
                    hero.moveTo(destination);

                    currentLocation = destination;
                    out.println("Герой теперь находится в: " + currentLocation);
                } catch (Exception ex) {
                    ex.printStackTrace(out);
                } finally {
                    System.setOut(prevOut);
                    System.setErr(prevErr);
                }
                return null;
            }
        };
        new Thread(task, "lab01-run").start();
    }
}
