/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_project_back.parkingSpace;

/**
 *
 * @author ritos
 */
public class CargoGate extends ParkingSpace {
//    private String Category;
//    private String FlightType;
//    private String PlaneType;
//    private String Services;
//    private Integer ParkingMaxTime;
    
    public CargoGate(){
        AddCategory("CargoGate");    
        AddFlightType("Cargo");    
        AddPlaneType("Jet");
        AddPlaneType("TurboProp");
        //AddService("all");
        AddService("Refueling");
        AddService("Cleaning"); 
        AddService("Loading/Unloading");
        AddService("PassengerTransport");
        setMaxParkingTime(90);
    };
    
}
