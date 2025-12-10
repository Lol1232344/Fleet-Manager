package vehicles;
import java.util.*;
import exceptions.*;
import interfaces.*;

public class Car extends LandVehicle implements FuelConsumable,PassengerCarrier,Maintainable{
    private double fuelLevel;
    private int passengerCapacity;
    private int currentPassengers;
    private boolean maintenanceNeeded;


public Car(String id, String model, double maxSpeed , int numWheels)throws InvalidOperationException{
    super(id,model,maxSpeed,numWheels);
    this.fuelLevel=0.0;
    this.passengerCapacity=5;
    this.maintenanceNeeded = false;
}


@Override
     public double calculateFuelEfficiency(){
        return 15.0;
     }
@Override
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


    

@Override

 public void refuel(double amount) throws InvalidOperationException{
     if(amount<=0){
         throw new InvalidOperationException("No more fuel needed");
        } 
        this.fuelLevel+=amount;
 }

@Override
 public double getFuelLevel(){
    return this.fuelLevel;
 }

@Override

 public double consumeFuel(double distance) throws InsufficientFuelException{
    double fuelconsumed = distance/calculateFuelEfficiency();
    if(this.fuelLevel<fuelconsumed){
        throw new InsufficientFuelException("Not enough fuel.....");

    }
     double fuelleft = this.fuelLevel - fuelconsumed;
     return fuelleft;
 }

@Override

 public void boardPassengers(int count) throws OverloadException{
    int passengerin=this.currentPassengers+=count;
    if(passengerin>passengerCapacity){
        throw new OverloadException("not enough space ");
    }
 }

@Override
 public void disembarkPassengers(int count) throws InvalidOperationException{
    this.currentPassengers-=count;
    if(count > this.currentPassengers){
        throw new InvalidOperationException("All passengers are disembarked");
    }
 }

@Override
 
public int getPassengerCapacity(){
    return this.passengerCapacity;
}

@Override

public int getCurrentPassengers(){
    return this.currentPassengers;
}

@Override

public void scheduleMaintenance(){
    this.maintenanceNeeded = true;
}

@Override

public boolean needsMaintenance(){
    return getCurrentMileage()>10000;
       
    }

@Override
 public void performMaintenance(){
    this.maintenanceNeeded = false;
    addMileage(-getCurrentMileage()) ;
    System.out.println("Maintanence on car: " + getId());
 }
}






