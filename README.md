<div align="center">
  <h1>🎬 Multiplex Movie Booking System</h1>
  <p><strong>A robust, console-based Java application that simulates the experience of booking movie tickets in a modern multiplex.</strong></p>
  
  <p>
    <img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java" alt="Java 17+" />
    <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge" alt="Architecture MVC" />
    <img src="https://img.shields.io/badge/Interface-CLI-darkgreen?style=for-the-badge" alt="CLI" />
  </p>
</div>

<br />

## 📖 Overview
The **Multiplex Movie Booking System** provides two distinct portals: 
* 🧑‍💻 **User Portal**: Browse movies, select seats via a visual seat map, order snacks, and book tickets.
* 👨‍💼 **Admin Portal**: Manage the movie catalogue, schedule shows, and view real-time revenue analytics.

Built without any external dependencies, this system utilizes core Java principles (OOP, Collections, Streams) to deliver a seamless and complete ticket-booking lifecycle entirely in memory.

---

## ✨ Features

### 🍿 User Portal
* 🎥 **Browse Movies & Shows**: View currently playing movies and their scheduled timings across different screens.
* 💺 **Visual Seat Selection**: Interactive tiered seating layout (*VIP, Premium, Executive, Normal*) with real-time availability tracking.
* 🍔 **Snack Bar**: Order add-on snacks (Popcorn, Nachos, Beverages) during the booking process.
* 🎟️ **Promo Codes**: Apply permanent discount codes (e.g., `WELCOME10`) or earn single-use bulk reward codes for booking 5+ seats.
* 🧾 **Detailed Billing**: Automatic calculation of seat totals, snack totals, discounts, 18% GST, and convenience fees.
* 💳 **Ticket Wallet & Cancellation**: View active tickets in your wallet or cancel them for a full refund (automatically freeing up seats and revoking used promo codes).

### ⚙️ Admin Portal
* 📊 **Revenue Analytics**: View per-movie, per-show ticket sales, revenue generation, and grand platform totals.
* 🎬 **Catalogue Management**: Dynamically add new movies or schedule new shows on specific screens.
* 🗑️ **Cascade Deletion**: Removing a movie automatically deletes its scheduled shows and revokes all booked tickets for those shows.

---

## 🛠️ Technologies & Architecture

* **Language**: Java 17 (JDK 17+)
* **Core Concepts**: Object-Oriented Programming (OOP), Collections Framework (List, Set, Map), Enums, Lambda Expressions, Stream API, UUID generation.
* **Architecture**: Layered design with distinct Separation of Concerns (*Presentation, Business Logic, and Data*).
* **Storage**: In-memory collections (`HashMap`, `ArrayList`) ensuring zero-dependency, ultra-fast execution.

---

## 🚀 Quick Start

### Prerequisites
Ensure you have Java (JDK 17 or higher) installed on your system. 
```bash
java -version
```

### Installation & Execution
1. **Navigate to the source directory**:
   ```bash
   cd MovieBookingSystem/src
   ```
2. **Compile the code**:
   ```bash
   javac MultiplexBookingSystem.java
   ```
3. **Run the application**:
   ```bash
   java MultiplexBookingSystem
   ```

---

## 🧪 Testing Instructions

You can manually test the system using the following flows:

1. **User Booking Flow**:
   * Select `1` to Login as User ➡️ `1` to Book Tickets.
   * Choose a Movie ID (e.g., `M04` for Dune) and a Show ID.
   * Select seats from the visual map (e.g., `A1 A2` for VIP seats).
   * Add snacks, apply the promo code `WELCOME10`, and verify the final tax-inclusive invoice.
2. **Ticket Cancellation**:
   * From the User Dashboard, select `3` to Cancel a Ticket.
   * Enter your Ticket ID and verify the refund and seat release.
3. **Admin Analytics**:
   * Select `2` to Login as Admin ➡️ `1` to View Analytics.
   * Verify that the revenue accurately reflects the tickets you booked or cancelled.

---

## 📁 Project Structure

```text
MovieBookingSystem/
├── src/
│   └── MultiplexBookingSystem.java      # Main source code file
├── Multiplex_Movie_Booking_System_Report_v4.docx  # Detailed project report
├── statement.md                         # Problem statement and scope
├── .gitignore                           # Git ignore rules
└── README.md                            # Project documentation
```

---

## 🔮 Future Enhancements (v2.0 Scope)
While the current version is robust and complete for in-memory execution, planned future upgrades include:
*   **Database Integration**: Migrating data storage from in-memory Collections to a relational database (e.g., MySQL or PostgreSQL) for data persistence.
*   **Graphical User Interface (GUI)**: Building a desktop interface using JavaFX or a web frontend using Spring Boot.
*   **Payment Gateway Mock**: Simulating external payment gateway integrations (e.g., Stripe or Razorpay) during checkout.
*   **Email Notifications**: Sending automated ticket confirmation and cancellation emails to users.

---

## 📸 Screenshots & Documentation
Comprehensive, high-resolution screenshots of the terminal interface (Main Menu, Movie Catalogue, Visual Seat Map, Snack Bar, Detailed Invoice, and Admin Analytics) along with UML and Architecture diagrams are available in the included **Project Report (`.docx`)** file.

<br />
<div align="center">
  <i>Developed as a demonstration of clean software design and core Java concepts.</i>
</div>
