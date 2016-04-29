package net.rikelek1.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rikelek1.BotInstance;
import sx.blah.discord.util.DiscordException;

import java.util.ArrayList;

public class MainWindow extends Application {
    private BotInstance bot;
    private String token = "";
    private ArrayList<String> messageList;
    private ObservableList<String> channelNames = FXCollections.observableArrayList("#general", "#afk", "#dev");

    private Stage window;
    private BorderPane mainLayout;
    private VBox chatVBox;
    private HBox chatHBox, mainHBox;

    private TextArea messageListView;
    private ListView<String> channelsListView;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Botelek1 GUI");
        window.setOnCloseRequest(event -> exit());

        messageListView = new TextArea();
        messageListView.setEditable(false);

        channelsListView= new ListView<>();
        channelsListView.setEditable(false);
        channelsListView.setItems(channelNames);

        TextField messageField = new TextField();
        Button buttonSend = new Button("Send");
        buttonSend.setOnAction(e -> {
            sendMessage(messageField.getText());
            messageField.clear();
        });
        buttonSend.requestFocus();

        chatHBox = new HBox(5);
        chatHBox.setSpacing(5);
        chatHBox.getChildren().addAll(messageField, buttonSend);

        chatVBox = new VBox(5);
        chatVBox.setSpacing(5);
        chatVBox.getChildren().addAll(messageListView, chatHBox);

        mainHBox = new HBox(5);
        mainHBox.setSpacing(5);
        mainHBox.getChildren().addAll(channelsListView, chatVBox);

        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(5, 5, 5, 5));
        mainLayout.setCenter(mainHBox);

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();

        initBot();
    }

    private void sendMessage(String message) {
        bot.sendMessage(message);
    }

    private void initBot() {
        bot = new BotInstance(token, this);

        try {
            bot.login();
        } catch(DiscordException e) {
            System.out.println("Couldn't start Botelek1");
        }
    }

    public void updateMessages() {
        Platform.runLater(() -> {
            messageListView.clear();
            for (String message : messageList) {
                messageListView.appendText(message);
            }
        });
    }

    public void setMessages(ArrayList<String> messages) {
        this.messageList = messages;
    }

    public void exit() {
        bot.terminate();
        Platform.exit();
        System.exit(1337);
    }
}
