create database IF NOT EXISTS AcmePlexDB;

-- Use the Database
USE AcmePlexDB;

-- Users Table
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    card_number VARCHAR(16),
    card_exp_date VARCHAR(5),
    card_cvv VARCHAR(3),
    credit_balance DECIMAL(10, 2) DEFAULT 0.00,
    is_registered BOOLEAN DEFAULT FALSE,
    annual_fee_paid BOOLEAN DEFAULT FALSE,
    last_payment_date DATE
);

-- Movies Table
CREATE TABLE IF NOT EXISTS Movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    genre VARCHAR(50),
    duration INT,
    rating DECIMAL(2, 1),
    poster_path VARCHAR(255),
    description TEXT
);

-- Screens Table
CREATE TABLE IF NOT EXISTS Screens (
    screen_id INT AUTO_INCREMENT PRIMARY KEY,
    screen_cols INT NOT NULL
);

-- Showtimes Table
CREATE TABLE IF NOT EXISTS Showtimes (
    showtime_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    screen_id INT NOT NULL,
    screening DATETIME NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (screen_id) REFERENCES Screens(screen_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Seats (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,  
    screen_id INT NOT NULL,                   
    seat_pos CHAR(1) NOT NULL,                
    seat_number INT NOT NULL,                 
    is_reserved BOOLEAN NOT NULL,             
    reserved_by INT,                          
    FOREIGN KEY (screen_id) REFERENCES Screens(screen_id) ON DELETE CASCADE
);

-- Tickets Table
CREATE TABLE IF NOT EXISTS Tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    showtime_id INT NOT NULL,
    seat_row CHAR(1) NOT NULL,
    seat_col INT NOT NULL,
    FOREIGN KEY (showtime_id) REFERENCES Showtimes(showtime_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    UNIQUE (showtime_id, seat_row, seat_col) -- Ensure no duplicate tickets
);

-- Payments Table
CREATE TABLE IF NOT EXISTS Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount DECIMAL(8, 2) NOT NULL,
    payment_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    method VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);