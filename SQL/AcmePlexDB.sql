DROP DATABASE IF EXISTS AcmePlexDB;

-- Create Database
CREATE DATABASE AcmePlexDB;

-- Use the Database
USE AcmePlexDB;

-- Create Users Table
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    card_number VARCHAR(16),
    is_registered BOOLEAN DEFAULT FALSE,
    account_recharge DATE,
    credit_balance DOUBLE DEFAULT 0.00
);

-- Create Movies Table
CREATE TABLE IF NOT EXISTS Movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    genre VARCHAR(50),
    duration INT,
    rating DECIMAL(2, 1),
    poster_path VARCHAR(255),
    description TEXT
);

-- Screens Table
CREATE TABLE Screens (
    screen_id INT AUTO_INCREMENT PRIMARY KEY,
    screen_rows INT,
    screen_cols INT
);

-- Create Showtimes Table
CREATE TABLE IF NOT EXISTS Showtimes (
    showtime_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    poster_path VARCHAR(255),
    description TEXT,
    FOREIGN KEY (movie_id) REFERENCES Movies(movie_id)
);

-- Create Seats Table
CREATE TABLE IF NOT EXISTS Seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    showtime_id INT NOT NULL,
    seat_pos CHAR(10),
    number INT,
    is_reserved BOOLEAN DEFAULT FALSE,
    reserved_by INT,
    FOREIGN KEY (showtime_id) REFERENCES Showtimes(showtime_id),
    FOREIGN KEY (reserved_by) REFERENCES Users(user_id)
);

-- Create Tickets Table
CREATE TABLE IF NOT EXISTS Tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    seat_id INT NOT NULL,
    payment_status VARCHAR(50),
    cancelled BOOLEAN DEFAULT FALSE,
    refund_amount DECIMAL(8, 2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (seat_id) REFERENCES Seats(seat_id)
);
