package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed) throws InvalidOperationException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidOperationException("you have to add the Id...");
        }
        this.id=id;
        this.model=model;
        this.maxSpeed=maxSpeed;
        this.currentMileage=0.0;
    }

    public abstract void move(double distance) throws InvalidOperationException,InsufficientFuelException;
    public abstract double calculateFuelEfficiency();
    public abstract double estimateJourneyTime(double distance) throws InvalidOperationException;

    public void displayInfo() {
        System.out.printf("ID: %s | Model: %s | MaxSpeed: %.2f km/h | Mileage: %.2f km%n", id, model, maxSpeed, currentMileage);
    }

    public double getCurrentMileage() {
        return currentMileage;
    }


    public void addMileage(double distance) {
        this.currentMileage += distance;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
    
    @Override
    public int compareTo(Vehicle other) {
        double e1 = this.calculateFuelEfficiency();
        double e2 = other.calculateFuelEfficiency();
        if (Double.compare(e2, e1) != 0) return Double.compare(e2, e1);
        return this.getId().compareTo(other.getId());
    }

    
}
