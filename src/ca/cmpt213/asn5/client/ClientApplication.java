package ca.cmpt213.asn5.client;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.geometry.HPos;

/**
 * This class provides a javafx interface to access data from the tokimon database
 */
public class ClientApplication extends Application {

    //custom font
    Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 40);
    String fontName = "VT323";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Create a heading label with the custom font
        Label heading = new Label("Tokimon Database");
        heading.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 50));


        // Creates an action board to display content as per user request.
        GridPane actionBoard = new GridPane();
        actionBoard.setHgap(10);
        actionBoard.setVgap(10);

        Button showTokimons = new Button("Show Tokimons");
        showTokimons.setFont(Font.font(fontName,18));
        showTokimons.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                actionBoard.setPadding(Insets.EMPTY);
                actionBoard.getChildren().clear();
                displayAllTokiCards(actionBoard);
            }
        });

        Button addTokimons = new Button("Add Tokimons");
        addTokimons.setFont(Font.font(fontName,18));
        addTokimons.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                actionBoard.setPadding(Insets.EMPTY);
                actionBoard.getChildren().clear();
                addTokimonControls(actionBoard);
            }
        });

        // Creates an initial control board for tokimon database
        GridPane controlBoard = new GridPane();
        controlBoard.add(showTokimons, 0, 0);
        controlBoard.add(addTokimons, 1, 0);
        controlBoard.setHgap(10);
        controlBoard.setVgap(10);
        controlBoard.setPadding(new Insets(10));
        controlBoard.setAlignment(Pos.TOP_CENTER);

        // create an HBox for the header and set alignment
        HBox headerBox = new HBox(heading);
        headerBox.setAlignment(Pos.TOP_CENTER);
        headerBox.setPadding(new Insets(50, 0, 10, 0));

        // adds header, control board, and action board to VBox
        VBox vbox = new VBox(headerBox, controlBoard, actionBoard);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);
        //vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/backround.gif"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(900);
        backgroundImageView.setPreserveRatio(true);

        // Create a StackPane and add the background image and VBox to it
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, vbox);
        StackPane.setAlignment(vbox, Pos.TOP_CENTER);

        // Sets a scene
        Scene scene = new Scene(stackPane, 1200, 800);

       // Scene scene = new Scene(vbox, 1200, 800);

        stage.setScene(scene);
        stage.setTitle("TokimonsData");
        stage.show();
    }

    /**
     * This function insert add tokimon controls on the display board
     * @param actionBoard a grid pane used to display add tokimon controls
     */
    private void addTokimonControls(GridPane actionBoard) {

        // Center the GridPane
        actionBoard.setAlignment(Pos.CENTER);
        actionBoard.setHgap(10);
        actionBoard.setVgap(10);
        actionBoard.setPadding(new Insets(20, 20, 20, 20));

        // Creates a heading for the action board
        Label heading = new Label("Add New Tokimon");
        heading.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 30));

        // Creates controls for the action board
        Label image = new Label("Image URL");
        image.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Label name = new Label("Name");
        name.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Label type = new Label("Type");
        type.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));
        Label rarity = new Label("Rarity");
        rarity.setFont(Font.font("Sans", FontWeight.BOLD, FontPosture.REGULAR, 15));

        // Set preferred width for labels
        double labelWidth = 80;
        image.setPrefWidth(labelWidth);
        name.setPrefWidth(labelWidth);
        type.setPrefWidth(labelWidth);
        rarity.setPrefWidth(labelWidth);

        // Creates text fields to collect Tokimon information
        TextField imageTextField = new TextField();
        TextField nameTextField = new TextField();
        TextField typeTextField = new TextField();
        TextField rarityTextField = new TextField();

        //Set the width of all the text fields.
        imageTextField.setPrefWidth(300);
        nameTextField.setPrefWidth(300);
        typeTextField.setPrefWidth(300);
        rarityTextField.setPrefWidth(300);

        //Allows only correct input to the text fields
        acceptStringInput(nameTextField);
        acceptStringInput(typeTextField);
        acceptIntegerInput(rarityTextField);

        // Create HBoxes and align them center
        HBox headingBox = new HBox(heading);
        headingBox.setAlignment(Pos.CENTER);
        HBox imageBox = new HBox(10, image, imageTextField);
        imageBox.setAlignment(Pos.CENTER_LEFT);
        HBox nameBox = new HBox(10, name, nameTextField);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        HBox typeBox = new HBox(10, type, typeTextField);
        typeBox.setAlignment(Pos.CENTER_LEFT);
        HBox rarityBox = new HBox(10, rarity, rarityTextField);
        rarityBox.setAlignment(Pos.CENTER_LEFT);

        Button submit = new Button("Submit");
        submit.setFont(Font.font(fontName,18));

        Label messageLabel = new Label();
        messageLabel.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 15));

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (isValidImageURL(imageTextField.getText())) {
                    //Creates a new tokimon
                    Tokimon tokimon = new Tokimon(imageTextField.getText(),
                                                  nameTextField.getText(),
                                                  typeTextField.getText(),
                                                  Integer.parseInt(rarityTextField.getText()));

                    //Add tokimon to the server and display a message
                    addTokimonToServer(tokimon, actionBoard);
                    messageLabel.setText("Tokimon added successfully!");
                    messageLabel.setStyle("-fx-text-fill: black;");

                    // Clear the text fields
                    imageTextField.clear();
                    nameTextField.clear();
                    typeTextField.clear();
                    rarityTextField.clear();

                } else {
                    messageLabel.setText("Invalid url, please try again");  //display error message
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        // Add all the control components to the action board
        actionBoard.add(headingBox, 0, 0, 2, 1); // Span two columns for heading
        actionBoard.add(imageBox, 0, 1);
        actionBoard.add(nameBox, 0, 2);
        actionBoard.add(typeBox, 0, 3);
        actionBoard.add(rarityBox, 0, 4);
        actionBoard.add(submit, 0, 5);
        actionBoard.add(messageLabel, 0, 6, 2, 1); // Span two columns for message label

        GridPane.setHalignment(submit, HPos.CENTER);
        GridPane.setHalignment(messageLabel, HPos.CENTER);

    }

    /**
     * Checks if the image url the user inputted is valid or not.
     * @param urlString the url string provided by the tokimon
     * @return returns the boolean true or false
     */
    private boolean isValidImageURL(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                String contentType = connection.getContentType();
                if (contentType.startsWith("image/")) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * This function adds a tokimon to the server
     * @param newTokimon a new tokimon to add to the server
     * @param actionBoard grid pane to display
     */
    public void addTokimonToServer(Tokimon newTokimon, GridPane actionBoard){

        try {
            //Starts a connection with the server
            URI uri = new URI("http://localhost:8080/tokimon/add");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function inserts all the tokimon cards to the display board
     * @param actionBoard a grid pane used to display all the tokimons.
     */
    private void displayAllTokiCards(GridPane actionBoard) {

        // Creates a heading for the action board
        Label heading = new Label("");

        //heading.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        GridPane.setHalignment(heading, HPos.CENTER);
        GridPane.setValignment(heading, VPos.CENTER);

        List<Tokimon> tokimonList = getTokimonsFromServer();

        // Creates a 2D array of tokimon cards
        VBox[][] tokimonCards = getTokimonCards(tokimonList, actionBoard);

        // Add heading and tokimon cards to the display board
        actionBoard.add(heading, 0, 0, tokimonCards[0].length, 1); // Span the entire width
        for (int i = 0; i < tokimonCards.length; i++) {
            for (int j = 0; j < tokimonCards[i].length; j++) {
                if (tokimonCards[i][j] != null) {
                    actionBoard.add(tokimonCards[i][j], j, i + 1);
                }
            }
        }

        actionBoard.setPadding(new Insets(10));
        actionBoard.setVgap(20);
        actionBoard.setHgap(20);

        actionBoard.setAlignment(Pos.CENTER);
    }

    /**
     * This function returns a 2d array of tokimon cards
     * @param tokimonList a list of tokimons
     * @return a 2d array of vboxes (tokimon cards)
     */
    public VBox[][] getTokimonCards(List<Tokimon> tokimonList, GridPane actionBoard) {

        //Creates an array of tokimon cards
        VBox [] cards = new VBox[tokimonList.size()];
        for(int i=0; i<tokimonList.size(); i++) {
            Tokimon tokimon = tokimonList.get(i);
            VBox card = getVbox(tokimon, actionBoard);       //Creates a tokimon card
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
     * @param tokimon a tokimon object
     * @return vbox of a tokimon card
     */
    public VBox getVbox(Tokimon tokimon, GridPane actionBoard){


        //Creates a label and Hbox for tokimon name
        Label name = new Label(tokimon.getName());
        name.setFont(Font.font(fontName, FontWeight.BOLD, 30));
        HBox nameBox = new HBox(name);
        nameBox.setPadding(new Insets(0, 15, 10, 15));
        ImageView img = new ImageView(new Image(tokimon.getImagePath()));
        img.setFitHeight(100);
        img.setPreserveRatio(true);

        HBox controls = getControls(tokimon, actionBoard);

        // adding all the components to a vbox to make tokimon card
        VBox vbox = new VBox();
        vbox.getChildren().add(nameBox);
        vbox.getChildren().add(img);
        vbox.getChildren().add(controls);
        vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setPadding(new Insets(0,10,10,10));

        return vbox;
    }

    /**
     * This function creates a hbox for tokimon controls - (view tokimon or delete tokimon)
     * @param tokimon a tokimon
     * @param actionBoard grid pane where tokimon is displayed on the stage.
     * @return
     */
    private HBox getControls(Tokimon tokimon, GridPane actionBoard) {

        // controls to view tokimon details
        Button view = new Button("View");
        view.setFont(Font.font(fontName,18));
        view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewTokimonDetails(tokimon, actionBoard);
            }
        });

        // controls to delete tokimon
        Button delete = new Button("Delete");
        delete.setFont(Font.font(fontName,18));
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteTokimonFromServer(tokimon, actionBoard);
            }
        });

        HBox controls = new HBox(view, delete);
        controls.setSpacing(10);

        return controls;
    }

    /**
     * This function fetch all the tokimons from the server.
     * @return a list of all the tokimons.
     */
    public List<Tokimon> getTokimonsFromServer(){

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
            Gson gson = new Gson();
            Type tokimonListType = new TypeToken<List<Tokimon>>(){}.getType();
            tokimonList = gson.fromJson(jsonOutput.toString(), tokimonListType);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokimonList;
    }

    /**
     * This function acts as an event handler and deletes the tokimon from the server.
     * @param tokimon the tokimon needed to be deleted
     * @param actionBoard where the tokimon cards are being displayed
     */
    private void deleteTokimonFromServer(Tokimon tokimon, GridPane actionBoard) {
        try {
            URI uri = new URI("http://localhost:8080/tokimon/"+tokimon.getTid());
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Tokimon deleted successfully!");
                refreshTokimonCards(actionBoard);
            } else {
                System.out.println("Failed");
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this function refreshes the tokimon cards after deleting
     * @param actionBoard where the tokimon cards are being displayed
     */
    private void refreshTokimonCards(GridPane actionBoard) {
        actionBoard.getChildren().clear();
        displayAllTokiCards(actionBoard);
    }


    /**
     * This function displays the complete details of the tokimon
     * and allows users to edit and save tokimon information.
     * @param tokimon a tokimon
     * @param actionBoard   grid pane to display tokimon details.
     */
    public void viewTokimonDetails(Tokimon tokimon,GridPane actionBoard){

        ImageView img = new ImageView(new Image(tokimon.getImagePath()));
        img.setFitHeight(120);
        img.setPreserveRatio(true);

        Label name = new Label("Name:   ");
        Label type = new Label("Type:   ");
        Label rarity = new Label( "Rarity Score:   ");
        Label imagePath = new Label("Image Path:   ");

        //Creates text fields to let user edit the tokimon details
        TextField nameField = new TextField(tokimon.getName());
        TextField typeField = new TextField(tokimon.getType());
        TextField rarityField = new TextField(String.valueOf(tokimon.getRarityScore()));
        TextField imageField = new TextField(tokimon.getImagePath());

        //Allows only correct input to the text fields
        acceptStringInput(nameField);
        acceptStringInput(typeField);
        acceptIntegerInput(rarityField);

        Label messageLabel = new Label();
        messageLabel.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 15));

        Button saveChanges = new Button("Save");
        saveChanges.setFont(Font.font(fontName,18));
        saveChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (isValidImageURL(imageField.getText())) {
                    //Change the attributes of tokimon to the required values
                    tokimon.setName(nameField.getText());
                    tokimon.setType(typeField.getText());
                    tokimon.setRarityScore(Integer.parseInt(rarityField.getText()));
                    tokimon.setImagePath(imageField.getText());

                    editTokimon(tokimon, actionBoard);
                    messageLabel.setText("Saved!");
                    messageLabel.setFont(Font.font(fontName,20));
                    messageLabel.setStyle("-fx-text-fill: black;");

                } else {
                    messageLabel.setText("Invalid url.");
                    messageLabel.setFont(Font.font(fontName,20));//display error message
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        //Creates a tokimon card for display
        GridPane tokimonCard = new GridPane();
        tokimonCard.add(img, 0, 0);
        tokimonCard.add(name, 0, 1);
        tokimonCard.add(type, 0, 2);
        tokimonCard.add(rarity, 0, 3);
        tokimonCard.add(imagePath, 0, 4);
        tokimonCard.add(nameField, 1, 1);
        tokimonCard.add(typeField, 1, 2);
        tokimonCard.add(rarityField, 1, 3);
        tokimonCard.add(imageField, 1, 4);
        tokimonCard.add(saveChanges, 0, 5);
        tokimonCard.add(messageLabel, 0, 6);
        tokimonCard.setPadding(new Insets(20));
        tokimonCard.setVgap(10);
        tokimonCard.setHgap(10);

        //Creates a new window to display complete details of the tokimon
        Scene scene = new Scene(tokimonCard,400,450);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(tokimon.getName());
        stage.show();
    }

    /**
     * This function allow user to enter only alphabetical characters to a given text field.
     * @param textField a text field containing user input
     */
    public void acceptStringInput(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[a-zA-Z]*")){
                textField.setText(oldValue);
            }
        });
    }

    /**
     * This function allow user to input only integer value to a given text field.
     * @param textField a text field containing user input
     */
    public void acceptIntegerInput(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                textField.setText(oldValue);
            }
        });
    }

    /**
     * this function edits the tokimon.by the tid
     * @param tokimon the tokimon needed to be chnaged
     * @param actionBoard grid pane action board
     */
    private void editTokimon(Tokimon tokimon, GridPane actionBoard) {
        try {
            URI uri = new URI("http://localhost:8080/tokimon/edit/" + tokimon.getTid());
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(tokimon);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //statusLabel.setText("Changes saved!");
                System.out.println("Tokimon updated successfully!");
                refreshTokimonCards(actionBoard); // Refresh the tokimon cards

            } else {
                //statusLabel.setText("Failed to save changes.");
                System.out.println("Failed to update Tokimon. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


