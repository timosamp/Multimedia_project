package Multimedia_project_front.MyMenus.MyMenuItems;

import Multimedia_project_back.Flight;
import Multimedia_project_back.parkingSpace.ParkingSpace;
import Multimedia_project_front.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;

public class ParkingTable extends VBox implements Observer{
    private TableView<ParkingSpace> table;
    private ObservableList<ParkingSpace> obsSpaces;
    private TableColumn<ParkingSpace, String> firstCol, secondCol;

    public ParkingTable() {

        Main.currentAirport.scheduler.addObserver(this);

        table = new TableView<ParkingSpace>();
        table.setEditable(true);

        obsSpaces = FXCollections.observableList(Main.currentAirport.getSpacesList());

        TableColumn<ParkingSpace, String> firstCol = new TableColumn<ParkingSpace, String>("ID");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<ParkingSpace, String>("ID"));
        firstCol.setStyle( "-fx-alignment: CENTER;");

        TableColumn<ParkingSpace, String> secondCol = new TableColumn<ParkingSpace, String>("Category");
        secondCol.setMinWidth(100);
        secondCol.setCellValueFactory(new PropertyValueFactory<ParkingSpace, String>("Category"));
        secondCol.setStyle( "-fx-alignment: CENTER;");

        TableColumn<ParkingSpace, String> thirdCol = new TableColumn<ParkingSpace, String>("Status");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<ParkingSpace, String>("Status"));
        thirdCol.setStyle( "-fx-alignment: CENTER;");

        TableColumn<ParkingSpace, String> fourthCol = new TableColumn<ParkingSpace, String>("Flight ID");
        fourthCol.setMinWidth(100);
        fourthCol.setCellValueFactory(new PropertyValueFactory<ParkingSpace, String>("flightID"));
        fourthCol.setStyle( "-fx-alignment: CENTER;");

        TableColumn<ParkingSpace, String> fifthCol = new TableColumn<ParkingSpace, String>("Leaving Time");
        fifthCol.setMinWidth(200);
        fifthCol.setCellValueFactory(new PropertyValueFactory<ParkingSpace, String>("leavingTime"));
        fifthCol.setStyle( "-fx-alignment: CENTER;");

        table.setItems(obsSpaces);
        table.getColumns().addAll(firstCol, secondCol, thirdCol, fourthCol, fifthCol);

        this.getChildren().add(table);
    }
//
    @Override
    public void update(Observable o, Object arg) {
        table.refresh();
    }
}
