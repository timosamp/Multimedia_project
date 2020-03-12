package Multimedia_project_front.MyMenus;

import Multimedia_project_front.MyMenus.MyMenuItems.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailsMenu extends Menu {

    public DetailsMenu(){

        /* Name of menu */
        this.setText("Details");

        MenuItem gates = new MenuItem("Gates");

        EventHandler<ActionEvent> eventGates = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //currentAirport = new Airport(sourceDirPath, nameOfFile);
                Stage parkingStage = new Stage();

                VBox vbox = new ParkingTable();
                Scene scene = new Scene(vbox);

                parkingStage.setTitle("Parking Spaces' Table");
                parkingStage.setScene(scene);
                parkingStage.show();

            }
        };

        gates.setOnAction(eventGates);


        MenuItem flights = new MenuItem("Flights");

        EventHandler<ActionEvent> eventFlights = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                Stage flightStage = new Stage();

                VBox vbox = new FlightsTable();
                Scene scene = new Scene(vbox);

                flightStage.setTitle("Flights' Table");
                flightStage.setScene(scene);
                flightStage.show();
            }
        };

        flights.setOnAction(eventFlights);


        MenuItem delayed = new MenuItem("Delayed");

        EventHandler<ActionEvent> eventDelatedFlights = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                Stage flightStage = new Stage();

                VBox vbox = new DelatedFlightsTable();
                Scene scene = new Scene(vbox);

                flightStage.setTitle("Delayed Flights' Table");
                flightStage.setScene(scene);
                flightStage.show();

            }
        };

        delayed.setOnAction(eventDelatedFlights);




        MenuItem holding = new MenuItem("Holding");

        EventHandler<ActionEvent> eventHoldingFlights = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                Stage flightStage = new Stage();

                VBox vbox = new HoldingFlightsTable();
                Scene scene = new Scene(vbox);

                flightStage.setTitle("Holding Flights' Table");
                flightStage.setScene(scene);
                flightStage.show();

            }
        };

        holding.setOnAction(eventHoldingFlights);




        MenuItem nextDepartures = new MenuItem("Next Departures");

        EventHandler<ActionEvent> eventNextDepartures = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                Stage flightStage = new Stage();

                VBox vbox = new NextFlightsTable();
                Scene scene = new Scene(vbox);

                flightStage.setTitle("Next Departures' Table");
                flightStage.setScene(scene);
                flightStage.show();

            }
        };

        nextDepartures.setOnAction(eventNextDepartures);


        // add menu items to Details menu
        this.getItems().add(gates);
        this.getItems().add(flights);
        this.getItems().add(delayed);
        this.getItems().add(holding);
        this.getItems().add(nextDepartures);

    }
}
