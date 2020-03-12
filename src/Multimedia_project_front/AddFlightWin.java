package Multimedia_project_front;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;

import java.util.UUID;

public class AddFlightWin extends GridPane{

//    private String flightType;
//    private String planeType;
    private ObservableList<String> selectedServices;
    final private ObservableList<String> avServices;
//    private Integer parkingTime;
//    private String status;
//    private String city;

    public AddFlightWin() {

        this.setMinSize(348, 230);

        Text flightTypeText = new Text("Flight Type");
        Text planeTypeText = new Text("Plane Type");
        Text cityText = new Text("City");
        Text parkingTimeText = new Text("Parking Time");
        Text reqServicesText = new Text("Required services");
        Text flightIDText = new Text("Flight ID");


        /* Create the bombo boxes */

        String listOfPlaneType[] = {"Jet", "SingleMotor", "TurboProp"};
        String listOfFlightType[] = {"Passenger", "Cargo", "Private"};

        
        ComboBox flightTypeIn = new ComboBox(FXCollections.observableArrayList((listOfFlightType)));
        flightTypeIn.getSelectionModel().select(1);

        ComboBox planeTypeIn = new ComboBox(FXCollections.observableArrayList(listOfPlaneType));
        planeTypeIn.getSelectionModel().select(1);

        /* Create the labels */

        TextField cityTextIn = new TextField("Chicago");
        TextField parkingTimeIn = new TextField("120");
        TextField flightIDIn = new TextField(UUID.randomUUID().toString().substring(0,8));


        Button submit = new Button("Submit");
        Button clear = new Button("Clear");


        /* General configuration */

        this.setPadding(new Insets(20, 20, 20, 20));

        //Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);

        //Setting the Grid alignment
        this.setAlignment(Pos.CENTER);


        /* Arranging all the nodes in the grid */

        this.add(flightIDText, 0, 0);
        this.add(flightIDIn, 1, 0);

        this.add(flightTypeText, 0, 1);
        this.add(flightTypeIn, 1, 1);

        this.add(planeTypeText, 0, 2);
        this.add(planeTypeIn, 1, 2);

        this.add(parkingTimeText, 0, 3);
        this.add(parkingTimeIn, 1, 3);

        this.add(cityText, 0, 4);
        this.add(cityTextIn, 1, 4);




        // create the data to show in the CheckComboBox
        avServices = FXCollections.observableArrayList();
        selectedServices = FXCollections.observableArrayList();

        avServices.add("Refuel");
        avServices.add("Cleaning");
        avServices.add("Passenger Transfer");
        avServices.add("Loading");

        // Create the CheckComboBox with the data
        final CheckComboBox<String> checkBoxServices = new CheckComboBox<String>(avServices);

        // and listen to the relevant events (e.g. when the selected indices or
        // selected items change).
        checkBoxServices.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {

//                System.out.println(checkBoxServices.getCheckModel().getCheckedItems());

               selectedServices = FXCollections.observableArrayList(checkBoxServices.getCheckModel().getCheckedItems());

               /* For test */
//                System.out.println(selectedServices);
            }
        });


        this.add(reqServicesText, 0, 5);
        this.add(checkBoxServices, 1, 5);



        EventHandler<ActionEvent> eventSubmit = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                if(!(flightTypeIn.getValue().toString().isEmpty() || planeTypeIn.getValue().toString().isEmpty() || cityTextIn.getText().isEmpty()
                    || parkingTimeIn.getText().isEmpty() || selectedServices.isEmpty())){

                    try {

                        /*  Add new flight  */
                        Main.currentAirport.scheduler.addFlight(flightIDIn.getText(), cityTextIn.getText(),
                                flightTypeIn.getValue().toString(), planeTypeIn.getValue().toString(),
                                Integer.parseInt(parkingTimeIn.getText()));


                        /*  Print All flights  */
//                        Main.currentAirport.scheduler.printAllFlights();

                        /*  Print new flight  */
//                        System.out.println(flightIDIn.getText() + " " + cityTextIn.getText()+ " " + flightTypeIn.getText()+ " " +
//                                planeTypeIn.getText()+ " " +  Integer.parseInt(parkingTimeIn.getText()));

                        /* Reset default value of id field */
                        flightIDIn.setText(UUID.randomUUID().toString().substring(0,8));

                    }catch (Exception e1) {
                        System.out.println("First load the scenario and then press the start button");
                        //e1.printStackTrace();
                    }


                }else{
                    System.out.println("You must to fill all the required informations in order to add a flight!");
                }

            }
        };

        EventHandler<ActionEvent> eventClear = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){

            }
        };


        submit.setOnAction(eventSubmit);
        clear.setOnAction(eventClear);


        this.add(submit, 0, 6);
//        this.add(clear, 1, 6);

        //Styling nodes
        parkingTimeText.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        flightIDText.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        reqServicesText.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        flightTypeText.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        planeTypeText.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        cityText.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        flightTypeText.setStyle("-fx-font: normal bold 14px 'serif' ");
        flightIDText.setStyle("-fx-font: normal bold 14px 'serif' ");
        planeTypeText.setStyle("-fx-font: normal bold 14px 'serif' ");
        cityText.setStyle("-fx-font: normal bold 14px 'serif' ");
        parkingTimeText.setStyle("-fx-font: normal bold 14px 'serif' ");
        reqServicesText.setStyle("-fx-font: normal bold 14px 'serif' ");

        this.setStyle("-fx-background-color: BEIGE;");


    }

}
