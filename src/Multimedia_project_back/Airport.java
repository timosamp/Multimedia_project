/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_project_back;

import java.util.*;
import Multimedia_project_back.parkingSpace.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author ritos
 */
public class Airport {

    private HashMap<String, Integer> MaxSationsNum;
    private final HashMap<Integer, String> NumToClassName;
    private String ID;
    private final List<ParkingSpace> availableSpaces;
    public Scheduler scheduler;

    /**
     * Constructs an Airport instance and immediately an Scheduler Instance.
     * In the meanwhile it reads the inputs scenario files from param1 and param2
     * and creates lists consist of instances of classes ParkingSpace and Scheduler
     * and append them accordingly to airport object and scheduler object.
     *
     * @see Scheduler#Scheduler(String, String, Airport) That's also called from Airport's constructor
     *
     * @param _Dir
     * @param _scenarioName
     * @throws Exception in case of missing scenario files or wrong configured files
     * @author Dimitrios Ampelikiotis
     * @version 9.2
     */
    public Airport(String _Dir, String _scenarioName) throws Exception
    {

        //ID = _ID;

        availableSpaces = new ArrayList<ParkingSpace>();

        NumToClassName = new HashMap<Integer, String>();
        MaxSationsNum = new HashMap<String, Integer>();

        NumToClassName.put(1, "Gate");
        NumToClassName.put(2, "CargoGate");
        NumToClassName.put(3, "ZoneA");
        NumToClassName.put(4, "ZoneB");
        NumToClassName.put(5, "ZoneC");
        NumToClassName.put(6, "GenParkingSpace");
        NumToClassName.put(7, "LongTerm");

        readAndConstSpaces(_Dir, _scenarioName);

        /* Only for test reasons */
//        printSpaces();
        
        scheduler = new Scheduler(_Dir, _scenarioName, this);

//        System.out.println("Airport's scheduler is constructed");
//        System.out.println("Airport's scheduler is started");
//        scheduler.startSceduler();

    }
    
    public void printSpaces(){
        for(ParkingSpace i : availableSpaces)
        {
            System.out.println(i.getCategory() + ": " + i.getID());
        }
    }

    /**
     * This function returns all the Parking Spaces of the airport
     *
     * @return a list of parking spaces objects
     */
    public List<ParkingSpace> getSpacesList(){
        return availableSpaces;
    }

    private void constructSpace(Integer classID, Integer amount, Integer _cost , String prefixID) throws Exception {

        try {

            String spacePackage = "Multimedia_project_back.parkingSpace.";
            String pathToClass = spacePackage.concat(NumToClassName.get(classID));       

            Class classType = Class.forName(pathToClass);
            
            MaxSationsNum.put(NumToClassName.get(classID), amount);
            
            for(Integer i = 0 ; i < amount ; i++){

                ParkingSpace parkingSpace = (ParkingSpace) classType.newInstance();
                parkingSpace.assignID(prefixID, i);
                parkingSpace.setCost(_cost);
                availableSpaces.add(parkingSpace);

            }
            
            

        } catch (Exception ex) {
//            System.out.println(ex.toString());
            throw ex;
        }
//        Logger.getLogger(Airport.class.getName()).log(Level.SEVERE, null, ex);
//        finally {
//            System.out.println("ola kala");
//        }
    }

    private void readAndConstSpaces(String Dir, String scenarioName) throws Exception {
        String pathToAir = Dir.concat("airport_");
        pathToAir = pathToAir.concat(scenarioName + ".txt");

        BufferedReader inAir = null;

        try {
            inAir = new BufferedReader(new FileReader(pathToAir));
        }catch (IOException e) {
//            System.out.println("There was a problem: " + e);
//            e.printStackTrace();
            throw e;
        }


        try{
            String lineA = null;

            while ((lineA = inAir.readLine()) != null) {
                String[] splited = lineA.split(",");
                constructSpace(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]),
                                                        Integer.parseInt(splited[2]), splited[3]);
                System.out.println();
            }

            System.out.println("\n\n");

        } catch (Exception e) {
            System.out.println("There was a problem with the Airport's file");
//            e.printStackTrace();
            throw e;

        } finally {
            try {
                inAir.close();
            } catch (Exception e) {
                System.out.println("There was a problem when closing the file: " + e.toString());
                throw e;
            }
        }
    }

}
