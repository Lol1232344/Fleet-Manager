package vehicles;

import exceptions.*;
import interfaces.*;
import java.util.*;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable  {
    private double fuelLevel;
    private double cargoCapacity;  
    private double currentCargo;
    private boolean maintenanceNeeded;

public Truck(String id, String model, double maxSpeed, int numWheels)  throws InvalidOperationException{
    super(id, model, maxSpeed, numWheels);
    this.fuelLevel=0.0;
    this.cargoCapacity=5000.0;
    this.currentCargo=0.0;
    this.maintenanceNeeded=false;
}


    public double calculateFuelEfficiency() {
        double fueleff = 8 ;
        if(currentCargo>0.5*cargoCapacity){
            return fueleff*0.90;
        }
        return fueleff;
    }


    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance not negative.");
        }

        double fuelNeeded = (distance / calculateFuelEfficiency());
        if (this.fuelLevel < fuelNeeded) {
            throw new InsufficientFuelException("Fuel over...");
        }

        consumeFuel(distance);
        addMileage(distance);

        System.out.println("Transporting cargo...");
    }
    


 public void refuel(double amount) throws InvalidOperationException{
     if(amount<=0){
         throw new InvalidOperationException("No more fuel needed");
        } 
        this.fuelLevel+=amount;
 }


 public double getFuelLevel(){
    return this.fuelLevel;
 }



 public double consumeFuel(double distance) throws InsufficientFuelException{
    double fuelconsumed = distance/calculateFuelEfficiency();
    if(this.fuelLevel<fuelconsumed){
        throw new InsufficientFuelException("Not enough fuel.....");

    }
     double fuelleft = this.fuelLevel - fuelconsumed;
     return fuelleft;
 }

 
 public void loadCargo(double weight) throws OverloadException{
    this.currentCargo+=weight;
    if(weight + this.currentCargo>this.cargoCapacity){
        throw new OverloadException("Capacity reached...");
    }
 }
 

 public void unloadCargo(double weight) throws InvalidOperationException{
    this.currentCargo-=weight;
    if(weight>this.currentCargo){
        throw new InvalidOperationException("Cannot unload more cargo");
    }
 }


    public double getCargoCapacity() {
        return this.cargoCapacity;
    }


    public double getCurrentCargo() {
        return this.currentCargo;
    }



public void scheduleMaintenance(){
    this.maintenanceNeeded = true;
}



public boolean needsMaintenance(){
    return getCurrentMileage()>10000;
       
    }


 public void performMaintenance(){
    this.maintenanceNeeded = false;
    addMileage(-getCurrentMileage()) ;
    System.out.println("Maintanence on car: " + getId());
 }

}
