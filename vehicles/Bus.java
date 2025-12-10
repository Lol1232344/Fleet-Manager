package vehicles;
import java.util.*;
import exceptions.*;
import interfaces.*;

public class Bus extends LandVehicle implements FuelConsumable,PassengerCarrier,Maintainable,CargoCarrier{
    private double fuelLevel;
    private int passengerCapacity;
    private int currentPassengers;
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;

public Bus(String id, String model, double maxSpeed , int numWheels) throws InvalidOperationException{
    super(id, model, maxSpeed, numWheels);
    this.fuelLevel=0.0;
    this.passengerCapacity=50;
    this.currentPassengers=0;
    this.cargoCapacity=500;
    this.currentCargo=0.0;
    this.maintenanceNeeded=false;
}

     public double calculateFuelEfficiency(){
        return 10.0;
     }
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative.");
        }

        double fuelNeeded = distance / calculateFuelEfficiency();
        if (this.fuelLevel < fuelNeeded) {
            throw new InsufficientFuelException("Not enough fuel to complete the journey.");
        }
        consumeFuel(distance);
        addMileage(distance);

        System.out.println("Driving on road..."); //
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







