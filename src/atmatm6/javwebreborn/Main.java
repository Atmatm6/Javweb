package atmatm6.javwebreborn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;

public class Main extends Application {

    public static boolean statechanged = false;
    public static final String prefix = "http://";
    public static final String goorl = prefix + "www.google.com";

    @Override
    public void start(Stage primaryStage) throws Exception{
        TextField urlField = new TextField();
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        Button goUrl = new Button();
        engine.load(goorl);

        urlField.setText(goorl);
        urlField.setPrefWidth(500000000);
        urlField.setTooltip(new Tooltip("This is where the link goes"));
        engine.setOnStatusChanged(event -> {
            if (!statechanged) {
                System.out.println(engine.getLoadWorker().getState() + " | " + engine.getLocation());
                if (StateHandler.isFailed(engine.getLoadWorker())) {
                    urlField.setText(engine.getLocation());
                    engine.load(String.valueOf(Main.class.getResource("failed.html")));
                    primaryStage.setTitle("Javweb | " + engine.getTitle());
                } else {
                    urlField.setText(engine.getLocation());
                    primaryStage.setTitle("Javweb | " + engine.getTitle());
                }
                statechanged = true;
            }
        });

        TextField google = new TextField();
        google.setMinWidth(500);
        google.setOnAction(event -> {
            
        });
        goUrl.setPrefWidth(3);
        goUrl.setOnAction(event -> {
            if (!urlField.getText().isEmpty()) {
                if (urlField.getText().equals("Please enter a URL")) urlField.setDisable(false);
                else if (!urlField.getText().startsWith(prefix)) {
                    engine.load(prefix + urlField.getText());
                    statechanged = false;
                } else {
                    engine.load(urlField.getText());
                    statechanged = false;
                }
            } else urlField.setText("Please enter a URL");
        });

        MenuBar menubar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(event -> {

        });
        file.getItems().addAll(openItem);
        menubar.getMenus().addAll(file);
        menubar.setUseSystemMenuBar(true);

        HBox searchBar = new HBox(google,urlField,goUrl);
        BorderPane minibp = new BorderPane(searchBar, menubar, null, null, null);
        BorderPane bp = new BorderPane(browser,minibp,null,null,null);
        primaryStage.setTitle("Javweb | " + engine.getLocation());
        if(!primaryStage.isMaximized()){ primaryStage.setMaximized(true); }
        primaryStage.setScene(new Scene(bp, 1280, 1280));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Stage stageClose = new Stage();
            Button close = new Button("Ok");
            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    CookieManager manager = new CookieManager();
                    CookieHandler.setDefault(manager);
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter("the-file-name.txt", "UTF-8");
                        for (int i = 0 ; i < engine.getHistory().getEntries().size() ; i = i+1) {
                            writer.write(engine.getHistory().getEntries().get(i).toString());
                        }
                        writer.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    stageClose.close();
                    primaryStage.close();
                    System.exit(0);   // To make sure that the program truly quits its evil deeds and the land gets saved from the dreaded monsters that wreak havoc around every corner.
                }
            });
            Button cancel = new Button("Cancel");
            cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stageClose.close();
                }
            });
            Label leavelabel = new Label("Close the browser?");
            HBox gp = new HBox(leavelabel,close,cancel);
            Scene scene = new Scene(gp,224,26);
            stageClose.setScene(scene);
            stageClose.setTitle("Close Dialog");
            stageClose.show();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
