package atmatm6.javwebreborn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;

public class Main extends Application {

    static final String prefix = "http://";
    String goorl = prefix + "www.google.com";

    @Override
    public void start(Stage primaryStage) throws Exception{
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        engine.load(goorl);
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
        manager.getCookieStore().getCookies();
        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        writer.write(String.valueOf(manager.getCookieStore().getCookies()));
        writer.close();
        manager.getCookieStore().getCookies();
        TextField urlField = new TextField();
        urlField.setText(goorl);
        urlField.setPrefWidth(500000000);
        urlField.setTooltip(new Tooltip("This is where the link goes"));
        engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                urlField.setText(engine.getLocation());
                primaryStage.setTitle("Javweb | " + engine.getTitle());
            }
        });
        TextField google = new TextField();
        google.setMinWidth(500);
        Mnemonic googleIt = new Mnemonic(google,);
        Button gourl = new Button();
        gourl.setPrefWidth(3);
        gourl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!urlField.getText().isEmpty()){
                    if(urlField.getText().equals("Please enter a URL")){
                    }else if(!urlField.getText().startsWith(Main.prefix)){
                        engine.load(Main.prefix + urlField.getText());
                    }else{
                        engine.load(urlField.getText());
                    }
                }else{
                    urlField.setText("Please enter a URL");
                }
            }
        });
        HBox searchBar = new HBox(google,urlField,gourl);
        BorderPane bp = new BorderPane(browser,searchBar,null,null,null);
        primaryStage.setTitle("Javweb | " + engine.getLocation());
        if(!primaryStage.isMaximized()){ primaryStage.setMaximized(true); }
        primaryStage.setScene(new Scene(bp, 1280, 1280));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Stage stageClose = new Stage();

                Button close = new Button("Ok");
                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stageClose.close();
                        primaryStage.close();
                        System.exit(0);
                    }
                });
                Button cancel = new Button("Cancel");
                cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stageClose.close();
                        primaryStage.notify();
                        primaryStage.show();
                    }
                });
                HBox gp = new HBox(close,cancel);
                Scene scene = new Scene(gp,50,50);
                stageClose.setScene(scene);
                stageClose.setTitle("Close?");
                stageClose.show();
                primaryStage.hide();
                try {
                    primaryStage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
