package Multimedia_project_front;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Observable;
import java.util.Observer;


public class ImportantInfoWinGeneric extends GridPane implements Observer {

    Integer arrivedFlights;
    Integer freeParkingSpaces;
    Integer futureDepartures;
    Double TotalCost;
    String AppTime;

    Text arrivedFlightsMes;
    Text freeParkingSpacesMes;
    Text futureDeparturesMes;
    Text AppTimeMes;
    Text TotalCostMes;

    Text arrivedFlightsLabel;
    Text freeParkingSpacesLabel;
    Text futureDeparturesLabel;
    Text AppTimeLabel;
    Text TotalCostLabel;


    public ImportantInfoWinGeneric() {

        Main.currentAirport.scheduler.addObserver(this);

        /* Create the labels */
        arrivedFlightsLabel = new Text("No. of arrived flights:    ");
        freeParkingSpacesLabel = new Text("No. of free Parking Spaces:  ");
        futureDeparturesLabel = new Text("No. of flights will be departed into the next 10 min: ");
        TotalCostLabel = new Text("Total amount of money earned:    ");
        AppTimeLabel = new Text("Time since Application has started:    ");

        /* Create the values */
        arrivedFlights = Main.currentAirport.scheduler.getFlightsWithStatus("Parked").size();
        freeParkingSpaces = Main.currentAirport.scheduler.getParkingSpaceWithStatus("Available").size();
        futureDepartures = Main.currentAirport.scheduler.getNextDepartures(10 * 60).size();
        TotalCost = Main.currentAirport.scheduler.getTotalCost();
        AppTime = Main.currentAirport.scheduler.getRunningTime();

        /* Create the messages */
        arrivedFlightsMes = new Text(arrivedFlights.toString());
        freeParkingSpacesMes = new Text(freeParkingSpaces.toString());
        futureDeparturesMes = new Text(futureDepartures.toString());
        TotalCostMes = new Text(TotalCost.toString());
        AppTimeMes = new Text(AppTime);


        /* Organize the GridPane */

//        this.setMinSize(400, 200);

        this.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        //Setting the Grid alignment
        this.setAlignment(Pos.CENTER);


        //Arranging all the nodes in the grid
        this.add(arrivedFlightsLabel, 0, 0);
        this.add(freeParkingSpacesLabel, 0, 1);
        this.add(futureDeparturesLabel, 0, 2);
        this.add(TotalCostLabel, 0, 3);
        this.add(AppTimeLabel, 0, 4);


        //Arranging all the nodes in the grid
        this.add(arrivedFlightsMes, 2, 0);
        this.add(freeParkingSpacesMes, 2, 1);
        this.add(futureDeparturesMes, 2, 2);
        this.add(TotalCostMes,2, 3);
        this.add(AppTimeMes, 2, 4);

        /* Center the Answers */
        GridPane.setHalignment(arrivedFlightsMes, HPos.CENTER);
        GridPane.setHalignment(freeParkingSpacesMes, HPos.CENTER);
        GridPane.setHalignment(futureDeparturesMes, HPos.CENTER);
        GridPane.setHalignment(TotalCostMes, HPos.CENTER);
        GridPane.setHalignment(AppTimeMes, HPos.CENTER);


        /* Center all the Texts*/
//        for(Node temp : this.getChildren()){
//            GridPane.setHalignment(temp, HPos.CENTER);
//        }

    }

    private void refreshQueriesResult(){

        /* Create the values */
        arrivedFlights = Main.currentAirport.scheduler.getFlightsWithStatus("Parked").size();
        freeParkingSpaces = Main.currentAirport.scheduler.getParkingSpaceWithStatus("Available").size();
        futureDepartures = Main.currentAirport.scheduler.getNextDepartures(10 * 60).size();
        TotalCost = Main.currentAirport.scheduler.getTotalCost();
        AppTime = Main.currentAirport.scheduler.getRunningTime();


        /* Create the messages */
        arrivedFlightsMes.setText(arrivedFlights.toString());
        freeParkingSpacesMes.setText(freeParkingSpaces.toString());
        futureDeparturesMes.setText(futureDepartures.toString());
        TotalCostMes.setText(TotalCost.toString());

        /* First way */
        AppTimeMes.setText(AppTime);
//        System.out.println(AppTime);

        /* Other way */
//        ((Text)this.getChildren().get(8)).setText(AppTime);


    }

    @Override
    public void update(Observable o, Object arg) {
//        System.out.println("Update info panel //////////////////");
        synchronized (this) {
            refreshQueriesResult();
        }
    }
}
