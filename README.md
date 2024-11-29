# Movie Theatre Reservation App  
**ENSF 480 Term Project**  

**Group Number**: L01-02  

---

## Team Members

| Name           | Email                      | UCID     |
| -------------- | -------------------------- | -------- |
| Cody Casselman | cody.casselman@ucalgary.ca | 30115793 |
| Ryan Graham    | ryan.graham3@ucalgary.ca   | 30171130 |
| Keeryn Johnson | keeryn.johnson@ucalgary.ca | 30170031 |
| Evan Mann      | evan.mann@ucalgary.ca      | 30141069 |  

---

## How to Run the Program  

This application has been tested on **Windows 11**.  

### Prerequisites  
1. Ensure you have a MySQL server installed and configured.  
2. Update the `DatabaseConfig.java` file (in the `backend` folder) with your MySQL server’s details:  
   - **URL**  
   - **Username**  
   - **Password**  

### Compilation Instructions  
If `Main.class` does not exist or needs to be updated, follow these steps:  
1. Open a terminal in the project directory: `ENSF3480_TermProject`.  
2. Run the following command:  
```bash
javac -cp lib/mysql-connector-j-9.1.0.jar Main.java frontend/pages/*.java frontend/decorators/*.java frontend/observers/*.java frontend/states/*.java frontend/panels/*.java backend/*.java
```

### Running the Application  
Once the `.class` files are compiled, run the application by entering:  
```bash
java -cp ".;lib\mysql-connector-j-9.1.0.jar" Main
```  

---

## How to Use the Program  

1. **Login/Sign-Up**  
   - Users must log in or create an account to access the application.  
   - Registered Users (RUs) must pay a $20 annual membership fee upon registration.  

2. **Search for Movies**  
   - Browse available movies, showtimes, and theaters.  

3. **View Available Seats**  
   - The app displays seat availability for each showtime using a graphical interface.  
   - Registered Users can reserve seats before public release, limited to 10% of the total seats.  

4. **Select and Reserve Seats**  
   - Choose desired seats for the selected movie and showtime.  

5. **Make Payment**  
   - Complete the reservation by entering credit card details or using stored card from previous purchaces.  
   - A receipt and ticket will be faux emailed upon successful purchase.  

6. **Cancel Reservation**  
   - Users can cancel a reservation up to 72 hours before the show.  
     - **Ordinary Users** pay a 15% administrative fee.  
     - **Registered Users** do not pay administrative fees.
     - Both receive a credit valid for one year. (Up to Dec 31 each year)

7. **Receive News Updates**  
   - Registered Users receive faux movie news before the public.  

8. **Logout**  
   - Log out to end your session.  

---

## Features List  

### Core Features  
1. **User Management**  
   - Account registration for ordinary and registered users.  
   - Secure login functionality.  

2. **Movie Search and Listings**  
   - Search movies by title, theater, or showtime.  
   - View details of selected movies.  

3. **Seat Reservation**  
   - Interactive seat selection with real-time updates.  
   - Enforced seat reservation policies for Registered Users.  

4. **Payment Integration**  
   - Credit card payment for tickets and membership fees.  
   - Receipt and ticket generation upon payment.  

5. **Reservation Management**  
   - Ticket cancellation with appropriate policies for each user type.  

6. **Notification System**  
   - Early movie announcements for Registered Users.  

---

## Project Overview  

### About the Application  
The Movie Theatre Reservation App is designed for **AcmePlex**, a movie theatre company. This app supports ticket reservations for ordinary and registered users, adhering to distinct policies for each group.  

### Key Features:  
- User registration and management.  
- Integration with a MySQL database for storing user and reservation data.  
- Real-time seat availability updates.  
- Refund policies based on user type and cancellation timelines.  
- Exclusive perks for Registered Users.  

### Technologies Used  
1. **Java**: Core application logic and GUI development using Swing.  
2. **Swing**: Interactive graphical user interface.  
3. **MySQL**: Backend database for managing user and reservation data.  
4. **JDBC**: Database connection and transaction handling.  

---