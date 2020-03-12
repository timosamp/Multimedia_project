package Multimedia_project_front;

import Multimedia_project_back.parkingSpace.ParkingSpace;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class ParkingSpaceOutput extends GridPane implements Observer {

    Double x = 0.0;
    Double y = 0.0;
    Double offset = 2.0;
    List<VBox> vboxesList;
    VBox tempVbox;


    public ParkingSpaceOutput() {

        this.setMinSize(348, 230);

        Text airportStatus = new Text("Airport status");

        /* Init the list of vboxes */
        vboxesList = new ArrayList<VBox>();

        /* Observe all the list -- https://www.geeksforgeeks.org/java-util-observable-class-java/ */
        for (ParkingSpace parkingSpace : Main.currentAirport.getSpacesList()) {
            parkingSpace.addObserver(this);
        }

        createGridPane();

        this.setPadding(new Insets(2, 2, 2, 2));


        //Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        //Setting the Grid alignment
        this.setAlignment(Pos.CENTER);

//        this.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        /* Implementing moving */
        this.setOnMousePressed((event) -> {
            this.x = event.getX();
            this.y = event.getY();
        });

        this.setOnMouseDragged((event) -> {

            if (event.getX() - this.x > offset) {
                this.setTranslateX(this.getTranslateX() + Math.max(event.getX() - this.x, offset));
            }

            if (event.getX() - this.x < -offset) {
                this.setTranslateX(this.getTranslateX() + Math.min(event.getX() - this.x, -offset));
            }

            if (event.getY() - this.y > offset) {
                this.setTranslateY(this.getTranslateY() + Math.max(event.getY() - this.y, offset));
            }

            if (event.getY() - this.y < -offset) {
                this.setTranslateY(this.getTranslateY() + Math.min(event.getY() - this.y, -offset));
            }

        });


        /* Implementing Zoom */
        this.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();

            if (Math.abs(deltaY) > 0.1) {

                if (deltaY < 0 && Math.abs(deltaY) > 0.1) {
                    zoomFactor = 2.0 - zoomFactor;
                }

                this.setScaleX(this.getScaleX() * zoomFactor);
                this.setScaleY(this.getScaleY() * zoomFactor);
            }
        });


    }

    public void createGridPane(){

        /* Place the parking spaces into the GridPane */
        int i = 0, j = 0;
//        Text text;

        String prevCategory = "";

        for (ParkingSpace temp : Main.currentAirport.getSpacesList()) {

            /* Create a vbox for the parking space */
            tempVbox = ParkingSpaceSpecs(temp);
            tempVbox.setId(temp.getID());

            /* Save this vbox to a list of vboxs */
            vboxesList.add(tempVbox);

            if (prevCategory.equals(temp.getCategory())) {
                j++;
            } else {
                i++;
                j = 1;

                /* Compatibility reasons */
//                text = new Text(temp.getCategory());
//                text.setId("");

                this.add(new Text(temp.getCategory()), i, 0);
            }

            prevCategory = temp.getCategory();

            this.add(tempVbox, i, j);
//            System.out.println("j: " + j + "i: " + i);
        }
    }

    private void changeColor(ParkingSpace parkingSpace){

        /* Styles */
        String unavailable = "-fx-background-color: darkred;";
        String available = "-fx-background-color: green";

        /* Search for specific vbox */
        for (Node tempVbox : this.getChildren()) {

            if (parkingSpace.getID().equals(tempVbox.getId())){

                if (parkingSpace.getStatus().equals("Available")) {
                    tempVbox.setStyle(available);
                } else {
                    tempVbox.setStyle(unavailable);
                }

                /* Change flight ID*/
                ((Text)(((VBox)tempVbox).getChildren().get(2))).setText("Flight ID:" + parkingSpace.getFlightID());

                /* Test */
                //System.out.println(tempVbox.getId());
            }

        }

    }


    @Override
    public void update(java.util.Observable o, Object arg) {
//        System.out.println("Update-------------------------");
        synchronized (this) {
            changeColor((ParkingSpace) o);
        }

        /* If you want to update the whole gridpane*/
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Update1-------------------------");
////                Main.sp4.getChildren().get(3).setStyle("-fx-background-color: red");
//                Main.sp4.getChildren().clear();
//                Main.sp4.createGridPane();
//
//            }
//        });
    }




    private VBox ParkingSpaceSpecs(ParkingSpace parkingSpace) {

        String flightID = parkingSpace.getFlightID();
        String status = parkingSpace.getStatus();
        String category = parkingSpace.getCategory();
        String id = parkingSpace.getID();

        String unavailable = "-fx-background-color: darkred;";
        String available = "-fx-background-color: green";
        String style = "-fx-font: normal bold ";
        style = style.concat(String.valueOf(2)).concat("px 'serif' ");

        Text categoryText = new Text("Category: " + category);
        Text idText = new Text("ID: " + id);
        Text flightIDText = new Text("Flight ID: " + flightID);


//        statusText.setStyle(style);
//        categoryText.setStyle(style);
//        idText.setStyle(style);

        VBox vb = new VBox();
        vb.getChildren().addAll(categoryText, idText, flightIDText);


        if (status.equals("Available")) {
            vb.setStyle(available);
        } else {
            vb.setStyle(unavailable);
        }

        vb.setAlignment(Pos.CENTER);
//        vb.setMaxSize(5, 5);

        return vb;
    }

}
