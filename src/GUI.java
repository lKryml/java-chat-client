import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.event.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class GUI extends Application {
    String message;
    TextArea chatArea;
    TextField messageField;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Chat Application");

        BorderPane layout = new BorderPane();

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        layout.setCenter(chatArea);

        messageField = new TextField();
        messageField.setOnAction(e -> sendMessage(messageField.getText()));
        layout.setBottom(messageField);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.out.println("close app"));
        primaryStage.show();
//        primaryStage.setTitle("JavaChat");
//
//
//
//        TextField txt1 = new TextField();
//        TextField txt2 = new TextField();
//        Button btn1 = new Button("Prompt");
//        btn1.setOnAction( e -> {
//            txt2.setText(GPT.prompt(txt1.getText()));
//
//        });
//
//
//
//
//        VBox layout = new VBox(btn1,txt1,txt2);
//        layout.setAlignment(Pos.CENTER);
//
//        Scene scene1 = new Scene(layout, 500,500);
//
//
//        primaryStage.setScene(scene1);
//        primaryStage.show();



    }

    private void sendMessage(String message) {
        chatArea.appendText("You: " + message + "\n");
        if(message.contains("GPT")){
            chatArea.appendText("ChatGPT: " + GPT.prompt(message));
            chatArea.appendText("\n");
        }
        messageField.clear();
//        try {
//            Socket socket = new Socket("localhost", 5555);
//            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
//            writer.println(message);
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private void closeApplication() {
//        try {
//            if (serverSocket != null && !serverSocket.isClosed()) {
//                serverSocket.close();
//            }
//            Platform.exit();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
