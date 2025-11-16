import ui.Lab01Pane;
import ui.Lab02Pane;
import ui.Lab03Pane;
import ui.Lab04Pane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppFx extends Application {

    @Override
    public void start(Stage stage) {
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabs.getTabs().add(new Tab("Lab01", new Lab01Pane()));
        tabs.getTabs().add(new Tab("Lab02", new Lab02Pane()));
        tabs.getTabs().add(new Tab("Lab03", new Lab03Pane()));
        tabs.getTabs().add(new Tab("Lab04", new Lab04Pane()));

        BorderPane root = new BorderPane(tabs);
        BorderPane.setMargin(tabs, new Insets(10));
        Scene scene = new Scene(root, 1100, 700);

        stage.setTitle("Course Work — JavaFX wrapper (Lab01–Lab04)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
