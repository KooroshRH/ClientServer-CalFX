import Components.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application
{
    private static Client client;

    private static Label calculatorText;
    private static String formattedData = "";

    public static void main(String[] args)
    {
        Server server = new Server(888);
        new Thread(server::waitForClient).start();
        client = new Client("127.0.0.1", 888);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Calculator");
        ArrayList<Button> buttons = new ArrayList<>();
        BorderPane borderPane = new BorderPane();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        calculatorText = new Label("");
        borderPane.setTop(calculatorText);
        calculatorText.setPadding(new Insets(10, 10, 0, 10));

        AddNumberButtons(buttons);
        AddOperatorButtons(buttons);

        gridPane.getChildren().addAll(buttons);
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 150, 175);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static void AddNumberButtons(ArrayList<Button> buttons)
    {
        for (int i = 0; i < 10; i++)
        {
            Button button = new Button("" + i);
            button.setOnAction(actionEvent -> {
                calculatorText.setText(calculatorText.getText() + ((Button) actionEvent.getSource()).getText());
                formattedData = formattedData + ((Button) actionEvent.getSource()).getText();
            });
            buttons.add(button);
            GridPane.setConstraints(button, i % 3, (i / 3)+1);
        }
    }

    private static void AddOperatorButtons(ArrayList<Button> buttons)
    {
        ArrayList<Button> operators = new ArrayList<>();
        operators.add(new Button("+"));
        operators.add(new Button("-"));
        operators.add(new Button("*"));
        operators.add(new Button("/"));
        for (Button button : operators) {
            button.setOnAction(actionEvent -> {
                formattedData = calculatorText.getText() + Server.SEPARATOR + operators.indexOf(button) + Server.SEPARATOR;
                calculatorText.setText(calculatorText.getText() + button.getText());
            });
            GridPane.setConstraints(button, 4, operators.indexOf(button) + 1 );
        }

        Button result = new Button("=");
        result.setOnAction(actionEvent -> {
            client.send(formattedData);
            formattedData = "";
            calculatorText.setText(client.receive());
        });
        GridPane.setConstraints(result, 2, 4);

        Button clear = new Button("C");
        clear.setOnAction(actionEvent -> {
            formattedData = "";
            calculatorText.setText("");
        });
        GridPane.setConstraints(clear, 1, 4);

        buttons.addAll(operators);
        buttons.add(result);
        buttons.add(clear);
    }
}
