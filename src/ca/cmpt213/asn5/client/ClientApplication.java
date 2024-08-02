package ca.cmpt213.asn5.client;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    // Load the custom font
    Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 40);

    // Use the font name defined in the font file
    String fontName = "VT323"; // Replace with the actual font name if different

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
                actionBoard.setPadding(Insets.EMPTY); // Clear padding
                actionBoard.getChildren().clear(); // Clear the grid pane before displaying all the tokimons
                displayAllTokiCards(actionBoard); // Display all the tokimons on the action board
            }
        });

        Button addTokimons = new Button("Add Tokimons");
        addTokimons.setFont(Font.font(fontName,18));
        addTokimons.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                actionBoard.setPadding(Insets.EMPTY); // Clear padding
                actionBoard.getChildren().clear(); // Clear the grid pane before displaying all the tokimons
                addTokimonControls(actionBoard); // Display the add tokimons controls on the action board
            }
        });

        // Creates an initial control board for tokimon database
        GridPane controlBoard = new GridPane();
        controlBoard.add(showTokimons, 0, 0);
        controlBoard.add(addTokimons, 1, 0);
        controlBoard.setHgap(10);
        controlBoard.setVgap(10);
        controlBoard.setPadding(new Insets(10));
        controlBoard.setAlignment(Pos.TOP_CENTER); // Center the control board contents

        // Create an HBox for the header and set alignment
        HBox headerBox = new HBox(heading);
        headerBox.setAlignment(Pos.TOP_CENTER);
        headerBox.setPadding(new Insets(50, 0, 10, 0)); // Padding at the top for spacing

        // Adds header, control board, and action board to VBox
        VBox vbox = new VBox(headerBox, controlBoard, actionBoard);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.TOP_CENTER); // Center contents in VBox

        // Sets a scene
        Scene scene = new Scene(vbox, 1200, 800);

        // Set and show the stage
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
        actionBoard.setHgap(10); // Horizontal gap between columns
        actionBoard.setVgap(10); // Vertical gap between rows
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

        imageTextField.setPrefWidth(300);
        nameTextField.setPrefWidth(300);
        typeTextField.setPrefWidth(300);
        rarityTextField.setPrefWidth(300);

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

        // Label for displaying messages
        Label messageLabel = new Label();
        messageLabel.setFont(Font.font(fontName, FontWeight.BOLD, FontPosture.REGULAR, 15));

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isValidImageURL(imageTextField.getText())) {
                    addTokimonToServer(imageTextField, nameTextField, typeTextField, rarityTextField, actionBoard);
                    messageLabel.setText("Tokimon added successfully!");
                    messageLabel.setStyle("-fx-text-fill: black;");
                } else {
                    messageLabel.setText("Invalid url, please try again");
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

        // Center the submit button
        GridPane.setHalignment(submit, HPos.CENTER);
        // Center the message label
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
     * @param imageTextField text field containing image path of the tokimon
     * @param nameTextField text field containing name of the tokimon
     * @param typeTextField text field containing type of the tokimon
     * @param rarityTextField text field containing rarity of the tokimon
     * @param actionBoard grid pane to display
     */
    public void addTokimonToServer(TextField imageTextField, TextField nameTextField, TextField typeTextField, TextField rarityTextField, GridPane actionBoard){

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

        // Center the heading in its cell
        GridPane.setHalignment(heading, HPos.CENTER);
        GridPane.setValignment(heading, VPos.CENTER);

        // Creates a list of tokimons
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

        // Ensure the actionBoard is centered in its parent container
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

        // Load the custom font
//        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 40);
//        if (font != null) {
//            System.out.println("Font loaded successfully.");
//        } else {
//            System.out.println("Font loading failed.");
//        }
        //Label for name
        Label name = new Label(tokimon.getName());



        // Use the font object for the Label
        name.setFont(Font.font(fontName, FontWeight.BOLD, 30));

        HBox nameBox = new HBox(name);
        nameBox.setPadding(new Insets(0, 15, 10, 15));

        //Imageview for tokimon image
        ImageView img = new ImageView(new Image(tokimon.getImagePath()));
        //System.out.println(tokimon.getImagePath());
        img.setFitHeight(100);
        img.setPreserveRatio(true);

        //Controls to display full tokimon data and delete tokimon card
        HBox controls = getControls(tokimon, actionBoard);

        //Adding all the components to a vbox to make tokimon card
        VBox vbox = new VBox();
        vbox.getChildren().add(nameBox);
        vbox.getChildren().add(img);
        vbox.getChildren().add(controls);

        return vbox;

    }

    /**
     * This function creates a hbox for tokimon controls - (view tokimon or delete tokimon)
     * @param tokimon a tokimon
     * @param actionBoard grid pane where tokimon is displayed on the stage.
     * @return
     */
    private HBox getControls(Tokimon tokimon, GridPane actionBoard) {

        //Controls to view tokimon details
        Button view = new Button("View");
        view.setFont(Font.font(fontName,18));
        view.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewTokimonDetails(tokimon, actionBoard);
            }
        });

        //Controls to delete tokimon
        Button delete = new Button("Delete");
        delete.setFont(Font.font(fontName,18));
        //delete tokimon from the server
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteTokimonFromServer(tokimon, actionBoard);
            }
        });

        //Creates a hbox containing view and delete tokimon controls
        HBox controls = new HBox(view, delete);
        controls.setSpacing(10);

        return controls;
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
        actionBoard.getChildren().clear(); // Clear the grid pane before displaying all the tokimons
        displayAllTokiCards(actionBoard);  // Display all the tokimons on the action board
    }


    /**
     * This function displays the complete details of the tokimon
     * and allows users to edit and save tokimon information.
     * @param tokimon a tokimon
     * @param actionBoard   grid pane to display tokimon details.
     */
    public void viewTokimonDetails(Tokimon tokimon,GridPane actionBoard){

        //Create an imageview for tokimon image
        ImageView img = new ImageView(new Image(tokimon.getImagePath()));
        img.setFitHeight(120);
        img.setPreserveRatio(true);

        //Create label for name,type and rarity
        Label name = new Label("Name:   ");
        Label type = new Label("Type:   ");

        Label rarity = new Label( "Rarity Score:   ");


        //Creates text fields to let user edit the tokimon details
        TextField nameField = new TextField(tokimon.getName());
        TextField typeField = new TextField(tokimon.getType());
        TextField rarityField = new TextField(String.valueOf(tokimon.getRarityScore()));
        TextField imageField = new TextField(tokimon.getImagePath());


//        HBox nameBox = new HBox(name, nameField);
//        HBox typeBox = new HBox(type,typeField);
//        HBox rarityBox = new HBox(rarity,rarityField);


        //Label to let user know that their changes has been saved.
        Label statusLabel = new Label();

        //Button to save changes
        Button saveChanges = new Button("Save Changes");
        saveChanges.setFont(Font.font(fontName,18));
        saveChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                tokimon.setName(nameField.getText());
                tokimon.setType(typeField.getText());
                tokimon.setRarityScore(Integer.parseInt(rarityField.getText()));
                tokimon.setImagePath(imageField.getText());


                editTokimon(tokimon, actionBoard, statusLabel);     //Edit tokimon details on the server.
            }
        });


//        VBox vbox = new VBox(img,nameBox,typeBox,rarityBox,editChanges, statusLabel);
//        vbox.setSpacing(10);
//        vbox.setPadding(new Insets(10, 50, 10, 50));

        //Creates a tokimon card for display
        GridPane tokimonCard = new GridPane();
        tokimonCard.add(img, 0, 0);
        tokimonCard.add(name, 0, 1);
        tokimonCard.add(type, 0, 2);
        tokimonCard.add(rarity, 0, 3);
        tokimonCard.add(nameField, 1, 1);
        tokimonCard.add(typeField, 1, 2);
        tokimonCard.add(rarityField, 1, 3);
        tokimonCard.add(saveChanges, 0, 4);
        tokimonCard.add(statusLabel, 0, 5);
        tokimonCard.setPadding(new Insets(20));
        tokimonCard.setVgap(10);
        tokimonCard.setHgap(10);

        //Creates a new window to display complete details of the tokimon
        Scene scene = new Scene(tokimonCard,300,350);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(tokimon.getName());
        stage.show();
    }

    /**
     * this function edits the tokimon.by the tid
     * @param tokimon the tokimon needed to be chnaged
     * @param actionBoard grid pane action board
     * @param statusLabel the 'success' text after user clicks the save changes button
     */
    private void editTokimon(Tokimon tokimon, GridPane actionBoard, Label statusLabel) {
        try {
            URI uri = new URI("http://localhost:8080/tokimon/edit/" + tokimon.getTid());
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Convert the updated Tokimon object to JSON
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(tokimon);

            // Write JSON input string to the connection output
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                statusLabel.setText("Changes saved!");
                System.out.println("Tokimon updated successfully!");
                refreshTokimonCards(actionBoard); // Refresh the tokimon cards

            } else {
                statusLabel.setText("Failed to save changes.");
                System.out.println("Failed to update Tokimon. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


