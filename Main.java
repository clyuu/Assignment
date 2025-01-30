import java.util.*;

class Customer {
    private String name;
    private String mobileNumber;
    private String email;
    private String city;
    private int age;

    public Customer(String name, String mobileNumber, String email, String city, int age) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.city = city;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', mobile='" + mobileNumber + "', city='" + city + "', age=" + age + "}";
    }
}

class Bus {
    private String busNumber;
    private int totalSeats;
    private String startingPoint;
    private String endingPoint;
    private String startingTime;
    private double fare;
    private List<Integer> availableSeats;

    public Bus(String busNumber, int totalSeats, String startingPoint, String endingPoint, String startingTime, double fare) {
        this.busNumber = busNumber;
        this.totalSeats = totalSeats;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.startingTime = startingTime;
        this.fare = fare;
        this.availableSeats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            availableSeats.add(i);
        }
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public double getFare() {
        return fare;
    }

    public List<Integer> getAvailableSeats() {
        return availableSeats;
    }

    public boolean reserveSeat(int seatNumber) {
        return availableSeats.remove((Integer) seatNumber);
    }

    public void cancelSeat(int seatNumber) {
        availableSeats.add(seatNumber);
        bubbleSort(availableSeats);
    }

    private void bubbleSort(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Bus{busNumber='" + busNumber + "', startingPoint='" + startingPoint + "', endingPoint='" + endingPoint + "', fare=" + fare + "}";
    }
}

class Reservation {
    private static int idCounter = 1;
    private int reservationId;
    private Customer customer;
    private Bus bus;
    private int seatNumber;

    public Reservation(Customer customer, Bus bus, int seatNumber) {
        this.reservationId = idCounter++;
        this.customer = customer;
        this.bus = bus;
        this.seatNumber = seatNumber;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Bus getBus() {
        return bus;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "Reservation{id=" + reservationId + ", customer=" + customer + ", bus=" + bus + ", seatNumber=" + seatNumber + "}";
    }
}

class BusReservationSystem {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Bus> buses = new HashMap<>();
    private List<Reservation> reservations = new ArrayList<>();
    private Queue<Customer> waitingQueue = new LinkedList<>();

    public void registerCustomer(String id, Customer customer) {
        if (customers.containsKey(id)) {
            System.out.println("Customer with this ID already exists.");
            return;
        }
        customers.put(id, customer);
        System.out.println("Customer registered successfully: " + customer);
    }

    public void registerBus(String id, Bus bus) {
        if (buses.containsKey(id)) {
            System.out.println("Bus with this ID already exists.");
            return;
        }
        buses.put(id, bus);
        System.out.println("Bus registered successfully: " + bus);
    }

    public void searchBuses(String startingPoint, String endingPoint) {
        System.out.println("\nSearching buses from " + startingPoint + " to " + endingPoint);
        boolean found = false;
        for (Bus bus : buses.values()) {
            if (bus.getStartingPoint().equalsIgnoreCase(startingPoint) && bus.getEndingPoint().equalsIgnoreCase(endingPoint)) {
                System.out.println("========================================================");
                System.out.printf("%-20s: %s\n", "Bus Number", bus.getBusNumber());
                System.out.printf("%-20s: %s\n", "Starting Point", bus.getStartingPoint());
                System.out.printf("%-20s: %s\n", "Ending Point", bus.getEndingPoint());
                System.out.printf("%-20s: %s\n", "Starting Time", bus.getStartingTime());
                System.out.printf("%-20s: %.2f\n", "Fare", bus.getFare());
                System.out.printf("%-20s: %s\n", "Available Seats", bus.getAvailableSeats());
                System.out.println("========================================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No buses found for the given route.");
        }
    }

    public void reserveSeat(String customerId, String busId) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Invalid Customer ID: " + customerId);
            return;
        }
        if (!buses.containsKey(busId)) {
            System.out.println("Invalid Bus ID: " + busId);
            return;
        }

        Customer customer = customers.get(customerId);
        Bus bus = buses.get(busId);

        if (bus.getAvailableSeats().isEmpty()) {
            System.out.println("No available seats. Adding customer to the waiting list.");
            waitingQueue.add(customer);
            return;
        }

        System.out.println("Available seats for Bus " + bus.getBusNumber() + ": " + bus.getAvailableSeats());
        System.out.print("Please select a seat number: ");
        Scanner scanner = new Scanner(System.in);
        int selectedSeat = scanner.nextInt();

        if (bus.getAvailableSeats().contains(selectedSeat)) {
            bus.reserveSeat(selectedSeat);
            Reservation reservation = new Reservation(customer, bus, selectedSeat);
            reservations.add(reservation);
            System.out.println("\n      Sent Notification");
            System.out.println("=== Reservation Successful ===");
            System.out.println("Customer: " + customer.getName());
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Bus: " + bus.getBusNumber());
            System.out.println("Seat: " + selectedSeat);
            System.out.println("==============================\n");
        } else {
            System.out.println("The selected seat is not available. Please choose another seat.");
        }
    }

    public void cancelReservation(int reservationId) {
        Reservation reservationToCancel = null;
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel == null) {
            System.out.println("Reservation ID not found.");
            return;
        }

        reservations.remove(reservationToCancel);
        Bus bus = reservationToCancel.getBus();
        int seatNumber = reservationToCancel.getSeatNumber();
        bus.cancelSeat(seatNumber);
        System.out.println("\n      Sent Notification");
        System.out.println("=== Reservation Canceled ===");
        System.out.println("Customer: " + reservationToCancel.getCustomer().getName());
        System.out.println("Reservation ID: " + reservationToCancel.getReservationId());
        System.out.println("Refund Amount: " + bus.getFare());
        System.out.println("============================\n");

        if (!waitingQueue.isEmpty()) {
            Customer nextCustomer = waitingQueue.poll();
            reserveSeat(nextCustomer.getMobileNumber(), bus.getBusNumber());
        }
    }

    public void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("\nAll Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println("========================================================");
            System.out.printf("%-20s: %d\n", "Reservation ID", reservation.getReservationId());
            System.out.printf("%-20s: %s\n", "Customer Name", reservation.getCustomer().getName());
            System.out.printf("%-20s: %s\n", "Mobile Number", reservation.getCustomer().getMobileNumber());
            System.out.printf("%-20s: %s\n", "Bus Number", reservation.getBus().getBusNumber());
            System.out.printf("%-20s: %d\n", "Seat Number", reservation.getSeatNumber());
            System.out.printf("%-20s: %s\n", "Starting Point", reservation.getBus().getStartingPoint());
            System.out.printf("%-20s: %s\n", "Ending Point", reservation.getBus().getEndingPoint());
            System.out.printf("%-20s: %.2f\n", "Fare", reservation.getBus().getFare());
            System.out.println("========================================================");
        }
    }

    // New method to display waiting queue
    public void displayWaitingQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("Please waiting for five minuts in queue.");
            return;
        }

        System.out.println("\nCurrent Waiting Queue:");
        for (Customer customer : waitingQueue) {
            System.out.println(customer);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BusReservationSystem system = new BusReservationSystem();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register Customer");
            System.out.println("2. Register Bus");
            System.out.println("3. Search Buses");
            System.out.println("4. Reserve Seat");
            System.out.println("5. Cancel Reservation");
            System.out.println("6. Display Reservations");
            System.out.println("7. Add Another Reservation");  // New option
            System.out.println("8. Exit");                   // Updated exit
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for menu choice.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Mobile Number: ");
                    String mobile = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age;
                    try {
                        age = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Age must be a number.");
                        scanner.nextLine();
                        break;
                    }
                    system.registerCustomer(id, new Customer(name, mobile, email, city, age));
                    break;

                case 2:
                    System.out.print("Enter Bus ID: ");
                    String busId = scanner.nextLine();
                    System.out.print("Enter Bus Number: ");
                    String busNumber = scanner.nextLine();
                    System.out.print("Enter Total Seats: ");
                    int totalSeats;
                    try {
                        totalSeats = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Total seats must be a number.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Enter Starting Point: ");
                    String start = scanner.nextLine();
                    System.out.print("Enter Ending Point: ");
                    String end = scanner.nextLine();
                    System.out.print("Enter Starting Time: ");
                    String time = scanner.nextLine();
                    System.out.print("Enter Fare: ");
                    double fare;
                    try {
                        fare = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Fare must be a number.");
                        scanner.nextLine();
                        break;
                    }
                    system.registerBus(busId, new Bus(busNumber, totalSeats, start, end, time, fare));
                    break;

                case 3:
                    System.out.print("Enter Starting Point: ");
                    String searchStart = scanner.nextLine();
                    System.out.print("Enter Ending Point: ");
                    String searchEnd = scanner.nextLine();
                    system.searchBuses(searchStart, searchEnd);
                    break;

                case 4:
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Bus ID: ");
                    String reserveBusId = scanner.nextLine();
                    system.reserveSeat(customerId, reserveBusId);
                    break;

                case 5:
                    System.out.print("Enter Reservation ID to cancel: ");
                    int reservationId;
                    try {
                        reservationId = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Reservation ID must be a number.");
                        scanner.nextLine();
                        break;
                    }
                    system.cancelReservation(reservationId);
                    break;

                case 6:
                    system.displayReservations();
                    break;

                case 7:  // New case for waiting queue
                    system.displayWaitingQueue();
                    break;

                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}