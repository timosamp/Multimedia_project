/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_project_back;

//import java.util.ArrayList;

/**
 *
 * @author ritos
 */
public class Airplane {
    Integer LandingTime;
    String ID;
    String PlaneType;
    
    
    public Airplane(String _ID, String _PlaneType, Integer _LandingTime){
        
        ID = _ID;
        PlaneType = _PlaneType;
        //ReqServices = new ArrayList<String>();
        LandingTime = _LandingTime;
        
    }
    
    
    public String PrintPlaneType(){
        return this.PlaneType;
    }
    
    public String GetID(){
        return this.ID;
    }
    
    public Integer LandingTime(){
        return this.LandingTime ;
    }
    
}
