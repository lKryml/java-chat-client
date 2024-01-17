import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.event.*;


public class GUI extends Application implements EventHandler<ActionEvent> {
    String message;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("JavaChat");



        TextField txt1 = new TextField();
        TextField txt2 = new TextField();
        Button btn1 = new Button("Prompt");
        btn1.setOnAction( e -> {
            txt2.setText(GPT.prompt(txt1.getText()));

        });




        VBox layout = new VBox(btn1,txt1,txt2);
        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 500,500);


        primaryStage.setScene(scene1);
        primaryStage.show();



    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
