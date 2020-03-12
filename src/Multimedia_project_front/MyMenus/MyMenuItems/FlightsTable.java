package Multimedia_project_front.MyMenus.MyMenuItems;

import Multimedia_project_back.Flight;
import Multimedia_project_front.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

public class FlightsTable extends VBox implements Observer{
    private TableView<Flight> table;
    private ObservableList<Flight> obsFlights;
    private TableColumn<Flight, String> firstCol, secondCol, thirdCol, fourthCol, fifthCol, sixthCol, seventhCol;

    public FlightsTable() {

        Main.currentAirport.scheduler.addObserver(this);

        table = new TableView<Flight>();
        table.setEditable(true);

        obsFlights = FXCollections.observableList(Main.currentAirport.scheduler.getAllFlights());


        firstCol = new TableColumn<Flight, String>("ID");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("ID"));
        firstCol.setStyle( "-fx-alignment: CENTER;");

        secondCol = new TableColumn<Flight, String>("City");
        secondCol.setMinWidth(100);
        secondCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("city"));
        secondCol.setStyle( "-fx-alignment: CENTER;");

        thirdCol = new TableColumn<Flight, String>("Flight Type");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightType"));
        thirdCol.setStyle( "-fx-alignment: CENTER;");

        fourthCol = new TableColumn<Flight, String>("Plane Type");
        fourthCol.setMinWidth(100);
        fourthCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("planeType"));
        fourthCol.setStyle( "-fx-alignment: CENTER;");

        fifthCol = new TableColumn<Flight, String>("Status");
        fifthCol.setMinWidth(100);
        fifthCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("status"));
        fifthCol.setStyle( "-fx-alignment: CENTER;");

        sixthCol = new TableColumn<Flight, String>("Parking Space ID");
        sixthCol.setMinWidth(150);
        sixthCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("parkingID"));
        sixthCol.setStyle( "-fx-alignment: CENTER;");

        seventhCol = new TableColumn<Flight, String>("Leaving Time");
        seventhCol.setMinWidth(200);
        seventhCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("leavingTime"));
        seventhCol.setStyle( "-fx-alignment: CENTER;");


        table.setItems(obsFlights);
        table.getColumns().addAll(firstCol, secondCol, thirdCol, fourthCol, fifthCol, sixthCol, seventhCol);


        this.getChildren().add(table);
    }
//
    @Override
    public void update(Observable o, Object arg) {
        table.refresh();
    }
}
