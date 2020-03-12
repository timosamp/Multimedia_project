package Multimedia_project_front;

import Multimedia_project_back.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Observable;
import java.util.Observer;


public class ImportantInfoWin extends VBox implements Observer {

    private TableView<Log> table;
    private ObservableList<Log> obsFlights;
    private TableColumn<Log, String> firstCol, secondCol;


    public ImportantInfoWin() {

        Main.currentAirport.scheduler.addObserver(this);

        table = new TableView<Log>();
        table.setEditable(true);

        obsFlights = FXCollections.observableList(Main.currentAirport.scheduler.getLogslist());

        firstCol = new TableColumn<Log, String>("Question");
        firstCol.setMinWidth(350);
        firstCol.setCellValueFactory(new PropertyValueFactory<Log, String>("label"));
        firstCol.setStyle( "-fx-alignment: CENTER-LEFT;");

        secondCol = new TableColumn<Log, String>("Info");
        secondCol.setMinWidth(348);
        secondCol.setCellValueFactory(new PropertyValueFactory<Log, String>("Info"));
        secondCol.setStyle( "-fx-alignment: CENTER;");


        table.setItems(obsFlights);
        table.getColumns().addAll(firstCol, secondCol);


        this.getChildren().add(table);
    }

    //
    @Override
    public void update(Observable o, Object arg) {
        table.refresh();
    }
}
