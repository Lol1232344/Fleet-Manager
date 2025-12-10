package vehicles;

import exceptions.*;
import interfaces.*;
import java.util.*;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable{
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;
    private double fuelLevel;


 public CargoShip(String id, String model, double maxSpeed, boolean hasSail) throws InvalidOperationException{
    super(id, model, maxSpeed,hasSail);
    this.currentCargo = 0.0 ;
    this.maintenanceNeeded = false;
    this.cargoCapacity=50000;
    if (hasSail) {
        this.fuelLevel = 0.0;
    } else {
        this.fuelLevel = 0.0; 
    }
 }



    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance not possible...");
        }
        if(!hasSail()){
            double fuelNeeded = distance/calculateFuelEfficiency();
            if(this.fuelLevel < fuelNeeded){
                throw new InsufficientFuelException("Not enough fuel...");
            }
            consumeFuel(distance);
        }
        addMileage(distance);
        System.out.println("Sailing with carrying cargo...");
    }

    public double calculateFuelEfficiency(){
        if(hasSail()){
            return 0.0;
        }
        else{
            return 4.0;
        }
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



