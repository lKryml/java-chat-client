import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.ServerSocket;


public class GUI extends Application {
    String message;
    TextArea chatArea;
    TextField messageField;
    private TextField usernameField;
    private Button serverButton;
    private Button connectButton;
    private boolean ServerRunning = false;
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

        HBox bottomBox = new HBox(10);

        Label messageFieldLabel = new Label("Message: ");
        messageField = new TextField();
        messageField.setOnAction(e -> sendMessage(messageField.getText()));
        HBox.setMargin(messageField, new Insets(0, 10, 0, 0));

        serverButton = new Button("Start Server");
        serverButton.setOnAction(e -> {
            try {
                startServer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox.setMargin(serverButton, new Insets(0, 10, 0, 0));

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPrefWidth(100);
        HBox.setMargin(usernameField, new Insets(0, 10, 0, 0));



        connectButton = new Button("Connect");
        connectButton.setOnAction(e -> connectClient(usernameField.getText()));

        bottomBox.getChildren().addAll(serverButton, usernameLabel, usernameField, connectButton,messageFieldLabel, messageField);
        bottomBox.setHgrow(messageField, Priority.ALWAYS);

        layout.setBottom(bottomBox);
        BorderPane.setMargin(bottomBox, new Insets(10));

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.out.println("close app"));
        primaryStage.show();
    }


    private void startServer() throws IOException {
        if (ServerRunning) {
            ServerRunning = false;



        } else {
            serverButton.setText("Exit");
            // Implement logic to start the server
            ServerRunning = true;
        }
    }

    private void connectClient(String username) {
        // Implement your logic to handle client connection
        if (username != null && !username.isEmpty()) {
            chatArea.appendText("Connected as: " + username + "\n");
            connectButton.setDisable(true); // Disable the connect button after successful connection
            usernameField.setDisable(true); // Disable the username field after successful connection
        } else {
            chatArea.appendText("Invalid username.\n");
        }
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
