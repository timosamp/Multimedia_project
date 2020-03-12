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
public class GenParkingSpace extends ParkingSpace {
//    private String Category;
//    private String FlightType;
//    private String PlaneType;
//    private String Services;
//    private Integer ParkingMaxTime;
    
    public GenParkingSpace(){
        AddCategory("GeneralParkingSpace");    
        AddFlightType("Passenger");
        AddFlightType("Private");
        AddFlightType("Cargo");
        AddPlaneType("Jet");
        AddPlaneType("SingleMotor");
        AddPlaneType("TurboProp");
        AddService("Refueling");
        AddService("Cleaning");        
        setMaxParkingTime(240);
    };
    
}
