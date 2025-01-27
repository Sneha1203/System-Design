import java.util.*;

enum VehicleType {
    CAR,
    TRUCK,
    MOTORCYCLE
}

class Vehicle {
    public String licensePlate;
    public VehicleType vehicleType;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    public VehicleType getType() {
        return vehicleType;
    }
}


class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }
}

class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) {
        super(licensePlate, VehicleType.MOTORCYCLE);
    }
}

class ParkingSpot {
    public int spotNumber;
    public VehicleType vehicleType;
    public Vehicle parkedVehicle;

    public ParkingSpot(int spotNumber, VehicleType vehicleType) {
        this.spotNumber = spotNumber;
        this.vehicleType = vehicleType;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public void parkVehicle(Vehicle vehicle) {
        if(isAvailable() && vehicle.getType() == vehicleType) {
            parkedVehicle = vehicle;
        } else {
            throw new IllegalArgumentException("Invalid vehicle type or spot already occupied.");
        }
    }

    public void unparkVehicle() {
        parkedVehicle = null;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public int getSpotNumber() {
        return spotNumber;
    }
}

class Level {
    public int floor;
    public List<ParkingSpot> parkingSpots;

    public Level(int floor, int numberOfSpots) {
        this.floor = floor;
        parkingSpots = new ArrayList<>(numberOfSpots);

        double spotsForBikes = 0.50;
        double spotsForCars = 0.40;

        int numBikes = (int) (numberOfSpots * spotsForBikes);
        int numCars = (int) (numberOfSpots * spotsForCars);

        for(int i = 1; i <= numBikes; i++) {
            parkingSpots.add(new ParkingSpot(i, VehicleType.MOTORCYCLE));
        }

        for(int i = numBikes + 1; i <= numCars + numBikes; i++) {
            parkingSpots.add(new ParkingSpot(i, VehicleType.CAR));
        }

        for(int i = numCars + numBikes + 1; i <= numberOfSpots; i++) {
            parkingSpots.add(new ParkingSpot(i, VehicleType.TRUCK));
        }
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for(ParkingSpot spot: parkingSpots) {
            if(spot.isAvailable() && spot.getVehicleType() == vehicle.getType()) {
                spot.parkVehicle(vehicle);
                return true;
            }
        }
        return false;
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        for(ParkingSpot spot: parkingSpots) {
            if(!spot.isAvailable() && spot.getVehicleType() == vehicle.getType()) {
                spot.unparkVehicle();
                return true;
            }
        }
        return false;
    }

    public void displayAvailability() {
        System.out.println("Level " + floor + "Availability: ");
        for(ParkingSpot spot: parkingSpots) {
            System.out.println("Spot " + spot.getSpotNumber() + ": " + (spot.isAvailable() ? "Available for: " : "Occupied by: ") + spot.getVehicleType());
        }
    }
}

class ParkingLot {
    public static ParkingLot instance;
    public List<Level> levels;

    public ParkingLot() {
        levels = new ArrayList<>();
    }

    public static ParkingLot getInstance() {
        if(instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addLevel(Level level) {
        levels.add(level);  
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (Level level: levels) {
            if(level.parkVehicle(vehicle)) {
                System.out.println("Vehicle parked successfully.");
                return true;
            }
        }
        System.out.println("Could not park vehicle.");
        return false;
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        for(Level level: levels) {
            if(level.unparkVehicle(vehicle)) {
                return true;
            }
        }
        return false;
    }

    public void displayAvailability() {
        for (Level level: levels) {
            level.displayAvailability();
        }
    }
}


class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        parkingLot.addLevel(new Level(1, 100));
        parkingLot.addLevel(new Level(2, 80));

        Vehicle car = new Car("ABC123");
        Vehicle truck = new Truck("XYZ789");
        Vehicle motorcycle = new Motorcycle("M1234");

        // Park vehicles
        parkingLot.parkVehicle(car);
        parkingLot.parkVehicle(truck);
        parkingLot.parkVehicle(motorcycle);

        // Display availability
        parkingLot.displayAvailability();

        // Unpark vehicle
        parkingLot.unparkVehicle(motorcycle);

        // Display updated availability
        parkingLot.displayAvailability();

    }
}
