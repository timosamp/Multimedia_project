package Multimedia_project_front;

import Multimedia_project_back.Airport;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {


    public static Airport currentAirport; // It is going to be initiated by "Start" Button
    public static Stage primaryStage;
    public static boolean startingFlag;

    public static void afterStart() throws IOException {

        //Creating the main panel
        BorderPane root = new BorderPane();

        //Creating Menu bar
        MyMenuBarItems menuBar = new MyMenuBarItems();

        /* --------------------------------- The top part ------------------------------------------- */

        //Plane Top
//        final StackPane sp1 = new StackPane();
//        GridPane sp1 = new ImportantInfoWin();
        VBox sp1 = new ImportantInfoWin();


        /* --------------------------------- The middle part ------------------------------------------- */
        //Split The Center Panel into three parts
        SplitPane sp2 = new SplitPane();


        //Plane Center Left - "Start" Button is going to initiate the panel
        ParkingSpaceOutput sp4 = new ParkingSpaceOutput();
//        sp4.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.80));

        //Plane Center Right
        GridPane sp5 = new AddFlightWin();

        //Add Left and Right Panels to The Center Panel
        sp2.getItems().addAll(sp4, sp5);
        sp2.setDividerPositions(0.5, 1);


        /* --------------------------------- The bottom part ------------------------------------------- */

        //Plane Down -- logs
        BorderPane sp3 = new MonitorLogs();
//        final Parent sp3 = FXMLLoader.load(Main.class.getResource("LogsConsole.fxml"));


        //Split Center body of BorderPane into three vertical parts
        SplitPane sp = new SplitPane();
        sp.getItems().addAll(sp1, sp2, sp3);
        sp.setDividerPositions(0.33f, 0.66f, 0.99f);
        sp.setOrientation(Orientation.VERTICAL);

        root.setTop(menuBar);
        root.setCenter(sp);

        Scene scene = new Scene(root, 700, 600);

        Integer columns = 7; // as the categories
        DoubleProperty fontSize = new SimpleDoubleProperty(1);
        fontSize.bind(scene.widthProperty().divide(columns * 25));
        sp4.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));


        //Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();

        /* Now you can Start the backend */
        Main.currentAirport.scheduler.startSceduler();

    }

    public static void beforeStart() throws Exception {

        /* Init Starting Flag to false */
        startingFlag = false;

        //Creating the main panel
        BorderPane root = new BorderPane();

        //Creating the Menu Bar
        MyMenuBarBegin menuBar = new MyMenuBarBegin();

        //Create Main message
        Text welcome = new Text("Multimedia Project");
        welcome.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        welcome.setStyle("-fx-font: normal bold 25px 'serif' ");

        //Create Console Monitor
        MonitorLogs monCon = new MonitorLogs();

        //Add them to root (BorderPane)
        root.setTop(menuBar);
        root.setCenter(welcome);
        root.setBottom(monCon);
//        root.setBottom(FXMLLoader.load(Main.class.getResource("LogsConsole.fxml")));


        /* Update monitor */
        System.out.println("\n1)  Load your airport's scenario files by choosing the directory where are included.");
        System.out.println("     Additionally write their SCENARIO-ID in the according field.\n");
        System.out.println("2)  Then press start.\n");


        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void start(Stage _primaryStage) throws Exception {

        primaryStage = _primaryStage;
        beforeStart();

//        MyMenuBar mymenu = new MyMenuBar();
//        mymenu.start(stage);
    }

    @Override
    public void stop() throws InterruptedException {

        /* Wait */
        //TimeUnit.SECONDS.sleep(2);

        /* Save files, logs before closing */

        /* Close the app */
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);

    }

}