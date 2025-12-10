
#1. How to Compile and Run the Program

This program is structured using Java packages. All source files (`.java`) should be organized into their respective package folders (e.g., `fleet`, `vehicles`, `interfaces`, `exceptions`). The main entry point, `Main.java`, should be in the root directory.

To Compile:

1.  Open your terminal or command prompt.
2.  Navigate to the root directory of your project (the one containing `Main.java` and the `fleet`, `vehicles`, etc. folders).
3.  Run the Java compiler (`javac`) targeting all source files:

    ```bash
    javac Main.java fleet/*.java vehicles/*.java interfaces/*.java exceptions/*.java
    ```

To Run:

1.  After successful compilation, run the `Main` class using the `java` command from the same root directory:

    ```bash
    java Main
    ```

2.  This will start the command-line interface (CLI) menu, allowing you to interact with the fleet management system.

# 2. Use of Collections and Justification

This project uses several key components from the Java Collections Framework to manage data efficiently.

List<Vehicle> fleet = new ArrayList<>();`**
    Usage: This is the primary data structure for the entire system. It stores all the `Vehicle` objects in the fleet.
    Justification: An `ArrayList` is used because it provides a dynamic, resizable array. It's ideal for our needs as we frequently need to:
        1.  Add and remove vehicles.
        2.  Iterate over the entire fleet (e.g., in `generateReport`, `startAllJourneys`).
        3.  Sort the fleet using different criteria (e.g., `Collections.sort()`).

Set<String> distinctModels = new HashSet<>();`**
    Usage: This set stores the unique model names of all vehicles in the fleet.
    Justification: A `HashSet` is the perfect choice for this task. Its main feature is that it **automatically enforces uniqueness**. When we add a model (`distinctModels.add(v.getModel().toLowerCase())`), the set handles duplicate entries automatically. This makes getting a count of unique models (`distinctModels.size()`) and listing them extremely fast and efficient, without needing to write complex filtering logic.

Map<String, Integer> vehicleCountByType = new HashMap<>();`**
    Usage: This map is created temporarily inside the `generateReport()` method.
    Justification:** A `HashMap` is used to count the number of vehicles of each type (e.g., "Car": 2, "Truck": 1). It provides a highly efficient key-value lookup. We use the vehicle's class name as the **key** and the count as the **value**. The `getOrDefault()` method makes it easy to increment the count for each vehicle type while iterating through the fleet.

Iterator<Vehicle> it = fleet.iterator();`**
    Usage: The `Iterator` is used in methods like `removeVehicle()`, `startAllJourneys()`, and `maintainAll()`.
    Justification:** While a `for-each` loop is simpler for just reading data, the `Iterator` is **essential** for `removeVehicle()`. Calling `fleet.remove()` inside a standard `for-each` loop would cause a `ConcurrentModificationException`. The `it.remove()` method is the only safe way to modify a collection while iterating over it.

Collections.sort()`, `Collections.max()`, `Collections.min()`**
    Usage: These utility methods are used for sorting the fleet and finding the fastest/slowest vehicles.
    Justification:** The Collections framework provides powerful, pre-built algorithms.
         `Collections.sort(fleet, new Vehicle...Comparator())` lets us sort the `fleet` list "in-place" using custom logic (like speed or model name) defined in a `Comparator` class.
         `Collections.max(fleet, new VehicleMaxSpeedComparator())` is a highly efficient way to find the "largest" element in the list based on our speed comparator, which is much cleaner than writing a manual `for` loop.

# 3. File I/O Implementation

File input/output is implemented using classic Java `java.io` classes to save and load the fleet data in a plain-text CSV (Comma-Separated Values) format.

Saving to File (`saveToFile`)**

1.  Technology:Uses `FileWriter` wrapped in a `PrintWriter` for efficient text writing. These are initialized in a `try-with-resources` block, which automatically closes the file streams, preventing resource leaks.
2.  Process:
    * The method iterates through the `fleet` list.
    * For each `Vehicle` object, it calls a private helper method: `writeinCSVformat(v)`.
    * This helper method uses a `StringBuilder` to construct a single CSV string. It appends common data (class name, ID, model, speed) and then uses a `switch` statement on the vehicle's class to append specific data (e.g., `numWheels` for a `Car`).
    * `PrintWriter.println()` writes this string to the file, followed by a new line.

Loading from File (`loadFromFile`)

1.  Technology: Uses `FileReader` wrapped in a `BufferedReader` for efficient line-by-line reading. This is also in a `try-with-resources` block.
2.  Process:
    * The method first clears the `fleet` and `distinctModels` to prevent data duplication.
    * It reads the file line by line using `BufferedReader.readLine()`.
    * Each line (CSV string) is passed to a private "factory" method: `createVehicle(String l)`.
3.  Factory Method (`createVehicle`)
    * This method is the core of the loading logic. It `split()`s the CSV line by commas.
    * It uses an `if-else if` chain (based on the first data field, e.g., "Car", "Truck") to determine which type of vehicle to create.
    * It parses the string data into `double`, `int`, etc., and constructs the correct object (`new Car(...)`, `new Airplane(...)`).
    * This object is returned, added to the `fleet` list, and its model is added to the `distinctModels` set.
    * This design handles `NumberFormatException` and `InvalidOperationException` if the file data is corrupt.


# 4. Sample Run

Below is a sample CLI session demonstrating the program's features.

Welcome to the Fleet Management System! (1) Add Vehicle (2) Remove Vehicle (3) Start All Journeys (1000km) (4) Generate Report (5) Sort and Display by Model (6) Sort and Display by Max Speed (7) Save Fleet to File (8) Load Fleet from File (0) Exit

1 Enter vehicle type (Car, Truck, Airplane, Bus, CargoShip): Car Enter ID: C-101 Enter Model: Civic Enter Max Speed: 180 Enter Number of Wheels: 4 Vehicle added :C-101 1 Enter vehicle type (Car, Truck, Airplane, Bus, CargoShip): Truck Enter ID: T-501 Enter Model: Cybertruck Enter Max Speed: 120 Enter Number of Wheels: 6 Vehicle added :T-501 1 Enter vehicle type (Car, Truck, Airplane, Bus, CargoShip): Airplane Enter ID: A-747 Enter Model: Boeing 747 Enter Max Speed: 900 Enter Number of Altitude: 40000 Vehicle added :A-747 1 Enter vehicle type (Car, Truck, Airplane, Bus, CargoShip): Car Enter ID: C-101 Enter Model: Accord Enter Max Speed: 190 Enter Number of Wheels: 4 Exception in thread "main" exceptions.InvalidOperationException: Vehicle already exists...

3 Starting Journeys for All Vehicles... Starting journey for C-101 Starting journey for T-501 Starting journey for A-747

4 Generating Report

Total Vehicles : 3

Vehicles by Type :

Car: 1

Truck: 1

Airplane: 1

Average Fuel Efficiency: 345.67 Total Fleet Mileage : 3000.00 Vehicles Needing Maintenance : 0

--- Assignment 2 Analysis --- Distinct Vehicle Models: 3 List of Models: - boeing 747 - civic - cybertruck Fastest Vehicle: A-747 (Boeing 747, 900.00 km/h) Slowest Vehicle: T-501 (Cybertruck, 120.00 km/h)

5 --- Fleet Sorted by Model Name (A-Z) --- Model | ID | Type

Boeing 747 | A-747 | Airplane

Civic | C-101 | Car

Cybertruck | T-501 | Truck

7 Enter filename to save (e.g., fleet.csv): my_fleet.csv Successfully save Fleet data to: my_fleet.csv

2 Enter Vehicle ID to remove: T-501 Vehicle removed :T-501

4 Generating Report

Total Vehicles : 2 ... (Report now shows only 2 vehicles) ... --- Assignment 2 Analysis --- Distinct Vehicle Models: 2 List of Models: - boeing 747 - civic ...

8 Enter filename to load (e.g., fleet.csv): my_fleet.csv Successfully Fleet data loaded from : my_fleet.csv

4 Generating Report

Total Vehicles : 3 ... (Report shows all 3 vehicles again) ...

0 Exiting...


1. How to Compile and Run 

Files required:

highway.java

SimVehicle.java

HighwaySimulatorGUI.java

Compile:

javac h.java SimVehicle.java HighwaySimulatorGUI.java


Run:

java HighwaySimulatorGUI


Program GUI window open karega aur saari interaction GUI se hoti hai.
Console input yaha use nahi hota.

2. GUI Features Used (Swing)

GUI simple Swing components se bani hai:

JFrame — main window

JPanel — grouping for controls and labels

JLabel — vehicle and highway distance display

JButton — Start, Pause, Resume, Stop, Refuel buttons

JCheckBox — synchronization on/off toggle

BorderLayout — top, center, bottom layout

GridLayout — 3 vehicle labels vertically

ActionListener with lambda expressions

Swing Timer (500ms) — labels ko refresh karta hai

SwingUtilities.invokeLater() — GUI safe thread par start hoti hai (EDT)

Saare GUI updates EDT pe hote hain aur background threads sirf model update karte hain.

3. Threading Design

Har vehicle ka apna thread hota hai, jo Runnable implement karta hai.

Vehicle thread ka kaam:

Har second mileage ++

Fuel --

h distance update karna

Fuel 0 hote hi automatically “Out-of-Fuel”

Refuel ke baad resume

Stop button dabane par clean exit

Thread control methods:

pause()

resume()

stop()

refuel()

Mileage, fuel, status getters synchronized rakhe gaye hain taaki GUI safely read kare.

4. Shared Counter and Race Condition

Shared variable:

public static int highwayDistance = 0;


Har vehicle thread:

h.increment();


Do modes:

Unsynchronized (CheckBox OFF)
highwayDistance++;


Result galat hota hai

Kabhi 57, kabhi 59, kabhi 56

Ye intentional hai (race condition demo)

Synchronized (CheckBox ON)
synchronized(lock) {
    highwayDistance++;
}


Saare updates correct

Shared value = sum of all vehicle mileages

Race condition fix ho jata hai

Assignment ne dono behaviors show karne ka bola tha.

5. Simulation Flow (GUI)

Start:

3 vehicle threads start

Labels real-time update

Fuel kam hota hai

Mileage badhta hai

Shared counter badhta hai

Out-of-Fuel:

Vehicle ruk jaata hai

Status update hota hai

Refuel X:

10 fuel units add

Vehicle automatically resume

Stop:

Saare threads cleanly band

Simulation end
