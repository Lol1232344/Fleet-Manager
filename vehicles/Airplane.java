package vehicles;

import exceptions.*;
import interfaces.*;
import java.util.*;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable{
    private double fuelLevel;
    private int passengerCapacity;
    private int currentPassengers;
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;


public Airplane(String id, String model, double maxSpeed, double maxAltitude) throws InvalidOperationException{
    super(id, model, maxSpeed, maxAltitude);
    this.fuelLevel = 0.0;
    this.passengerCapacity = 200;
    this.currentPassengers = 0;
    this.cargoCapacity = 10000;
    this.maintenanceNeeded = false;
}

    public double calculateFuelEfficiency() {
        return 5.0; 
    }


    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance not negative...");
        }

        double fuelNeeded = (distance / calculateFuelEfficiency());
        if (this.fuelLevel < fuelNeeded) {
            throw new InsufficientFuelException("Not enough fuel...");
        }

        consumeFuel(distance);
        addMileage(distance);

        System.out.println("Flying at height : " + getMaxAltitude() );
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


 public void boardPassengers(int count) throws OverloadException{
    int passengerin=this.currentPassengers+=count;
    if(passengerin>passengerCapacity){
        throw new OverloadException("not enough space ");
    }
 }


 public void disembarkPassengers(int count) throws InvalidOperationException{
    this.currentPassengers-=count;
    if(count > this.currentPassengers){
        throw new InvalidOperationException("All passengers are disembarked");
    }
 }


    public int getPassengerCapacity() {
        return this.passengerCapacity;
    }


    public int getCurrentPassengers() {
        return this.currentPassengers;
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



