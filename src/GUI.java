import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
public class GUI extends Application {
    private TextArea chatArea;
    private TextField messageField;
    private TextField usernameField;
    private Button connectButton;
    private boolean connectedToServer = false;
    private BufferedWriter serverWriter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaChatClient");

        BorderPane layout = new BorderPane();

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        layout.setCenter(chatArea);

        HBox bottomBox = new HBox(10);

        Label messageFieldLabel = new Label("Message: ");
        messageField = new TextField();
        messageField.setOnAction(e -> {
            sendMessage(messageField.getText());
            if(messageField.getText().contains("GPT")){
                sendMessage("ChatGPT: " + GPT.prompt(messageField.getText()));
            }
            messageField.clear();
        }
        );
        HBox.setMargin(messageField, new Insets(0, 10, 0, 0));

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPrefWidth(100);
        HBox.setMargin(usernameField, new Insets(0, 10, 0, 0));

        connectButton = new Button("Connect");
        connectButton.setOnAction(e -> connectClient(usernameField.getText()));
        HBox.setMargin(connectButton, new Insets(0, 10, 0, 0));

        bottomBox.getChildren().addAll(usernameLabel, usernameField, connectButton, messageFieldLabel, messageField);
        bottomBox.setHgrow(messageField, Priority.ALWAYS);

        layout.setBottom(bottomBox);
        BorderPane.setMargin(bottomBox, new Insets(10));

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image( "icon.png" ));
        primaryStage.show();
    }

    private void startServerConnection() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("192.168.1.5", 12345);
                serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Handle server messages in a separate thread
                new Thread(() -> {
                    while (true) {
                        try {
                            String serverMessage = reader.readLine();
                            if (serverMessage != null) {
                                Platform.runLater(() -> chatArea.appendText(serverMessage + "\n"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                // Notify server about the new client
                serverWriter.write(usernameField.getText() + " || has connected"  + "\r\n");
                serverWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        connectedToServer = true;
    }

    private void connectClient(String username) {
        if (username != null && !username.isEmpty()) {
            connectButton.setDisable(true); // Disable the connect button after successful connection
            usernameField.setDisable(true); // Disable the username field after successful connection
        } else {
            chatArea.appendText("Invalid username.\n");
        }
        if (!connectedToServer) {
            startServerConnection();
        }
    }
    private void sendMessage(String message) {
        if (serverWriter != null) {
            try {
                if(message.contains("ChatGPT")){
                    serverWriter.write(message + "\r\n");
                }else {
                    serverWriter.write(usernameField.getText() + ": " + message + "\r\n");
                }
                serverWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
