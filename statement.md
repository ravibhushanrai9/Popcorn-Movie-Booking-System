# Problem Statement & Scope

## Problem Statement
In many regions, multiplex cinemas still rely heavily on manual, counter-based ticket booking systems or disjointed platforms. This leads to several significant pain points for both the management and the customers:
*   **Customer Friction**: Long queues at the ticket counter and snack bar, especially during peak hours, weekends, and blockbuster releases.
*   **Lack of Transparency**: Customers cannot preview seat availability or pricing tiers before arriving at the cinema, often resulting in wasted trips if shows are sold out.
*   **Inefficient Add-on Sales**: Snack purchasing is typically disconnected from ticket booking, requiring customers to queue twice.
*   **Manual Calculation Errors**: Applying dynamic promotional discounts, calculating regional taxes (like GST), and handling cancellations manually is prone to human error.
*   **Poor Administrative Insight**: Cinema administrators lack centralized, real-time analytics to track revenue, occupancy rates, and show performance, making it difficult to optimize screen scheduling dynamically.

## Scope of the Project
The **Multiplex Movie Booking System** is designed to address these gaps by providing a unified, self-service digital platform. 

The scope includes:
*   **Role-Based Access Control**: Separate, secure portals for Users (customers) and Admins (cinema managers).
*   **End-to-End Booking Lifecycle**: From browsing movies and selecting specific tiered seats to ordering snacks and generating tax-inclusive invoices.
*   **Dynamic Pricing & Promotions**: A flexible promo code engine supporting both permanent global discounts and single-use loyalty rewards (e.g., bulk booking discounts).
*   **Administrative Management**: Tools for admins to dynamically add/remove movies and schedule shows on different screens.
*   **Real-Time Analytics**: An automated dashboard providing per-show and per-movie revenue and occupancy tracking.
*   **In-Memory Architecture**: For the scope of this version, the system operates entirely in-memory using advanced Java data structures, ensuring portability without requiring external database setup.

## Target Users
1.  **Moviegoers / Customers**: Individuals looking for a fast, transparent way to browse schedules, choose their preferred seats, order snacks in advance, and maintain a digital wallet of their tickets.
2.  **Cinema Administrators / Managers**: Staff members responsible for updating the movie catalogue, scheduling showtimes across multiple screens, and analyzing daily revenue and occupancy metrics to make business decisions.

## High-Level Features
*   **Visual Seat Mapping**: An intuitive, ASCII-based visual representation of seat layouts categorized by pricing tiers (VIP, Premium, Executive, Normal).
*   **Integrated Snack Bar**: A seamless add-on module for food and beverage pre-ordering during the ticket workflow.
*   **Robust Billing Engine**: Automated calculation of base fares, snack totals, promotional discounts, GST (18%), and convenience fees.
*   **Ticket Wallet Management**: Capability for users to track active bookings and process self-service cancellations with automated seat release and refund calculations.
*   **Analytics Dashboard**: Aggregated financial and occupancy reporting grouped by movie and showtime.
*   **Cascading Data Integrity**: Robust entity relationship management (e.g., deleting a movie automatically drops all its shows and revokes related tickets).
