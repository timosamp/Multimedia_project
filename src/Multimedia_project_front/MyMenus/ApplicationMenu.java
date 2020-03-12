package Multimedia_project_front.MyMenus;

import Multimedia_project_back.Airport;
import Multimedia_project_front.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ApplicationMenu extends Menu {
    private String nameOfFile;
    private String sourceDirPath;
    private boolean hasBeenLoaded;
    private Airport currentAirport; // (only for tests)

    public ApplicationMenu() {

        /* Set the flag */
        hasBeenLoaded = false;

        /* Name of menu */
        this.setText("Application");

        //create its items
        MenuItem start = new MenuItem("Start");
        MenuItem load = new MenuItem("Load");
        MenuItem exit = new MenuItem("Exit");

        //Set Action for Load
        EventHandler<ActionEvent> eventLoad = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                //State of load
                Stage loadStage = new Stage();

                /* Get current direcory path */
                Path path = FileSystems.getDefault().getPath(".");
                String pathToFile = path.toString().concat("/example");

                //System.out.println(pathToFile); //for test

                Text fileNameOut = new Text("Scenario ID");
                TextField fileNameIn = new TextField("default");
                Text dirPathOut = new Text("Source Directory");
                TextField dirPathIn = new TextField(pathToFile);

                Button openButton = new Button("Open");
                Button okButton = new Button("OK");

                EventHandler<ActionEvent> eventOpen = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {

                        DirectoryChooser directoryChooser = new DirectoryChooser();
                        File selectedDirectory = directoryChooser.showDialog(loadStage);

                        if (selectedDirectory != null) {
                            dirPathIn.setText(selectedDirectory.getAbsolutePath() + "/");
                            System.out.println("Path to source directory: " + selectedDirectory.getAbsolutePath());
                        } else {
                            System.out.println("This path is not valid");
                        }
                    }
                };

                EventHandler<ActionEvent> eventOK = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {

                        if (Main.startingFlag) {
                            /* For user's logs */
                            System.out.println("You loaded a new scenario ID");

                            /* Close the window */
                           // ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
                        }

                        if (!(dirPathIn.getText().isEmpty()) && !(fileNameIn.getText().isEmpty())) {
                            nameOfFile = fileNameIn.getText();
                            sourceDirPath = dirPathIn.getText().concat("/");
                            System.out.println("nameNameIn: " + nameOfFile + "\n" + "dirPathIn: " + sourceDirPath);
                            hasBeenLoaded = true;
                            loadStage.close();
                        } else {
                            System.out.println("Path or Name field is emprty");
                        }
                    }
                };

                okButton.setOnAction(eventOK);
                openButton.setOnAction(eventOpen);

                GridPane gridPane = new GridPane();

                gridPane.setMinSize(400, 200);

                gridPane.setPadding(new Insets(10, 10, 10, 10));

                //Setting the vertical and horizontal gaps between the columns
                gridPane.setVgap(5);
                gridPane.setHgap(5);

                //Setting the Grid alignment
                gridPane.setAlignment(Pos.CENTER);

                //Arranging all the nodes in the grid
                gridPane.add(fileNameOut, 0, 0);
                gridPane.add(fileNameIn, 1, 0);
                gridPane.add(dirPathOut, 0, 1);
                gridPane.add(dirPathIn, 1, 1);
                gridPane.add(openButton, 0, 2);
                gridPane.add(okButton, 1, 2);


                Scene scene = new Scene(gridPane);

                loadStage.setTitle("Load source directory");
                loadStage.setScene(scene);
                loadStage.show();
            }
        };


        //set action for start
        EventHandler<ActionEvent> eventStart = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                try {

                    if (hasBeenLoaded) {

                        if(Main.startingFlag) {

                            /* For user's logs */
                            System.out.println("The application will be restarted depend on the new scenario");

                            /* Stop timer */
                            Main.currentAirport.scheduler.stopTimer();

                        }

                        /* Construct the airport's object */
                        Main.currentAirport = new Airport(sourceDirPath, nameOfFile);
                        System.out.println("Airport is constructed!");

                        /* Load the ew scene */
                        Main.afterStart();

                        /* Set the flag true to avoid reinstallation of timer and other problems */
                        Main.startingFlag = true;
                    } else {
                        System.out.println("You haven't load a scenario");
                    }

                } catch (Exception ex) {
//                    ex.printStackTrace();
                    System.out.println(ex.toString());
                }

            }
        };

        //Set exit action
        EventHandler<ActionEvent> eventExit = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage exitStage = new Stage();

                String areyousure = "Are you sure that you want to close this application";

                Button noButton = new Button("No");
                Button yesButton = new Button("Yes");


                //handlers
                EventHandler<ActionEvent> eventNo = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                    }
                };

                EventHandler<ActionEvent> eventYes = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("App is closing");
                        System.out.flush();
                        Platform.exit();
                    }
                };

                //add them to the according buttons
                noButton.setOnAction(eventNo);
                yesButton.setOnAction(eventYes);

                //Panel construction
                GridPane gridPane = new GridPane();

                gridPane.setMinSize(400, 100);

                gridPane.setPadding(new Insets(10, 10, 10, 10));

                //Setting the vertical and horizontal gaps between the columns
                gridPane.setVgap(5);
                gridPane.setHgap(5);

                //Setting the Grid alignment
                gridPane.setAlignment(Pos.CENTER);

                //Arranging all the nodes in the grid
                gridPane.add(yesButton, 0, 1);
                gridPane.add(noButton, 5, 1);

                //Create a scene
                Scene scene = new Scene(gridPane);

                exitStage.setTitle(areyousure);
                exitStage.setScene(scene);
                exitStage.show();

            }
        };

        load.setOnAction(eventLoad);
        start.setOnAction(eventStart);
        exit.setOnAction(eventExit);

        // add menu items to Application menu
        this.getItems().add(start);
        this.getItems().add(load);
        this.getItems().add(exit);

    }
}
