/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_project_back.parkingSpace;
import Multimedia_project_back.Flight;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;
/**
 *
 * @author ritos
 */
public class ParkingSpace extends Observable {
    private String Category;
    private final List<String> FlightTypes = new ArrayList<>();
    private final List<String> PlaneTypes = new ArrayList<>();
    private final List<String> Services = new ArrayList<>();
    private Integer MaxParkingTime;
    private String prefixID;
    private Integer numID;
    private String Status;
    private Integer Cost;
    private Flight flight;
    private String flightID;
    private String leavingTime;

    /*  Somehow we need to initialize the Status to available for all the childrens */
    public ParkingSpace() {
        Status = "Available";

        /* Init flight ID */
        flightID = "";

        /* Init flight's leaving time */
        leavingTime = "";

//        Status = new SimpleStringProperty("Available");
    };


    public void AddCategory(String temp){
        this.Category = temp;
    }

    public void AddFlightType(String temp){
        this.FlightTypes.add(temp);
    }

    public void AddPlaneType(String temp){
        this.PlaneTypes.add(temp);
    }

    public void AddService(String temp){
        this.Services.add(temp);  
    }
    
     
    public void changeStatusToAvailable(){

        /* Set the change for the observers and change the status */
        setChanged();
        this.Status = "Available";

        /* Init flightID */
        this.flightID = "";

        /* Init leaving time */
        this.leavingTime = "";

        /* Notify the observers */
        notifyObservers();
    }
    
    public void changeStatusToUnavailable() {
        setChanged();
        this.Status = "Unavailable";
        notifyObservers();
//        Status.set("Unavailable");
    }
        
    public void setCost(Integer temp){
        this.Cost = temp;   
    }
    
    public void assignID(String prefix, Integer num){
        this.prefixID = prefix;
        this.numID = num;
    }
    
    public void setMaxParkingTime(Integer temp){
        this.MaxParkingTime = temp;
    }

    public void setFlight(Flight _flight){
        this.flight = _flight ;

        /* Append flight ID */
        this.flightID = flight.getID();

        /* Append the flight's leaving time */
        this.leavingTime = flight.getLeavingTime();
    }

//    public void setFlightID(String _flightID){
//        this.flightID = _flightID ;
//    }

    public String getFlightID(){
        return this.flightID;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public Flight getFlight(){
        return this.flight ;
    }


    public String getCategory(){
        return this.Category ;
    }
    

    public List<String> getFlightType(){
        return this.FlightTypes ;
    }
    
    public List<String> getServices(){
        return this.Services ;
    }
    
    public List<String> getPlaneTypes(){
        return this.PlaneTypes ;
    }
    
    
    public String getStatus() {
        return this.Status;
//        return this.Status.get();
    }

//    public StringProperty statusProperty() {
//        return Status;
//    }


    public Integer getStartingCost(){
        return this.Cost;
    }

    public double calcCost(){

        List<String> flightServices = new ArrayList<>(flight.getReqServices());
        double calculated = (double) this.Cost;

        if(flightServices.contains("Refueling")){
            calculated += 0.25 * calculated;
        }

        if(flightServices.contains("Cleaning")){
            calculated += 0.02 * calculated;
        }

        if(flightServices.contains("PassengerTransport")){
            calculated += 0.02 * calculated;
        }

        if(flightServices.contains("Loading/Unloading")){
            calculated += 0.05 * calculated;
        }

        if(flight.getScheduledParkingTime() < flight.getParkingTime()){
            calculated += calculated;
        }else{
            /* if the flight's left 25min earlier then has a 20% sale*/
            if(flight.getScheduledParkingTime() - flight.getParkingTime() >= 25){
                calculated -= 0.20 * calculated;

            /* if the flight's left 10min earlier then has a 10% sale*/
            }else if(flight.getScheduledParkingTime() - flight.getParkingTime() >= 10){
                calculated -= 0.10 * calculated;
            }
        }

        return calculated;
    }
    
    public String getID(){
        return (this.prefixID + this.numID);
    }
    
    public Integer getMaxParkingTime(){
        return this.MaxParkingTime ;
    }

    public boolean handleFlight(Flight flight){

        /* Check if this parking space is available */
        if(!this.getStatus().equals("Available")){
//            System.out.println("Not Available");
            return false;
        }

        /* Check if this flight type is suitable for this parking space */
        if(!this.FlightTypes.contains(flight.getFlightType())){
//            System.out.println("Doesn't contain flight type");
            return false;
        }

        /* Check if this plane type is suitable for this parking space */
        if(!this.PlaneTypes.contains(flight.getPlaneType())){
//            System.out.println("Doesn't contain plane type");
            return false;
        }


        /* Check if the flight's required services can be served by the parking space */
        if(!this.Services.containsAll(flight.getReqServices())){
//            System.out.println("Doesn't contain required services ");
            return false;
        }

        /* Check if flight time plus the estimated parking time is less than
           the max time that the parking space can be occupied */
        if(flight.getReqLandingTime() + flight.getParkingTime() > this.MaxParkingTime){
//            System.out.println(" Max time < required ");
            return false;
        }

        return true;
    }


    public boolean canBehandled(Flight flight){

        /* Check if this flight type is suitable for this parking space */
        if(!this.FlightTypes.contains(flight.getFlightType())){
//            System.out.println("Doesn't contain flight type");
            return false;
        }

        /* Check if this plane type is suitable for this parking space */
        if(!this.PlaneTypes.contains(flight.getPlaneType())){
//            System.out.println("Doesn't contain plane type");
            return false;
        }

        /* Check if the flight's required services can be served by the parking space */
        if(!this.Services.containsAll(flight.getReqServices())){
//            System.out.println("Doesn't contain required services ");
            return false;
        }

        /* Check if flight time plus the estimated parking time is less than
           the max time that the parking space can be occupied */
        if(flight.getReqLandingTime() + flight.getParkingTime() > this.MaxParkingTime){
//            System.out.println(" Max time < required ");
            return false;
        }

        return true;
    }

}
    
    


