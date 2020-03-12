/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_project_back;


import Multimedia_project_back.parkingSpace.ParkingSpace;

import java.sql.Timestamp;
import java.util.*;

/**
 *
 * @author ritos
 */
public class Flight {
    private final String flightType;
    private final String planeType;
    private final List<String> reqServices;
    private final Integer parkingTime;
    private final String ID;
//    private SimpleStringProperty ID;
    private String status;
    private String city;
    private Timestamp timestamp; //2016-11-16 06:43:19.77
    private Timestamp begOfLanding; //2016-11-16 06:43:19.77
    private Timestamp whenParked; //2016-11-16 06:43:19.77
    private Integer reqLandingTime;
    private ParkingSpace parkingSpace;
    private String parkingID;
    private Integer ScheduledParkingTime;
    private Scheduler scheduler;
    private Random random;
    private String leavingTime;
    private String requestTime;

   // public  Airplane airplane = new Jet() 


    public Flight (String _flightID, String _city, String _flightType, String _planeType, Integer _parkingTime, Integer _landingTime, Timestamp _requestTime, Scheduler _scheduler)
    {
        
        ID = _flightID;
//        ID = new SimpleStringProperty(_flightID);

        city = _city;
        flightType = _flightType;
        planeType = _planeType;
        reqServices = new ArrayList<String>();
        ScheduledParkingTime = _parkingTime;

        /* Add a random value */
        random =  new Random();
        parkingTime = _parkingTime + random.nextInt() % _parkingTime;

        reqLandingTime = _landingTime;
        status = "Holding";
        scheduler = _scheduler;
        requestTime = _requestTime.toString();

        /* Init Parking ID */
        parkingID = "";
    }

    private Timestamp addSecToTimestamp(Timestamp timestamp, Integer sec){
        /* Create a Calender */
        Calendar cal = Calendar.getInstance();

        /* Set the app_time in the calender */
        cal.setTimeInMillis(timestamp.getTime());

        /* Add the seconds to the timestamp's time */
        cal.add(Calendar.SECOND, sec);

        /* Append the new value to a new timestamp */
        return new Timestamp(cal.getTime().getTime());
    }
    
    
    public boolean AddService(String temp){
        return this.reqServices.add(temp);
    }
    
    public void ChangeStatusToHolding(){
        this.status = "Holding";
    }

    public void ChangeStatusTo(String _status){
        this.status = _status;
    }

    private void compLeavingTime(){
        Timestamp timeItWillBeLanded = addSecToTimestamp(this.begOfLanding, this.reqLandingTime * 60);
        this.leavingTime = addSecToTimestamp(timeItWillBeLanded, this.parkingTime * 60).toString();
    }
    
    public void ChangeStatusToLanding(){
        this.status = "Landing";

        /* Timestamp of when landing started */
        this.begOfLanding = this.scheduler.getCurrentTime();

        /* Now that the flight has been accepted compute leaving time */
        compLeavingTime();
    }

    public String getLeavingTime(){
        return this.leavingTime;
    }
    
    public void ChangeStatusToParked(){
        this.status = "Parked";

        /* Timestamp of when the flight has parked */
        this.whenParked = this.scheduler.getCurrentTime();
    }

    public void setParkingSpace(ParkingSpace _parkingSpace){
        /* Append parking Space */
        parkingSpace = _parkingSpace;

        /* Append parking ID */
        parkingID = _parkingSpace.getID();
    }


    public ParkingSpace getParkingSpace(){
        return this.parkingSpace;
    }

    public String getParkingID(){
        return this.parkingID;
    }

    public String getFlightType(){
        return this.flightType;
    }
    
    public String getPlaneType(){
        return this.planeType;
    }
    
    public List<String> getReqServices(){
        return this.reqServices;
    }

    public String getStatus(){
        return this.status;
    }

    public String getRequestTime(){
        return this.requestTime;
    }

    /* For id */
    public String getID(){ return this.ID; }

//    public String getID(){
//        return this.ID.get();
//    }
//
//    public SimpleStringProperty IDProperty() {
//        return ID;
//    }
//
//    public void setID(String _id) {
//        this.ID.set(_id);
//    }
    /* ---------------------------------- */

    public String getCity(){
        return this.city;
    }

    public Integer getParkingTime(){ return this.parkingTime; }

    public Integer getScheduledParkingTime(){ return this.ScheduledParkingTime; }

    public Integer getReqLandingTime(){
        return this.reqLandingTime;
    }

    public Timestamp getBegOfLanding(){
        return this.begOfLanding;
    }

    public Timestamp getWhenParked(){
        return this.whenParked;
    }

    public Timestamp getTimeStamp(){
        return timestamp;
    }

    
}
