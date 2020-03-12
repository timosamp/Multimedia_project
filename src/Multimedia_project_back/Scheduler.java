/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_project_back;

import Multimedia_project_back.parkingSpace.ParkingSpace;
import Multimedia_project_front.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
//import java.util.List;


/**
 *
 * @author ritos
 */


public class Scheduler extends Observable{


//    private Comparator<Flight> flightComparator = new Comparator<Flight>() {
//         @Override
//         public int compare(Flight f1, Flight f2) {
//            return f1.getTimeStamp().compareTo(f2.getTimeStamp());
//        }
//     };

    private final Airport airport;

    private String dir;
    private String scenarioName;

    private final HashMap<Integer, String> flightType;
    private final HashMap<Integer, String> planeType;
    private final HashMap<String, Integer> landingTime;

    private Timestamp app_time;
    private Timestamp beginningTime;
    private Integer realSec;
    private Integer translatedSec;

    private Timer timer;

//    private String uniqueID;

    private final List<ParkingSpace> avSpaces;
//    private final List<ParkingSpace> occupiedSpaces;

    private final List<Flight> flights;

    /* An iterator for use */
    private ListIterator parkIter;
    private ListIterator flightIter;

//    private final PriorityQueue<Flight> flights;
//    private final ArrayList<Flight> flights;

    /* List of logs */
    private ArrayList<Log> logslist;

    /* Cost of Flights */
    private double sumCost;


    /**
     * This constructor initiates all the hashMaps of numbers and names for the planeType, flightType
     * and landing time. Additionally, read the scenario file for the flights, create a list of them and
     * its consisting object and throws an Exception in case of missing file or element in the specified file.
     * in the end construct the schedulers object as expected. In the end, _Dir must specify the directory
     * path which both scenarios (airport's, flight's) are stored together.
     *
     * @param _Dir
     * @param _scenarioName
     * @param _airport
     * @throws Exception
     */
    public Scheduler(String _Dir, String _scenarioName, Airport _airport) throws Exception {


        /* A reference to the airport object*/
        this.airport = _airport;

        /* Save scenario settings */
        this.dir = _Dir;
        this.scenarioName = _scenarioName;

        /* A reference to the airport's available spaces */
        this.avSpaces = this.airport.getSpacesList();
//        this.occupiedSpaces = new ArrayList<ParkingSpace>();

        /* Init scheduler's required data structures */
        flightType = new HashMap<Integer, String>();
        planeType = new HashMap<Integer, String>();
        landingTime = new HashMap<String, Integer>();

        flights = new ArrayList<Flight>();
        //flights = new ArrayList<Flight>();
        //flights = new PriorityQueue<>(flightComparator);

        /* Init Unique id*/
//        uniqueID = UUID.randomUUID().toString();

        /* Init total cost */
        sumCost = 0;

        /* Init hash map of flight type */
        flightType.put(1, "Passenger");
        flightType.put(2, "Cargo");
        flightType.put(3, "Private");

        /* Init hash map of plane type */
        planeType.put(1, "SingleMotor");
        planeType.put(2, "TurboProp");
        planeType.put(3, "Jet");

        /* Init hash map of landing time */
        landingTime.put("Jet", 2);  // Jet
        landingTime.put("TurboProp", 4);  // TurboDrop
        landingTime.put("SingleMotor", 6);  // SingleMotor

        /* Init the time of the application */
        app_time = new Timestamp(System.currentTimeMillis());

        /* Init the time of the application since beginning */
        beginningTime = new Timestamp(System.currentTimeMillis());

        /* Construct a Timer */
        timer = new Timer();

        /* Read the input files and construct the data structures for the flights */
        readNconstFlights(this.dir, this.scenarioName);

        /* As a test print all the input flights */
//        printAllFlights();

        /* Construct logs list */
        logslist = new ArrayList<Log>();
        initLogs();
        updateLogs();
    }

    /**
     * This function called when we want to start the scheduling process.
     */
    public void startSceduler(){

        System.out.println("Airport's scheduler is started");

        /* Init the time that scheduler starts */
//        this.app_time = new Timestamp(System.currentTimeMillis());


        /* Default parameters: 1 sec is translated in 5 min in the application */
        setTimeParametersAndActivate(1, 5 * 60 );

    }

    private void initLogs(){
        logslist.add(new Log("No. of arrived flights:", ""));
        logslist.add(new Log("No. of Available Parking Spaces:", ""));
        logslist.add(new Log("No. of flights will be departed into the next 10 min:", ""));
        logslist.add(new Log("Total amount of money earned:",""));
        logslist.add(new Log("Time since Application has started:",""));
    }

    private void updateLogs(){
        logslist.get(0).setInfo( ((Integer)(getFlightsWithStatus("Parked").size())).toString() );
        logslist.get(1).setInfo( ((Integer)(getParkingSpaceWithStatus("Available").size())).toString() );
        logslist.get(2).setInfo( ((Integer)(getNextDepartures(10 * 60).size())).toString() );
        logslist.get(3).setInfo( ((Double)(getTotalCost())).toString() );
        logslist.get(4).setInfo( getRunningTime() );
    }

    /**
     * This function return a list of logs objects who consist of useful centralised
     * information for the app.
     *
     * @return
     */
    public ArrayList<Log> getLogslist() {
        return logslist;
    }

    /**
     * This function return the current time measured by the app
     *
     * @return Timestamp
     */
    public Timestamp getCurrentTime(){
        synchronized (this){
            return this.app_time;
        }
    }

    /**
     * This function returns the amount of time since the app has started in a String form of
     * "%d days, %d hours, %d minutes, %d seconds%n"
     *
     * @return String
     */
    public String getRunningTime(){

        //milliseconds
        long different = app_time.getTime() - beginningTime.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return String.format("%d days, %d hours, %d minutes, %d seconds%n",
                       elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

    }

    /**
     * This function returns a new Timestamp which is equivalent to timestamp + sec.
     *
     * @param timestamp
     * @param sec
     * @return Timestamp
     */
    public Timestamp addSecToTimestamp(Timestamp timestamp, Integer sec){
        /* Create a Calender */
        Calendar cal = Calendar.getInstance();

        /* Set the app_time in the calender */
        cal.setTimeInMillis(timestamp.getTime());

        /* Add the seconds to the timestamp's time */
        cal.add(Calendar.SECOND, sec);

        /* Append the new value to a new timestamp */
        return new Timestamp(cal.getTime().getTime());
    }

    /**
     * This function provided by the scheduler's class help us to check if t1 + sec >= t2.
     *
     * @param t1
     * @param t2
     * @param sec
     * @return boolean
     */
    public Boolean CompTimestampsWithSecInterval(Timestamp t1, Timestamp t2, Integer sec){
        /* Create a Calender */
        Calendar cal = Calendar.getInstance();

        /* Set the app_time in the calender */
        cal.setTimeInMillis(t1.getTime());

        /* Add the seconds to the t1's time */
        cal.add(Calendar.SECOND, sec);

        /* Create the new temp timestamp */
        Timestamp tempT = new Timestamp(cal.getTime().getTime());

        /* check if t1 + sec > t2 */
        return tempT.getTime() >= t2.getTime() ;
    }


    private void updateAppTime(){
        /* Update application's time */

        this.app_time = addSecToTimestamp(this.app_time, this.translatedSec);
//        System.out.println(this.getCurrentTime());
        setChanged();
        notifyObservers();
    }

    private void timeInterruptHandler(){

        /* Definition of the handler that is called every realSec seconds */

//        System.out.println("Time Handler");
        synchronized(this) {
            updateAppTime(); //Update application time
            checkFlights();  //Check the flights and make the required changes
            updateLogs(); //Update the logs of the scheduler
        }
    }

    private void activateTimeInterrupt(){
        System.out.println("Activate time interrupt");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Main.currentAirport.scheduler.timeInterruptHandler();

//                System.out.println("Time's up");
            }
        }, 1000, this.realSec); //set a small delay till the scene is loaded
    }

    /**
     * This function it's used for stopping the timer before close or restart the application.
     */
    public void stopTimer(){
        /* Stop the timer */
        timer.cancel();
        timer.purge();
    }

    /**
     * This function it is used to configure the ratio of the real time and the relative one
     * for the application and also activates the time interrupts.
     *
     * @param _realSec This parameter is how often the interrupt occurs
     * @param _translatedSec This parameter is about how many second will be added to the app's clock in every interrupt.
     */
    public void setTimeParametersAndActivate(Integer _realSec, Integer _translatedSec){

        System.out.println("Set parameters for time interrupt");

        /* Set the required parameters for the time translation */
        this.realSec = _realSec * 1000; //milliseconds
        this.translatedSec = _translatedSec;

        /* Activate the time scheduler */
        activateTimeInterrupt();
    }


    /**
     * This function used for adding a new flight request to scheduler's list flight.
     * It checks the uniqueness of the _flightID and then calls the Constructor of Class Flight.
     * There are no checks for the validity of the parameters so be sure for them before calling them,
     * otherwise a nice exception will generate.
     *
     * @see Flight#Flight(String, String, String, String, Integer, Integer, Timestamp, Scheduler)
     *
     * @param _flightID
     * @param _city
     * @param _flightType
     * @param _planeType
     * @param _parkingTime
     * @throws Exception
     */
    public void addFlight(String _flightID, String _city, String _flightType, String _planeType, Integer _parkingTime) throws Exception{

        synchronized (this) {

            if (isAUniqueId(_flightID)) {

                Integer _landingTime = landingTime.get(_planeType);

                try {

                    Flight flight = new Flight(_flightID, _city, _flightType, _planeType, _parkingTime, _landingTime, this.app_time, this);

                    flights.add(flight);
                }catch (Exception e){
                    throw e;
                }

            } else {
                System.out.println("The ID: " + _flightID + " already exists");
            }
        }
    }

    private boolean isAUniqueId (String id){

        for(Flight flight : getAllFlights()){
            if(flight.getID().equals(id)) {
                return false;
            }
        }

        return true;
    }

//    public String getUniqueID(){
//
//        //Find a unique id in the current time
//        while(!isAUniqueId(this.uniqueID)){
//            this.uniqueID = UUID.randomUUID().toString();
//        }
//
//        return this.uniqueID;
//    }


    /* Queries for flights */

    /**
     * This function returns a list consists of the objects of all flights in the app.
     *
     * @return
     */
    public List<Flight> getAllFlights(){
        return flights;//.stream().collect(Collectors.toList());
    }

    /**
     * This function returns a list consists of the objects of flights in the app,
     * depends on the demand status.
     *
     * @param status
     * @return
     */
    public List<Flight> getFlightsWithStatus(String status){
        return flights.stream()
                .filter(f -> f.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * This function returns a list consists of the objects of flights in the app
     * which has be delayed.
     *
     * @return
     */
    public List<Flight> getDelayedFlights(){
        return flights.stream()
                .filter(f -> (f.getBegOfLanding() != null) && CompTimestampsWithSecInterval(this.app_time,
                        addSecToTimestamp(f.getBegOfLanding(), (f.getReqLandingTime() + f.getScheduledParkingTime()) * 60), // scheduled leaving time
                        0))
                .collect(Collectors.toList());

    }

    /**
     * This function returns a list consists of the objects of flights in the app
     * which will leave from the airport the next _sec seconds.
     *
     * @param _sec
     * @return
     */
    public List<Flight> getNextDepartures(Integer _sec){
                                    /* t1 + sec >= t2 */
        return flights.stream()
                .filter(f -> (f.getBegOfLanding() != null) && CompTimestampsWithSecInterval(this.app_time,
                        addSecToTimestamp(f.getBegOfLanding(), (f.getReqLandingTime() + f.getParkingTime()) * 60), // the time flight leaves
                        _sec))
                .collect(Collectors.toList());


    }

    /**
     * This function prints to the stdout all the flights of the airport.
     */
    public void printAllFlights(){
        System.out.println("Printing all the flights ");

        for(Flight i : flights)
        {
            System.out.println(i.getFlightType() + ": " + i.getID());
        }
    }

    /**
     * This finction returns a list of parking space objects depends on the status demand.
     *
     * @param status
     * @return
     */
    public List<ParkingSpace> getParkingSpaceWithStatus(String status){
        return avSpaces.stream()
                .filter(p -> p.getStatus().equals(status))
                .collect(Collectors.toList());
    }



    private boolean checkForAvSpace(Flight _flight){

//        System.out.println(" Check for available space ");
        boolean flag = false;

        /*Init list iterator */
        parkIter = avSpaces.listIterator();

        /* Check if there is any parking space available which is suitable to handle this flight*/
        while(parkIter.hasNext()){
//        for(ParkingSpace parkingSpace : avSpaces){
            ParkingSpace parkingSpace = (ParkingSpace) parkIter.next();

            /* If you find one then move this parking space to the occupied list
               and make the required changes */
            if(parkingSpace.handleFlight(_flight)){

//                System.out.println(" We found a suitable parking space ");

                /* Change status of the flight */
                _flight.ChangeStatusToLanding();

                /* Save flight to the parking space */
                parkingSpace.setFlight(_flight);
//                System.out.println(" Set flight to parking space ");

                /* Set to flight it's parking space */
                _flight.setParkingSpace(parkingSpace);
//                System.out.println(" Set flight's parking space ");

                /* Change status of the parking space */
                parkingSpace.changeStatusToUnavailable();

                /* FIXME: Print the required messages to the console logs */
                System.out.println("Your request for flight " + _flight.getID() + " is being handled");

                /* No more iterations needed */
                return true;

            }

            /*In this case the flight can be handled in the near future */
            if(parkingSpace.canBehandled(_flight)){
                flag = true;
            }
        }

        /* This flight can be handle but there is no available space right now */
        if(flag){
            System.out.println("Your request for flight " + _flight.getID() + " is pending");
        }

        return flag;
    }


    /* Arrivals */
    private void parkLandingFlights(Flight flight){
        /* FIXME */

        /* Check if the landing phase is ended for the flight */

        /* When the plane is landed make the required changes */
        if (this.app_time.getTime() >= addSecToTimestamp(flight.getBegOfLanding(), flight.getReqLandingTime() * 60).getTime()) {

            /* Change status of flight to parked */
            flight.ChangeStatusToParked();

            /* FIXME: Print the required messages to the console logs */
            System.out.println("The flight " + flight.getID() + " is parked");

        }

    }

    /**
     * This function returns the Total amount of money that the airport
     * has earned through the handling flights process the whole time since
     * the beginning.
     *
     * @return
     */
    public double getTotalCost () {
        return this.sumCost;
    }

    private void addToTotalCost (double plusCost) {
        this.sumCost += plusCost;
    }



    /* Departures */
    private void freeParkedFlights(Flight flight){

        /* Check if the parking phase is ended for the flight */
        if (this.app_time.getTime() >= addSecToTimestamp(flight.getBegOfLanding(),
                (flight.getReqLandingTime() + flight.getParkingTime()) * 60).getTime()) {

            ParkingSpace flightParkingSpace = flight.getParkingSpace();

            /* Cost the flight and append it to the total cost variable */
            addToTotalCost(flightParkingSpace.calcCost());

            /* Change the status of parking space to available */
            flightParkingSpace.changeStatusToAvailable();

            /* Update monitor messages */
            System.out.println("The flight " + flight.getID() + " is leaving");

            /* Delete the flight */
            flightIter.remove();

        }

        /* Another way for choosing if the flight will leave or not */
//        boolean coin = Math.random() < 0.5; // random true/false variable

        /* if is true the plane is leaving, otherwise is staying */
//        if (coin) {
//        }

        /* FIXME: Print the required messages to the console logs */

    }

    private void checkFlights(){

        flightIter = flights.listIterator();

        while(flightIter.hasNext()) {
//        for(Flight flight : flights){

            Flight flight = (Flight) flightIter.next();

            switch (flight.getStatus())
            {
                case "Holding":
                    /* Try to fulfill the holding flight requests*/
                    if (!checkForAvSpace(flight)){
                        /* This combination of parameters can not be handled generally */
                        flightIter.remove();
                        System.out.println("This flight can not be handled by this Airport");
                    }
                    break;
                case "Landing" :
                    /* if landing is landed change the status */
                    parkLandingFlights(flight);
                    break;
                case "Parked" :
                    /* if the flights has departed then remove it */
                    freeParkedFlights(flight);
                    break;
            }

        }

        /* FIXME: Print the required messages to the console logs */

    }



    private void readNconstFlights(String Dir, String scenarioName) throws Exception {

        System.out.println("Start reading and constructing flight objects");

        String pathToFlights = Dir.concat("setup_");
        pathToFlights = pathToFlights.concat(scenarioName + ".txt");

        BufferedReader inFl = null;

        try {
            inFl = new BufferedReader(new FileReader(pathToFlights));
        }catch (IOException e) {
//            System.out.println("There was a problem: " + e);
//            e.printStackTrace();
            throw e;
        }

        try{
            String lineF = null;

            while ((lineF = inFl.readLine()) != null) {
                String[] splited = lineF.split(",");

//                Flight flight = new Flight(splited[0], splited[1], flightType.get(Integer.parseInt(splited[2])),
//                        planeType.get(Integer.parseInt(splited[3])), Integer.parseInt(splited[4]),
//                        landingTime.get(planeType.get(Integer.parseInt(splited[3]))), this);
//                flights.add(flight);


                /*  Add new flight -- second way*/
                addFlight(splited[0], splited[1], flightType.get(Integer.parseInt(splited[2])),
                        planeType.get(Integer.parseInt(splited[3])), Integer.parseInt(splited[4]));

            }

        } catch (Exception e) {
//            System.out.println("There was a problem: " + e);
//            e.printStackTrace();
            System.out.println("There was a problem with the Flight's file");
            throw e;
        } finally {
            try {
                inFl.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }



}

