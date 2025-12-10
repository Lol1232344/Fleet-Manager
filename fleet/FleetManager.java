package fleet;

import exceptions.*;
import interfaces.*;
import vehicles.*;
import java.io.*;
import java.util.*;

public class FleetManager {
    private List<Vehicle> fleet;
    private Set<String> distinctModels;

    public FleetManager() {
        this.fleet = new ArrayList<>();
        this.distinctModels = new HashSet<>();
    }

    
    public void addVehicle(Vehicle v) throws InvalidOperationException {   
        for (Vehicle vehiclu : fleet) {
            if (vehiclu.getId().equals(v.getId())) {                          
                throw new InvalidOperationException("Vehicle already exists...");
            }
        }
        fleet.add(v);
        distinctModels.add(v.getModel().toLowerCase());
        System.out.println("Vehicle added :" + v.getId() );
    }

   
    public void removeVehicle(String id) throws InvalidOperationException {

        Iterator<Vehicle> it = fleet.iterator();
        boolean removed = false;
        Vehicle removedVehicle=null;
        while (it.hasNext()) {
            Vehicle v = it.next();
            if (v.getId().equals(id)) {   
                removedVehicle=v;         
                it.remove();               
                System.out.println("Vehicle removed :" + id);
                removed = true;
                break;
            }
        }
        if (!removed) {    
            throw new InvalidOperationException("Vehicle with ID " + id + " not present...");
        }
        if(removedVehicle!=null){
            String modeltoremove=removedVehicle.getModel().toLowerCase();
            int modelstill=0;
            for (Vehicle v:fleet){
                if(v.getModel().equals(modeltoremove)){
                    modelstill=1;
                    break;
                }
            }
            if (modelstill==0){
                distinctModels.remove(modeltoremove);
            }
        }
    }

   
    public void startAllJourneys(double distance) {
        System.out.println("Starting Journeys for All Vehicles...");
       
        Iterator<Vehicle> it = fleet.iterator();
        while (it.hasNext()) {                        
            Vehicle v = it.next();                      
            try {               
                System.out.println("Starting journey for " + v.getId());
                v.move(distance);
            } catch (Exception except) { 
                System.out.println("Error for vehicle " + v.getId() + ": " + except.getMessage());          
            }
        }
    }

   
public double getTotalFuelConsumption(double distance) {
    double totalConsumption = 0.0;
    for (Vehicle v : fleet) {
        if (v instanceof FuelConsumable) {
            try {                                                                                
                totalConsumption += ((FuelConsumable) v).consumeFuel(distance);
            } catch (Exception e) {
                System.out.println("Could not calculate fuel for " + v.getId() + ": " + e.getMessage());
            }
        }
    }
    return totalConsumption;
}

    
    public void maintainAll() {
        System.out.println("Doing Maintenance on All Vehicles...");
        Iterator<Vehicle> it = fleet.iterator();
        while (it.hasNext()) {
            Vehicle v = it.next();
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) {
                    m.performMaintenance();
                }
            }
        }
    }

public List<Vehicle> searchByType(Class<?> type) {
    List<Vehicle> foundVehicles = new ArrayList<>();
    
 
    for (Vehicle v : fleet) {

        if (type.isInstance(v)) {
            foundVehicles.add(v);
        }
    }
    return foundVehicles;
}
    


public String generateReport() {
    StringBuilder report = new StringBuilder();
    report.append("  Generating Report  \n");
    report.append("Total Vehicles :  ").append(fleet.size()).append("\n");
    
    Map<String,Integer> vehicleCountByType = new HashMap<>();
    double totalEfficiency = 0.0;
    double totalMileage = 0.0;
    int vehiclesNeedingMaintenance = 0;
    
    Iterator<Vehicle> iterator = fleet.iterator();
    while (iterator.hasNext()) {
        Vehicle v = iterator.next();
        
        String vehicleType = v.getClass().getSimpleName();
        vehicleCountByType.put(vehicleType, vehicleCountByType.getOrDefault(vehicleType, 0) + 1);
        
        totalEfficiency += v.calculateFuelEfficiency();
        totalMileage += v.getCurrentMileage();
        
        if (((Maintainable) v).needsMaintenance()) {
            vehiclesNeedingMaintenance++;
        }
    }
    
    report.append(" Vehicles by Type : \n");
    for (Map.Entry<String, Integer> entry : vehicleCountByType.entrySet()) {
        report.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }

    double averageEfficiency = fleet.isEmpty() ? 0 : totalEfficiency / fleet.size();
    report.append(String.format("Average Fuel Efficiency: %.2f\n",averageEfficiency));
    
    report.append(String.format("Total Fleet Mileage : %.2f\n",totalMileage));
    
    report.append("Vehicles Needing Maintenance : ").append(vehiclesNeedingMaintenance).append("\n");
    
    report.append("\n--- Assignment 2 Analysis ---\n");
    report.append("Distinct Vehicle Models: ").append(distinctModels.size()).append("\n"); 
    
    if (distinctModels.isEmpty()){
        report.append(" (No distinct models)");
    }
    else{
        List<String> sorted_Models= new ArrayList<>(distinctModels);
        Collections.sort(sorted_Models);
        report.append(" List of Models:\n");
        for(String model: sorted_Models){
            report.append("    -").append(model).append("\n");
        }
    }
    
    Vehicle fastest = getFastestVehicle();
    Vehicle slowest = getSlowestVehicle();
    
    if (fastest != null) {
        report.append(String.format("Fastest Vehicle: %s (%s,%.2f km/h)\n", 
        fastest.getId(),fastest.getModel(), fastest.getMaxSpeed()));
    }
    if (slowest != null) {
        report.append(String.format("Slowest Vehicle: %s (%s,%.2f km/h)\n", 
        slowest.getId(), slowest.getModel(), slowest.getMaxSpeed()));
    }
    return report.toString();
}


public List<Vehicle> getVehiclesNeedingMaintenance() {   
    List<Vehicle> maintenanceList = new ArrayList<>();
    for(Vehicle v : fleet){
        if (((Maintainable) v).needsMaintenance()) {
            maintenanceList.add(v);
        }
    }
    return maintenanceList;
}

    public List<Vehicle> getFleet() {
        return fleet;
    }
    
    public void refuelAll(double amount) {
        System.out.println("...Refueling All Fuel-Consumable Vehicles...");
        
        for (Vehicle v : fleet) {
            
            if (v instanceof FuelConsumable) {
                try {
                    
                    ((FuelConsumable) v).refuel(amount);
                    System.out.println("Vehicle " + v.getId() + " refueled with " + amount + " units.");
                } catch (InvalidOperationException e) {
                
                System.out.println("Error refueling vehicle " + v.getId() + ": " + e.getMessage());
            }
        }
    }
}

public void saveToFile(String filename) throws IOException {
    try (
        FileWriter fileWriter = new FileWriter(filename); 
        PrintWriter writer = new PrintWriter(fileWriter);
        ) {
            for (Vehicle v : fleet) {
                String l = writeinCSVformat(v);              
                writer.println(l);
            }
            System.out.println("Successfully save Fleet data to:   " + filename);        
        } catch (IOException e) {
            throw new IOException("Error saving file: " + e.getMessage());
        }
    }
    
    private String writeinCSVformat(Vehicle v){
        
        String TypeVehicle = v.getClass().getSimpleName();
        
        StringBuilder s = new StringBuilder();
        s.append(v.getClass().getSimpleName()).append(",");
        s.append(v.getId()).append(",");
        s.append(v.getModel()).append(",");
        s.append(v.getMaxSpeed()).append(",");
        s.append(v.getCurrentMileage());
        
        switch (TypeVehicle) {
            case "Car":
            s.append(",").append(((Car) v).getNumWheels());
            break;
            case "Truck":
            s.append(",").append(((Truck) v).getNumWheels());
            break;
            case "Bus":
            s.append(",").append(((Bus) v).getNumWheels());
            break;
            case "Airplane":
            s.append(",").append(((Airplane) v).getMaxAltitude());
            break;
            case "CargoShip":
            s.append(",").append(((CargoShip) v).hasSail());
            break;
        }
        
        return s.toString();
    }
    
    public void loadFromFile(String filename) throws IOException, InvalidOperationException {
        fleet.clear(); 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String l;
            while ((l = reader.readLine()) != null) {
                
                Vehicle v = createVehicle(l);
                if (v != null) {
                    fleet.add(v);
                    distinctModels.add(v.getModel().toLowerCase());
                }
            }
            System.out.println("Successfully Fleet data loaded from :  " + filename);
        } catch (IOException exception) {
            throw new IOException("Error loading the file : " + exception.getMessage());
        }
    }
    
    private Vehicle createVehicle(String l) throws InvalidOperationException {
        String[] data = l.split(",");
        String type = data[0];
        if (data.length < 5) {
            throw new InvalidOperationException("Invalid data format : " + l);
        }
        
        String id = data[1];
        String model = data[2];
        double maxSpeed = 0.0;
        double currentMileage = 0.0;
        try {
            maxSpeed = Double.parseDouble(data[3]);
        currentMileage = Double.parseDouble(data[4]);
        if (type.equals("Car")) {
            int numWheelsCar = Integer.parseInt(data[5]);
            Car car = new Car(id, model, maxSpeed, numWheelsCar);
            car.addMileage(currentMileage);
            return car;
        } else if (type.equals("Truck")) {
            int numWheelsTruck = Integer.parseInt(data[5]);
            Truck truck = new Truck(id, model, maxSpeed, numWheelsTruck);
            truck.addMileage(currentMileage);
            return truck;
        } else if (type.equals("Bus")) {
            int numWheelsBus = Integer.parseInt(data[5]);
            Bus bus = new Bus(id, model, maxSpeed, numWheelsBus);
            bus.addMileage(currentMileage);
            return bus;
        } else if (type.equals("Airplane")) {
            double maxAltitude = Double.parseDouble(data[5]);
            Airplane airplane = new Airplane(id, model, maxSpeed, maxAltitude);
            airplane.addMileage(currentMileage);
            return airplane;
        } else if (type.equals("CargoShip")) {
            boolean hasSail = Boolean.parseBoolean(data[5]);
            CargoShip cargoShip = new CargoShip(id, model, maxSpeed, hasSail);
            cargoShip.addMileage(currentMileage);
            return cargoShip;
        } else {
            
            throw new InvalidOperationException("Unknown vehicle type : " + type);
        }
        
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
        throw new InvalidOperationException("Invalid numeric data or missing field in CSV line: " + l);
    }
}

public Set<String> getDistinctModels() {
    return distinctModels;
}
public void sortFleetByEfficiency() {
    Collections.sort(fleet);
    System.out.println("vehicle sorted by (high to low): ");
}
private static class VehicleMaxSpeedComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle v1, Vehicle v2) {
        return Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed());
    }
}

private static class VehicleComparatorModel implements Comparator<Vehicle> {
    
    public int compare(Vehicle v1, Vehicle v2) {
            return v1.getModel().compareToIgnoreCase(v2.getModel());
        }
    }

public Vehicle getFastestVehicle() {
    if (fleet.isEmpty()) return null;
    return Collections.max(fleet, new VehicleMaxSpeedComparator()); 
}

public Vehicle getSlowestVehicle() {
    if (fleet.isEmpty()) return null;
    return Collections.min(fleet, new VehicleMaxSpeedComparator());
}
public void sortFleetByMaxSpeed() {
    Collections.sort(fleet, new VehicleMaxSpeedComparator());
    System.out.println("Fleet sorted by Max Speed");
}
public void sortFleetByModel(){
    Collections.sort(fleet,new VehicleComparatorModel());
    System.out.println("Fleet sorted by Model");
}

}
