import java.util.*;
import java.text.NumberFormat;
import java.util.Locale;

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

    public String getEmail() {  
        return email;
    }

    public String getCity() {  
        return city;
    }

    public int getAge() { 
        return age;
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

    public int getTotalSeats() {
        return totalSeats;
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

    public void registerCustomer(Customer customer) {
        if (customers.containsKey(customer.getName())) {
            System.out.println("Customer with this name already exists.");
            return;
        }
        customers.put(customer.getName(), customer);
        System.out.println("\n=== Customer Registration Successful ===");
        System.out.println(String.format("%-20s: %s", "Customer Name", customer.getName()));
        System.out.println(String.format("%-20s: %s", "Mobile Number", customer.getMobileNumber()));
        System.out.println(String.format("%-20s: %s", "Email", customer.getEmail()));  
        System.out.println(String.format("%-20s: %s", "City", customer.getCity()));    
        System.out.println(String.format("%-20s: %d", "Age", customer.getAge()));      
        System.out.println("=========================================");
    }

    public void registerBus(Bus bus) {
        if (buses.containsKey(bus.getBusNumber())) {
            System.out.println("Bus with this number already exists.");
            return;
        }
        buses.put(bus.getBusNumber(), bus);
        System.out.println("\n=== Bus Registration Successful ===");
        System.out.println(String.format("%-20s: %s", "Bus Number", bus.getBusNumber()));
        System.out.println(String.format("%-20s: %d", "Total Seats", bus.getTotalSeats()));  
        System.out.println(String.format("%-20s: %s", "Starting Point", bus.getStartingPoint()));
        System.out.println(String.format("%-20s: %s", "Ending Point", bus.getEndingPoint()));
        System.out.println(String.format("%-20s: %s", "Starting Time", bus.getStartingTime()));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String formattedFare = currencyFormat.format(bus.getFare());
        System.out.println(String.format("%-20s: %s", "Fare", formattedFare));
        System.out.println("=====================================");
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

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                String formattedFare = currencyFormat.format(bus.getFare());
                System.out.printf("%-20s: %s\n", "Fare", formattedFare);
                System.out.printf("%-20s: %s\n", "Available Seats", bus.getAvailableSeats());
                System.out.println("========================================================");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No buses found for the given route.");
        }
    }

    public void reserveSeat(String customerName, String busNumber) {
        if (!customers.containsKey(customerName)) {
            System.out.println("Invalid Customer Name: " + customerName);
            return;
        }
        if (!buses.containsKey(busNumber)) {
            System.out.println("Invalid Bus Number: " + busNumber);
            return;
        }

        Customer customer = customers.get(customerName);
        Bus bus = buses.get(busNumber);

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

            System.out.println("\n=== Reservation Details ===");
            System.out.println("Customer: " + customer.getName());
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Bus: " + bus.getBusNumber());
            System.out.println("Seat: " + selectedSeat);

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            String formattedFare = currencyFormat.format(bus.getFare());
            System.out.println(String.format("Total Fare: %s", formattedFare));  // Displaying fare in Rs.

            System.out.print("Do you want to confirm the payment (Yes/No)? ");
            String paymentConfirmation = scanner.next();

            if (paymentConfirmation.equalsIgnoreCase("Yes")) {
                System.out.println("Payment Successful!");
                // Sent Notification message
                System.out.println("\n      Sent Notification");
                System.out.println("\n=== Reservation Successful ===");
                System.out.println("Customer: " + customer.getName());
                System.out.println("Reservation ID: " + reservation.getReservationId());
                System.out.println("Bus: " + bus.getBusNumber());
                System.out.println("Seat: " + selectedSeat);
                System.out.println("==============================\n");
            } else {
                bus.cancelSeat(selectedSeat);
                reservations.remove(reservation);
                System.out.println("Reservation Canceled due to payment failure.");
            }
        } else {
            System.out.println("The selected seat is not available. Please choose another seat.");
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

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            String formattedFare = currencyFormat.format(reservation.getBus().getFare());
            System.out.printf("%-20s: %s\n", "Fare", formattedFare);
            System.out.println("========================================================");
        }
    }

    public void displayWaitingQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("Please waiting for five minutes in queue.");
            return;
        }

        System.out.println("\nCurrent Waiting Queue:");
        for (Customer customer : waitingQueue) {
            System.out.println(customer);
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

        if (reservationToCancel != null) {
            reservations.remove(reservationToCancel);
            Bus bus = reservationToCancel.getBus();
            bus.cancelSeat(reservationToCancel.getSeatNumber());

            System.out.println("\n      Sent Notification");
            System.out.println("\n=== Reservation Canceled ===");
            System.out.println("Customer: " + reservationToCancel.getCustomer().getName());
            System.out.println("Reservation ID: " + reservationToCancel.getReservationId());
            System.out.println("Bus: " + bus.getBusNumber());
            System.out.println("Seat: " + reservationToCancel.getSeatNumber());

            // Using NumberFormat to display refund in Rs.
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            String formattedRefund = currencyFormat.format(bus.getFare());
            System.out.println(String.format("Refund Amount: %s", formattedRefund));  // Show refund amount
            System.out.println("==============================\n");
        } else {
            System.out.println("No reservation found with this ID.");
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
            System.out.println("7. Add Another Reserve Seat");   
            System.out.println("8. Exit");
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
                    system.registerCustomer(new Customer(name, mobile, email, city, age));
                    break;

                case 2:
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
                    system.registerBus(new Bus(busNumber, totalSeats, start, end, time, fare));
                    break;

                case 3:
                    System.out.print("Enter Starting Point: ");
                    String searchStart = scanner.nextLine();
                    System.out.print("Enter Ending Point: ");
                    String searchEnd = scanner.nextLine();
                    system.searchBuses(searchStart, searchEnd);
                    break;

                case 4:
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter Bus Number: ");
                    String reserveBusNumber = scanner.nextLine();
                    system.reserveSeat(customerName, reserveBusNumber);
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

                case 7:  
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
