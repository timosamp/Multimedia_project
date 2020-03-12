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

public class HoldingFlightsTable extends VBox implements Observer {
    private TableView<Flight> table;
    private ObservableList<Flight> obsFlights;
    private TableColumn<Flight, String> firstCol, secondCol, thirdCol, fourthCol;

    public HoldingFlightsTable() {

        Main.currentAirport.scheduler.addObserver(this);

        table = new TableView<Flight>();
        table.setEditable(true);

        obsFlights = FXCollections.observableList(Main.currentAirport.scheduler.getFlightsWithStatus("Holding"));

        firstCol = new TableColumn<Flight, String>("ID");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("ID"));
        firstCol.setStyle( "-fx-alignment: CENTER;");

        secondCol = new TableColumn<Flight, String>("Flight Type");
        secondCol.setMinWidth(100);
        secondCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightType"));
        secondCol.setStyle( "-fx-alignment: CENTER;");

        thirdCol = new TableColumn<Flight, String>("Plane Type");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("planeType"));
        thirdCol.setStyle( "-fx-alignment: CENTER;");

        fourthCol = new TableColumn<Flight, String>("Time of Landing Request");
        fourthCol.setMinWidth(200);
        fourthCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("requestTime"));
        fourthCol.setStyle( "-fx-alignment: CENTER;");
        

        table.setItems(obsFlights);
        table.getColumns().addAll(firstCol, secondCol, thirdCol, fourthCol);

        this.getChildren().add(table);
    }

    @Override
    public void update(Observable o, Object arg) {
        obsFlights = FXCollections.observableList(Main.currentAirport.scheduler.getFlightsWithStatus("Holding"));
        table.setItems(obsFlights);
        table.refresh();
    }
}
