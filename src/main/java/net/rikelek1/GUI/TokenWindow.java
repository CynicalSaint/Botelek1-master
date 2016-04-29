package net.rikelek1.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TokenWindow {
    public static String display() {
        StringProperty answer = new SimpleStringProperty("No Token Entered");

        Stage window = new Stage();
        window.setTitle("Your bot's Token");
        window.setMinWidth(250);

        Label mainText = new Label("Please enter the token of your bot.");
        TextField tokenText = new TextField();
        Button buttonSubmit = new Button("Submit");

        buttonSubmit.requestFocus();

        buttonSubmit.setOnAction(e -> {
            answer.set(tokenText.getText());
            window.close();
        });

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(mainText, tokenText, buttonSubmit);
        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.showAndWait();

        return answer.get();
    }
}
