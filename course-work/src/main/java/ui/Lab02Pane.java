package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.PrintStream;

import lab02.AnnotatedClass;
import lab02.MethodCaller;

public class Lab02Pane extends VBox {
    private final ConsolePane console = new ConsolePane();

    public Lab02Pane() {
        setSpacing(10);
        setPadding(new Insets(10));

        Button run = new Button("Вывести методы");
        run.setOnAction(e -> runTask());

        HBox controls = new HBox(10, new Label(""), run);
        controls.setAlignment(Pos.CENTER_LEFT);

        VBox.setVgrow(console, Priority.ALWAYS);
        console.setPrefHeight(420);

        getChildren().addAll(controls, console);
    }

    private void runTask() {
        PrintStream out = console.getPrintStream();
        PrintStream prevOut = System.out, prevErr = System.err;

        try {
            System.setOut(out);
            System.setErr(out);

            AnnotatedClass obj = new AnnotatedClass();
            MethodCaller caller = new MethodCaller();

            System.out.println("Вызов публичных методов:");
            obj.publicMethod();
            obj.publicMethod2(2);
            obj.publicMethod3("param1", 1.0);
            System.out.println();

            System.out.println("Вызов защищённых и приватных методов:");
            caller.callAnnotatedMethods(obj);
        } catch (Throwable t) {
            t.printStackTrace(out);
        } finally {
            System.setOut(prevOut);
            System.setErr(prevErr);
        }
    }
}
