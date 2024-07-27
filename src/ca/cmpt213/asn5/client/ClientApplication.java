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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
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
                actionBoard.setPadding(new Insets(0));
                actionBoard.getChildren().clear();      //Clear the grid pane before displaying all the tokimons
                displayAllTokiCards(actionBoard);       // display all the tokimons on the action board

            }
        });

        Button addTokimons = new Button("Add Tokimons");
        addTokimons.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO: clear padding on action board
                actionBoard.setPadding(new Insets(0));
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
        TextField imageTextField = new TextField();
        TextField nameTextField = new TextField();
        TextField typeTextField = new TextField();
        TextField rarityTextField = new TextField();

        HBox headingBox = new HBox(10, heading);
        HBox imageBox = new HBox(10, image, imageTextField);
        HBox nameBox = new HBox(10, name, nameTextField);
        HBox typeBox = new HBox(10, type, typeTextField);
        HBox rarityBox = new HBox(10, rarity, rarityTextField);

        Button sumbit = new Button("Submit");


        sumbit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label success = new Label("Tokimon successfully added!");

                String imageText = imageTextField.getText();
                String nameText = nameTextField.getText();
                String typeText = typeTextField.getText();
                String rarityText = rarityTextField.getText();
                try {
                    URI uri = new URI("http://localhost:8080/tokimon/add");
                    URL url = uri.toURL();
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");

                    Tokimon newTokimon = new Tokimon();
                    newTokimon.setImagePath(imageText);
                    newTokimon.setName(nameText);
                    newTokimon.setType(typeText);
                    newTokimon.setRarityScore(Integer.parseInt(rarityText));

                    // Convert Tokimon object to JSON
                    Gson gson = new Gson();
                    String json = gson.toJson(newTokimon);

                    // Send JSON to the server
                    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                    wr.write(json);
                    wr.flush();
                    wr.close();

                    connection.connect();
                    System.out.println(connection.getResponseCode());
                    connection.disconnect();


                    // Clear the text fields
                    imageTextField.clear();
                    nameTextField.clear();
                    typeTextField.clear();
                    rarityTextField.clear();

                    // Display the success label
                    actionBoard.getChildren().remove(success); // Remove previous success label if any
                    actionBoard.add(success, 3, 6); // Add success label to the action board

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //Add all the control components to the action board
        actionBoard.add(headingBox, 5, 0);
        actionBoard.add(imageBox, 3, 1);

        actionBoard.add(nameBox, 3, 2);
        actionBoard.add(typeBox, 3, 3);
        actionBoard.add(rarityBox, 3, 4);
        actionBoard.add(sumbit,3,5);
        actionBoard.setPadding(new Insets(10, 100, 10, 450));

    }

    /**
     * This function inserts all the tokimon cards to the display board
     * @param actionBoard a grid pane used to display all the tokimons.
     */
    private void displayAllTokiCards(GridPane actionBoard) {

        //Creates a heading for the action board
        Label heading = new Label("Tokimons");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        //Creates a list of tokimons
        List<Tokimon> tokimonList = getTokimonsFromServer();

        //Creates a 2d array of tokimon cards
        VBox[][] tokimonCards = getTokimonCards(tokimonList);

        //Add heading and tokimon cards to the display board
        actionBoard.add(heading, 6, 0);
        for(int i = 0; i < tokimonCards.length; i++) {
            for(int j = 0; j < tokimonCards[i].length; j++) {
                if(tokimonCards[i][j] != null) {
                    actionBoard.add(tokimonCards[i][j], i,j+1);
                }
            }
        }
        actionBoard.setPadding(new Insets(10, 100, 10, 200));
    }

    /**
     * This function returns a 2d array of tokimon cards
     * @param tokimonList a list of tokimons
     * @return a 2d array of vboxes (tokimon cards)
     */
    public VBox[][] getTokimonCards(List<Tokimon> tokimonList) {

        //Creates an array of tokimon cards
        VBox [] cards = new VBox[tokimonList.size()];
        for(int i=0; i<tokimonList.size(); i++) {
            Tokimon tokimon = tokimonList.get(i);
            VBox card = getVbox(tokimon);       //Creates a tokimon card
            cards[i] = card;
        }

        //Creates a 2d array of tokimon cards
        VBox[][] tokimonCards = new VBox[5][cards.length];
        int rowLength = cards.length/5;     //Calculates row length based on total number of tokimon cards
        if(cards.length%5 != 0) {
            rowLength++;
        }
        int cardIndex = 0;
        for(int i=0; i<rowLength; i++) {
            for(int j=0; j<5; j++) {
                if(cardIndex == cards.length) {     //ends the loop after adding all cards to 2d array
                    break;
                }
                tokimonCards[i][j] = cards[cardIndex++];
            }
        }
        return tokimonCards;
    }

    /**
     * This function returns a single vbox(tokimon card) created for a given tokimon.
     * @param tokimon a tokimon
     * @return vbox of a tokimon card
     */
    public VBox getVbox(Tokimon tokimon){

        //Label for name
        Label name = new Label(tokimon.getName());

        //Imageview for tokimon image
        ImageView img = new ImageView(new Image("file:https://images.app.goo.gl/Jp72LusHZGgPkdbZ9"));
        img.setFitHeight(30);
        img.setPreserveRatio(true);

        //Controls to view full tokimon data or delete tokimon card
        Button view = new Button("View");
        Button delete = new Button("Delete");
        HBox hBox = new HBox(view, delete);

        //Adding all the components to a vbox to make tokimon card
        VBox vbox = new VBox();
        vbox.getChildren().add(name);
        vbox.getChildren().add(img);
        vbox.getChildren().add(hBox);

        return vbox;
    }

    /**
     * This function fetch all the tokimons from the server.
     * @return a list of all the tokimons.
     */
    public List<Tokimon> getTokimonsFromServer(){

        //Creates a list of all the tokimons
        List<Tokimon> tokimonList = new ArrayList<>();
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
          
            tokimonList = gson.fromJson(jsonOutput.toString(), tokimonListType);

            List<Tokimon> tokimonList = gson.fromJson(jsonOutput.toString(), tokimonListType);
     
            connection.disconnect();
          
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokimonList;

    }

}


