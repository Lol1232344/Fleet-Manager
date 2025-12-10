package vehicles;

import exceptions.*;

public abstract class WaterVehicle extends Vehicle {
    private boolean hasSail;

    public WaterVehicle(String id, String model, double maxSpeed, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.hasSail = hasSail;
    }

    @Override
    public double estimateJourneyTime(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance can't be negative.");
        double base = distance / getMaxSpeed();
        return base * 1.15;
    }

    public boolean hasSail() {
        return hasSail;
    }
}
