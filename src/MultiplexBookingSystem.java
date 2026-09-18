import java.util.*;
import java.util.stream.Collectors;

// --- ENUMS ---
enum SeatTier {
    VIP(500.0), PREMIUM(350.0), EXECUTIVE(250.0), NORMAL(150.0);
    private final double price;
    SeatTier(double price) { this.price = price; }
    public double getPrice() { return price; }
}

enum SnackItem {
    POPCORN("Large Caramel Popcorn", 220.0),
    NACHOS("Jalapeno Cheese Nachos", 180.0),
    COKE("Fountain Coke", 120.0),
    WATER("Mineral Water", 60.0);

    private final String description;
    private final double price;

    SnackItem(String description, double price) {
        this.description = description;
        this.price = price;
    }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
}

// --- ENTITIES ---
class Seat {
    private final String seatId;
    private final SeatTier tier;
    private boolean isBooked;

    public Seat(String seatId, SeatTier tier) {
        this.seatId = seatId;
        this.tier = tier;
        this.isBooked = false;
    }

    public String getSeatId() { return seatId; }
    public SeatTier getTier() { return tier; }
    public boolean isBooked() { return isBooked; }
    public void book() { this.isBooked = true; }
    public void unbook() { this.isBooked = false; }
}

class Movie {
    private final String movieId;
    private final String title;
    private final String genre;

    public Movie(String movieId, String title, String genre) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
    }

    public String getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieId.equals(movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}

class Show {
    private final String showId;
    private final Movie movie;
    private final String screenName;
    private final String time;
    private final List<Seat> seats;
    private double totalRevenueGenerated;

    public Show(String showId, Movie movie, String screenName, String time) {
        this.showId = showId;
        this.movie = movie;
        this.screenName = screenName;
        this.time = time;
        this.seats = generateTieredSeats();
        this.totalRevenueGenerated = 0.0;
    }

    private List<Seat> generateTieredSeats() {
        List<Seat> newSeats = new ArrayList<>();
        for (int i = 1; i <= 6; i++) newSeats.add(new Seat("A" + i, SeatTier.VIP));
        for (char row = 'B'; row <= 'C'; row++) {
            for (int i = 1; i <= 6; i++) newSeats.add(new Seat(row + String.valueOf(i), SeatTier.PREMIUM));
        }
        for (char row = 'D'; row <= 'E'; row++) {
            for (int i = 1; i <= 6; i++) newSeats.add(new Seat(row + String.valueOf(i), SeatTier.EXECUTIVE));
        }
        for (int i = 1; i <= 6; i++) newSeats.add(new Seat("F" + i, SeatTier.NORMAL));
        return newSeats;
    }

    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public String getScreenName() { return screenName; }
    public String getTime() { return time; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalRevenueGenerated() { return totalRevenueGenerated; }
    public void addRevenue(double amount) { this.totalRevenueGenerated += amount; }
    public void deductRevenue(double amount) { this.totalRevenueGenerated -= amount; }

    public void displaySeatMap() {
        System.out.println("\n============== [ " + screenName.toUpperCase() + " ] ==============");
        SeatTier currentTier = null;
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            if (currentTier != seat.getTier()) {
                currentTier = seat.getTier();
                System.out.println("\n--- " + currentTier.name() + " CLASS (Rs. " + currentTier.getPrice() + ") ---");
            }
            if (seat.isBooked()) {
                System.out.print("[ XX ] ");
            } else {
                System.out.printf("[%4s] ", seat.getSeatId());
            }
            if ((i + 1) % 6 == 0) System.out.println();
        }
        System.out.println("==========================================\n");
    }
}

class Ticket {
    private final String ticketId;
    private final Show show;
    private final List<Seat> bookedSeats;
    private final List<SnackItem> snacks;

    private final double seatTotal;
    private final double snackTotal;
    private final double discountApplied;
    private final double gstAmount;
    private final double convenienceFee;
    private final double grandTotal;
    private final String earnedPromoCode;

    public Ticket(Show show, List<Seat> bookedSeats, List<SnackItem> snacks,
                  double seatTotal, double snackTotal, double discountApplied,
                  double gstAmount, double convenienceFee, double grandTotal, String earnedPromoCode) {
        this.ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.snacks = snacks;
        this.seatTotal = seatTotal;
        this.snackTotal = snackTotal;
        this.discountApplied = discountApplied;
        this.gstAmount = gstAmount;
        this.convenienceFee = convenienceFee;
        this.grandTotal = grandTotal;
        this.earnedPromoCode = earnedPromoCode;
    }

    public String getTicketId() { return ticketId; }
    public Show getShow() { return show; }
    public List<Seat> getBookedSeats() { return bookedSeats; }
    public double getGrandTotal() { return grandTotal; }
    public String getEarnedPromoCode() { return earnedPromoCode; }

    public void printTicket() {
        System.out.println("\n+--------------------------------------------------+");
        System.out.println("|                 CONFIRMED TICKET                 |");
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Ticket ID : %-36s |\n", ticketId);
        System.out.printf("| Movie     : %-36s |\n", show.getMovie().getTitle());
        System.out.printf("| Screen    : %-15s | Time: %-12s |\n", show.getScreenName(), show.getTime());

        String seatNumbers = bookedSeats.stream().map(Seat::getSeatId).collect(Collectors.joining(", "));
        if (seatNumbers.length() > 36) seatNumbers = seatNumbers.substring(0, 33) + "...";
        System.out.printf("| Seats     : %-36s |\n", seatNumbers);

        if (!snacks.isEmpty()) {
            String snackList = snacks.stream().map(SnackItem::name).collect(Collectors.joining(", "));
            if (snackList.length() > 36) snackList = snackList.substring(0, 33) + "...";
            System.out.printf("| Snacks    : %-36s |\n", snackList);
        }

        System.out.println("|--------------------------------------------------|");
        System.out.printf("| Seats Subtotal             : Rs. %10.2f    |\n", seatTotal);
        System.out.printf("| Snacks Subtotal            : Rs. %10.2f    |\n", snackTotal);

        if (discountApplied > 0) {
            System.out.printf("| Discount Applied           :-Rs. %10.2f    |\n", discountApplied);
        }

        System.out.println("|                                                  |");
        System.out.printf("| GST (18%%)                  : Rs. %10.2f    |\n", gstAmount);
        System.out.printf("| Convenience Fee            : Rs. %10.2f    |\n", convenienceFee);
        System.out.println("|--------------------------------------------------|");
        System.out.printf("| GRAND TOTAL                : Rs. %10.2f    |\n", grandTotal);

        if (earnedPromoCode != null) {
            System.out.println("|==================================================|");
            System.out.println("| 🌟 BULK BOOKING REWARD UNLOCKED 🌟               |");
            System.out.println("| You booked 5+ tickets! Enjoy 20% OFF next time!  |");
            System.out.printf("| Your One-Time Code: %-28s |\n", earnedPromoCode);
        }
        System.out.println("+--------------------------------------------------+\n");
    }
}

// --- MAIN SYSTEM CONTROLLER ---
public class MultiplexBookingSystem {
    private static final List<Movie> movies = new ArrayList<>();
    private static final List<Show> shows = new ArrayList<>();
    private static final List<Ticket> userWallet = new ArrayList<>();

    private static final Map<String, Double> permanentPromoCodes = Map.of("FESTIVAL20", 0.20, "WELCOME10", 0.10);
    private static final Map<String, Double> singleUsePromoCodes = new HashMap<>();
    private static final Set<String> usedPromoCodes = new HashSet<>();

    private static final Scanner scanner = new Scanner(System.in);
    private static int idCounter = 100;

    public static void main(String[] args) {
        seedInitialData();
        runPortalSelection();
    }

    private static void runPortalSelection() {
        while (true) {
            System.out.println("\n============================================");
            System.out.println("       MULTIPLEX MOVIE BOOKING SYSTEM       ");
            System.out.println("============================================");
            System.out.println("1. Login as User");
            System.out.println("2. Login as Admin");
            System.out.println("0. Exit Application");
            System.out.print("Select Portal: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> runUserMenu();
                case "2" -> runAdminMenu();
                case "0" -> {
                    System.out.println("Shutting down... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid selection. Try again.");
            }
        }
    }

    // --- USER MENU ---
    private static void runUserMenu() {
        while (true) {
            System.out.println("\n------------- USER DASHBOARD -------------");
            System.out.println("1. Book Tickets (Browse Movies & Schedules)");
            System.out.println("2. View My Tickets (Wallet)");
            System.out.println("3. Cancel a Ticket");
            System.out.println("0. Back to Main Menu / Logout");
            System.out.print("Select Action: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> processBookingFlow();
                case "2" -> displayUserWallet();
                case "3" -> processCancellation();
                case "0" -> { return; }
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    private static void processBookingFlow() {
        if (movies.isEmpty()) {
            System.out.println("\nNo movies currently playing.");
            return;
        }

        System.out.println("\n--- Now Showing in Theaters ---");
        movies.forEach(m -> System.out.printf("[%s] %s (%s)\n", m.getMovieId(), m.getTitle(), m.getGenre()));

        System.out.print("\nEnter Movie ID to view schedules (or 0 to back): ");
        String movieId = scanner.nextLine().trim().toUpperCase();
        if (movieId.equals("0")) return;

        Optional<Movie> selectedMovieOpt = movies.stream().filter(m -> m.getMovieId().equalsIgnoreCase(movieId)).findFirst();
        if (selectedMovieOpt.isEmpty()) {
            System.out.println("Error: Invalid Movie ID.");
            return;
        }

        Movie selectedMovie = selectedMovieOpt.get();
        List<Show> movieShows = shows.stream()
                .filter(s -> s.getMovie().equals(selectedMovie))
                .sorted(Comparator.comparing(Show::getTime))
                .toList();

        if (movieShows.isEmpty()) {
            System.out.println("No shows scheduled for this movie yet.");
            return;
        }

        System.out.println("\n--- Schedules for: " + selectedMovie.getTitle() + " ---");
        movieShows.forEach(show -> System.out.printf("[%s] %-10s | %s\n", show.getShowId(), show.getScreenName(), show.getTime()));

        System.out.print("\nEnter Show ID to book (or 0 to back): ");
        String showId = scanner.nextLine().trim().toUpperCase();
        if (showId.equals("0")) return;

        Optional<Show> selectedShowOpt = movieShows.stream().filter(s -> s.getShowId().equalsIgnoreCase(showId)).findFirst();
        if (selectedShowOpt.isEmpty()) {
            System.out.println("Error: Invalid Show ID.");
            return;
        }

        Show show = selectedShowOpt.get();
        show.displaySeatMap();

        System.out.print("Enter seat numbers separated by space (e.g., B1 B2 C4) or 0 to cancel: ");
        String seatInput = scanner.nextLine().trim().toUpperCase();
        if (seatInput.equals("0")) return;

        List<String> requestedSeatIds = Arrays.asList(seatInput.split("\\s+"));
        List<Seat> seatsToBook = show.getSeats().stream().filter(seat -> requestedSeatIds.contains(seat.getSeatId())).toList();

        if (seatsToBook.size() != requestedSeatIds.size()) {
            System.out.println("Error: One or more selected seat numbers are invalid.");
            return;
        }
        if (seatsToBook.stream().anyMatch(Seat::isBooked)) {
            System.out.println("Error: One or more selected seats are already booked [ XX ].");
            return;
        }

        double seatTotal = seatsToBook.stream().mapToDouble(s -> s.getTier().getPrice()).sum();
        List<SnackItem> selectedSnacks = processSnackBar();
        double snackTotal = selectedSnacks.stream().mapToDouble(SnackItem::getPrice).sum();
        double subTotal = seatTotal + snackTotal;

        System.out.print("\nEnter Promo Code (Press Enter to skip): ");
        String promo = scanner.nextLine().trim().toUpperCase();

        double discount = 0;

        if (!promo.isEmpty()) {
            if (usedPromoCodes.contains(promo)) {
                System.out.println("❌ ERROR: This promo code has already been claimed. Codes are strictly single-use per user.");
            } else if (permanentPromoCodes.containsKey(promo)) {
                discount = subTotal * permanentPromoCodes.get(promo);
                usedPromoCodes.add(promo);
                System.out.printf("✅ Promo applied! You saved Rs. %.2f\n", discount);
            } else if (singleUsePromoCodes.containsKey(promo)) {
                discount = subTotal * singleUsePromoCodes.get(promo);
                usedPromoCodes.add(promo);
                singleUsePromoCodes.remove(promo);
                System.out.printf("✅ Exclusive Bulk Reward applied! You saved Rs. %.2f\n", discount);
            } else {
                System.out.println("Invalid or expired promo code. Proceeding without discount.");
            }
        }

        // TAX & BILLING CALCULATIONS
        double amountAfterDiscount = subTotal - discount;
        double gstRate = 0.18;
        double convenienceFee = 35.00;
        double gstAmount = amountAfterDiscount * gstRate;
        double grandTotal = amountAfterDiscount + gstAmount + convenienceFee;

        System.out.println("\nProcessing Payment and Generating Final Invoice...");
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        String earnedPromoCode = null;
        if (seatsToBook.size() >= 5) {
            earnedPromoCode = "BULK20-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            singleUsePromoCodes.put(earnedPromoCode, 0.20);
        }

        seatsToBook.forEach(Seat::book);
        show.addRevenue(grandTotal);

        Ticket ticket = new Ticket(show, seatsToBook, selectedSnacks, seatTotal, snackTotal, discount, gstAmount, convenienceFee, grandTotal, earnedPromoCode);
        userWallet.add(ticket);

        System.out.println("✅ PAYMENT SUCCESSFUL!");
        ticket.printTicket();
    }

    private static List<SnackItem> processSnackBar() {
        List<SnackItem> cart = new ArrayList<>();
        SnackItem[] menu = SnackItem.values();

        while (true) {
            System.out.println("\n--- SNACK BAR ---");
            for (int i = 0; i < menu.length; i++) {
                System.out.printf("%d. %-25s - Rs. %.2f\n", (i + 1), menu[i].getDescription(), menu[i].getPrice());
            }
            System.out.println("0. Proceed to Checkout");
            System.out.print("Select item to add: ");

            String choiceStr = scanner.nextLine().trim();
            if (choiceStr.equals("0")) break;

            try {
                int index = Integer.parseInt(choiceStr) - 1;
                if (index >= 0 && index < menu.length) {
                    cart.add(menu[index]);
                    System.out.println("✅ Added: " + menu[index].name());
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return cart;
    }

    private static void displayUserWallet() {
        if (userWallet.isEmpty()) {
            System.out.println("\nYour wallet is empty. No active tickets.");
            return;
        }
        System.out.println("\n--- Your Active Tickets ---");
        userWallet.forEach(Ticket::printTicket);
    }

    private static void processCancellation() {
        if (userWallet.isEmpty()) {
            System.out.println("\nYou have no active tickets to cancel.");
            return;
        }
        displayUserWallet();
        System.out.print("Enter the exact Ticket ID to cancel (or 0 to exit): ");
        String targetId = scanner.nextLine().trim().toUpperCase();
        if (targetId.equals("0")) return;

        Optional<Ticket> ticketOpt = userWallet.stream().filter(t -> t.getTicketId().equalsIgnoreCase(targetId)).findFirst();
        if (ticketOpt.isEmpty()) {
            System.out.println("Error: Ticket ID not found in your wallet.");
            return;
        }

        Ticket ticket = ticketOpt.get();
        ticket.getBookedSeats().forEach(Seat::unbook);
        ticket.getShow().deductRevenue(ticket.getGrandTotal());
        userWallet.remove(ticket);

        System.out.println("\n✅ Ticket " + targetId + " cancelled successfully. Rs. " + ticket.getGrandTotal() + " refunded.");

        if (ticket.getEarnedPromoCode() != null) {
            singleUsePromoCodes.remove(ticket.getEarnedPromoCode());
            System.out.println("⚠️ NOTICE: The bulk reward code (" + ticket.getEarnedPromoCode() + ") generated by this booking has been revoked and destroyed.");
        }
    }

    // --- ADMIN MENU ---
    private static void runAdminMenu() {
        while (true) {
            System.out.println("\n------------- ADMIN DASHBOARD -------------");
            System.out.println("1. View Multiplex Analytics");
            System.out.println("2. Add New Movie");
            System.out.println("3. Add New Show");
            System.out.println("4. Remove Movie");
            System.out.println("0. Back to Main Menu / Logout");
            System.out.print("Select Action: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> displayRevenueAnalytics();
                case "2" -> addMovieOperation();
                case "3" -> addShowOperation();
                case "4" -> removeMovieOperation();
                case "0" -> { return; }
                default -> System.out.println("Invalid selection.");
            }
        }
    }

    private static void addMovieOperation() {
        System.out.print("\nEnter Movie Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Movie Genre: ");
        String genre = scanner.nextLine().trim();

        String newId = "M" + (++idCounter);
        movies.add(new Movie(newId, title, genre));
        System.out.println("✅ Movie Added Successfully! ID: " + newId);
    }

    private static void addShowOperation() {
        if (movies.isEmpty()) {
            System.out.println("No movies available. Add a movie first.");
            return;
        }
        movies.forEach(m -> System.out.printf("[%s] %s\n", m.getMovieId(), m.getTitle()));
        System.out.print("\nEnter Movie ID to create a show for: ");
        String movieId = scanner.nextLine().trim().toUpperCase();

        Optional<Movie> movieOpt = movies.stream().filter(m -> m.getMovieId().equalsIgnoreCase(movieId)).findFirst();
        if (movieOpt.isEmpty()) {
            System.out.println("Invalid Movie ID.");
            return;
        }

        System.out.print("Enter Screen Name (e.g., Screen 1): ");
        String screen = scanner.nextLine().trim();
        System.out.print("Enter Time (e.g., 06:00 PM): ");
        String time = scanner.nextLine().trim();

        String showId = "SH" + (++idCounter);
        shows.add(new Show(showId, movieOpt.get(), screen, time));
        System.out.println("✅ Show Created Successfully! Show ID: " + showId);
    }

    private static void removeMovieOperation() {
        if (movies.isEmpty()) {
            System.out.println("No movies to remove.");
            return;
        }
        movies.forEach(m -> System.out.printf("[%s] %s\n", m.getMovieId(), m.getTitle()));
        System.out.print("\nEnter Movie ID to remove: ");
        String movieId = scanner.nextLine().trim().toUpperCase();

        Optional<Movie> movieOpt = movies.stream().filter(m -> m.getMovieId().equalsIgnoreCase(movieId)).findFirst();
        if (movieOpt.isEmpty()) {
            System.out.println("Movie not found.");
            return;
        }

        Movie target = movieOpt.get();
        userWallet.removeIf(ticket -> ticket.getShow().getMovie().equals(target));
        shows.removeIf(show -> show.getMovie().equals(target));
        movies.remove(target);
        System.out.println("✅ Movie and all associated shows/tickets deleted.");
    }

    private static void displayRevenueAnalytics() {
        System.out.println("\n============= MULTIPLEX ANALYTICS =============");
        Map<Movie, List<Show>> showsByMovie = shows.stream().collect(Collectors.groupingBy(Show::getMovie));
        double platformTotal = 0;

        for (Map.Entry<Movie, List<Show>> entry : showsByMovie.entrySet()) {
            Movie movie = entry.getKey();
            List<Show> movieShows = entry.getValue();
            System.out.println("\n🎞️ MOVIE: " + movie.getTitle().toUpperCase());

            double movieSubtotal = 0;
            for (Show show : movieShows) {
                long soldSeats = show.getSeats().stream().filter(Seat::isBooked).count();
                double showRevenue = show.getTotalRevenueGenerated();
                movieSubtotal += showRevenue;
                System.out.printf("  [%s] %-10s | %s | Seats Sold: %02d/%d | Rev: Rs. %.2f\n",
                        show.getShowId(), show.getScreenName(), show.getTime(), soldSeats, show.getSeats().size(), showRevenue);
            }
            System.out.printf("  => Subtotal for %s: Rs. %.2f\n", movie.getTitle(), movieSubtotal);
            platformTotal += movieSubtotal;
        }
        System.out.println("\n=============================================");
        System.out.printf("GRAND PLATFORM REVENUE: Rs. %.2f\n", platformTotal);
        System.out.println("=============================================");
    }

    // --- SEED DATA (DYNAMIC TRENDING VS CLASSICS) ---
    private static void seedInitialData() {
        Movie m1 = new Movie("M01", "The Dark Knight", "Action/Classic");
        Movie m2 = new Movie("M02", "Inception", "Sci-Fi/Classic");
        Movie m3 = new Movie("M03", "Interstellar", "Sci-Fi/Classic");
        Movie m4 = new Movie("M04", "Dune: Part Two", "Sci-Fi/Trending");
        Movie m5 = new Movie("M05", "Avatar: The Way of Water", "Fantasy/Trending");
        Movie m6 = new Movie("M06", "Spider-Man: Across the Spider-Verse", "Animation/Popular");
        Movie m7 = new Movie("M07", "Oppenheimer", "Biography/Trending");
        Movie m8 = new Movie("M08", "John Wick: Chapter 4", "Action/Popular");

        movies.addAll(List.of(m1, m2, m3, m4, m5, m6, m7, m8));

        int showCount = 101;

        // --- 🎬 TRENDING BLOCKBUSTERS (Heavy Rotation: 5-6 Shows) ---
        // Dune: Part Two monopolizes Screens 1 and 2
        shows.add(new Show("SH" + (showCount++), m4, "Screen 1", "09:00 AM"));
        shows.add(new Show("SH" + (showCount++), m4, "Screen 2", "11:30 AM"));
        shows.add(new Show("SH" + (showCount++), m4, "Screen 1", "02:30 PM"));
        shows.add(new Show("SH" + (showCount++), m4, "Screen 3", "05:00 PM"));
        shows.add(new Show("SH" + (showCount++), m4, "Screen 1", "08:00 PM"));
        shows.add(new Show("SH" + (showCount++), m4, "Screen 2", "10:30 PM"));

        // Oppenheimer heavily booked on IMAX-style screens
        shows.add(new Show("SH" + (showCount++), m7, "Screen 5", "09:30 AM"));
        shows.add(new Show("SH" + (showCount++), m7, "Screen 5", "01:00 PM"));
        shows.add(new Show("SH" + (showCount++), m7, "Screen 4", "04:30 PM"));
        shows.add(new Show("SH" + (showCount++), m7, "Screen 5", "06:45 PM"));
        shows.add(new Show("SH" + (showCount++), m7, "Screen 6", "10:00 PM"));

        // Avatar: The Way of Water dominates Screen 3 and 4
        shows.add(new Show("SH" + (showCount++), m5, "Screen 3", "10:00 AM"));
        shows.add(new Show("SH" + (showCount++), m5, "Screen 4", "02:00 PM"));
        shows.add(new Show("SH" + (showCount++), m5, "Screen 3", "08:30 PM"));
        shows.add(new Show("SH" + (showCount++), m5, "Screen 7", "11:00 PM"));

        // --- 🍿 POPULAR MOVIES (Standard Rotation: 3 Shows) ---
        // John Wick: Chapter 4
        shows.add(new Show("SH" + (showCount++), m8, "Screen 6", "11:00 AM"));
        shows.add(new Show("SH" + (showCount++), m8, "Screen 6", "04:00 PM"));
        shows.add(new Show("SH" + (showCount++), m8, "Screen 4", "07:30 PM"));

        // Spider-Man
        shows.add(new Show("SH" + (showCount++), m6, "Screen 7", "09:00 AM"));
        shows.add(new Show("SH" + (showCount++), m6, "Screen 7", "01:30 PM"));
        shows.add(new Show("SH" + (showCount++), m6, "Screen 2", "08:15 PM"));

        // --- 🎥 CLASSIC RE-RELEASES (Limited Rotation: 1-2 Shows) ---
        // The Dark Knight
        shows.add(new Show("SH" + (showCount++), m1, "Screen 8", "10:30 AM"));
        shows.add(new Show("SH" + (showCount++), m1, "Screen 8", "06:00 PM"));

        // Inception (Matinee only)
        shows.add(new Show("SH" + (showCount++), m2, "Screen 8", "02:00 PM"));

        // Interstellar (Late Night only)
        shows.add(new Show("SH" + (showCount++), m3, "Screen 8", "09:30 PM"));
    }
}