package ca.cmpt213.asn5.client;

import com.google.gson.Gson;


import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

/**
 * This class provides a javafx interface to access data from the tokimon database
 */
public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Creates an action board to display content as per user request.
        GridPane actionBoard = new GridPane();
        actionBoard.setHgap(10);
        actionBoard.setVgap(10);

        Label heading = new Label("Tokimon Database");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        Button showTokimons = new Button("Show Tokimons");
        showTokimons.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO: clear padding on action board
                actionBoard.getChildren().clear();      //Clear the grid pane before displaying all the tokimons
                displayAllTokimons(actionBoard);       // display all the tokimons on the action board

            }
        });

        Button addTokimons = new Button("Add Tokimons");
        addTokimons.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO: clear padding on action board
                actionBoard.getChildren().clear();  //Clear the grid pane before displaying all the tokimons
                addTokimonControls(actionBoard);   //display the add tokimons controls on the action board.

            }
        });

        //Creates an initial control board for tokimon database
        GridPane controlBoard = new GridPane();
        controlBoard.add(heading, 0, 0);
        controlBoard.add(showTokimons, 0, 1);
        controlBoard.add(addTokimons, 1, 1);
        controlBoard.setHgap(10);
        controlBoard.setVgap(10);
        controlBoard.setPadding(new Insets(10, 100, 10, 450));

        //Adds control board and actions board to vbox
        VBox vbox = new VBox(controlBoard, actionBoard);

        //Sets a scene
        Scene scene = new Scene(vbox, 1200, 800);

        //Set and show the stage
        stage.setScene(scene);
        stage.setTitle("TokimonsData");
        stage.show();
    }

    /**
     * This function insert add tokimon controls on the display board
     *
     * @param actionBoard a grid pane used to display add tokimon controls
     */
    private void addTokimonControls(GridPane actionBoard) {

        //Creates a heading for the action board
        Label heading = new Label("Add New Tokimon");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        //Creates controls for the actions board
        Label image = new Label("Image");
        image.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Label name = new Label("Name");
        name.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Label type = new Label("Type");
        type.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Label rarity = new Label("Rarity");
        rarity.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));

        //TODO: Create text box for the image, name, type and rarity


        //Add all the control components to the action board
        actionBoard.add(heading, 5, 0);
        actionBoard.add(image, 3, 1);
        actionBoard.add(name, 3, 2);
        actionBoard.add(type, 3, 3);
        actionBoard.add(rarity, 3, 4);
        actionBoard.setPadding(new Insets(10, 100, 10, 450));

    }

    /**
     * This function inserts all the tokimons to the display board
     *
     * @param actionBoard a grid pane used to display all the tokimons.
     */
    private void displayAllTokimons(GridPane actionBoard) {

        //Creates a heading for the action board
        Label heading = new Label("Tokimons");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));


        //TODO: Get tokimons from database
        Label testServer = new Label();
        try {
            URI uri = new URI("http://localhost:8080/tokimon/all");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.getInputStream();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonOutput.append(line);
            }

            // Parse JSON using Gson
            Gson gson = new Gson();
            Type tokimonListType = new TypeToken<List<Tokimon>>(){}.getType();
            List<Tokimon> tokimonList = gson.fromJson(jsonOutput.toString(), tokimonListType);

            // Display the data
            StringBuilder displayText = new StringBuilder();
            for (Tokimon tokimon : tokimonList) {
                displayText.append(tokimon.toString()).append("\n");
            }
            testServer.setText(displayText.toString());

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            testServer.setText("Error: " + e.getMessage());
        }

//            connection.getInputStream();
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream())
//            );
//            String output = br.readLine();
//            System.out.println(output);
//            System.out.println(connection.getResponseCode());
//
//
//            testServer.setText(output);
//            connection.disconnect();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //Add heading and tokimons to the display board
        //TODO: Add tokimons to the display board
        actionBoard.add(heading, 6, 0);
        actionBoard.add(testServer, 0,1);


    }

}


