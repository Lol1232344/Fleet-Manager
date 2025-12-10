package vehicles;

import exceptions.*;

public abstract class AirVehicle extends Vehicle {
    private double maxAltitude;

    public AirVehicle(String id, String model, double maxSpeed, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.maxAltitude = maxAltitude;
    }

    @Override
    public double estimateJourneyTime(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance can't be negative...");
        double base = distance / getMaxSpeed();
        return base * 0.95; 
    }

    public double getMaxAltitude() {
        return maxAltitude;
    }
}
