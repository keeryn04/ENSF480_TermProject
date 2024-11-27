USE AcmePlexDB;
SET SQL_SAFE_UPDATES = 0;

-- Clear Existing Data
DELETE FROM Payments WHERE payment_id IS NOT NULL;
DELETE FROM Tickets WHERE ticket_id IS NOT NULL;
DELETE FROM Showtimes WHERE showtime_id IS NOT NULL;
DELETE FROM Movies WHERE movie_id IS NOT NULL;
DELETE FROM Users WHERE user_id IS NOT NULL;
DELETE FROM Tickets WHERE ticket_id IS NOT NULL;
DELETE FROM Screens WHERE screen_id IS NOT NULL;

-- Reset Auto-Increment Counters
ALTER TABLE Payments AUTO_INCREMENT = 1;
ALTER TABLE Tickets AUTO_INCREMENT = 1;
ALTER TABLE Showtimes AUTO_INCREMENT = 1;
ALTER TABLE Movies AUTO_INCREMENT = 1;
ALTER TABLE Users AUTO_INCREMENT = 1;
ALTER TABLE Screens AUTO_INCREMENT = 1;

-- Insert Users
INSERT INTO Users (name, email, password, card_number, card_exp_date, card_cvv, is_registered, credit_balance, last_payment_date)
VALUES
    ('John Doe', 'john.doe@example.com', 'hashedpassword123', '1111222233334444', '04/28', "234", TRUE, 3.45, '2025-12-31'),
    ('Jane Smith', 'jane.smith@example.com', 'hashedpassword456', NULL, NULL, NULL, FALSE, 0.00, NULL),
    ('Alice Johnson', 'alice.johnson@example.com', '1234', '5555666677778888', '09/30', "548", TRUE, 1.00, '2024-08-23'),
    ('Bob Brown', 'bob.brown@example.com', 'password', '9999888877776666', '11/16', "002", FALSE, 25.75, NULL)
ON DUPLICATE KEY UPDATE 
    name = VALUES(name),
    password = VALUES(password),
    card_number = VALUES(card_number),
    is_registered = VALUES(is_registered),
    credit_balance = VALUES(credit_balance),
    last_payment_date = VALUES(last_payment_date);

-- Insert Movies
INSERT INTO Movies (title, genre, duration, rating, poster_path, description)
VALUES
    ('Inception', 'Sci-Fi', 148, 8.8, '/images/Inception.jpg', "A thief who steals corporate secrets must plant an idea in a target\'s mind."),
    ('The Dark Knight', 'Action', 152, 9.0, '/images/DarkKnight.jpg', 'Batman faces the Joker, a criminal mastermind, in Gotham City.'),
    ('Interstellar', 'Sci-Fi', 169, 8.6, '/images/Interstellar.jpg', 'A team of explorers travels through a wormhole in space to save humanity.'),
    ('Venom', 'Action', 112, 6.7, '/images/Venom.jpg', 'A journalist gains superpowers when bonded with an alien symbiote.')
ON DUPLICATE KEY UPDATE
    genre = VALUES(genre),
    duration = VALUES(duration),
    rating = VALUES(rating),
    poster_path = VALUES(poster_path),
    description = VALUES(description);

-- Insert Screens
INSERT INTO Screens (screen_cols)
VALUES
    (2),
    (3),
    (6),
    (5);

-- Insert Showtimes
INSERT INTO Showtimes (movie_id, screen_id, screening)
VALUES
    (1, 1, '2024-11-24 18:00:00'),
    (2, 3, '2024-11-24 20:30:00'),
    (3, 2, '2024-11-25 19:00:00'),
    (4, 4, '2024-11-25 21:00:00'),
    (4, 4, '2024-11-25 16:00:00')
ON DUPLICATE KEY UPDATE
    screening = VALUES(screening);

-- Insert Tickets
INSERT INTO Tickets (user_id, showtime_id, seat_row, seat_col)
VALUES
    (1, 1, 'A', 1), -- John Doe reserves seat A1 for Inception
    (2, 1, 'A', 2), -- Jane Smith reserves seat A2 for Inception
    (3, 2, 'B', 3), -- Alice Johnson reserves seat B3 for The Dark Knight
    (4, 3, 'C', 4)  -- Bob Brown reserves seat C4 for Interstellar
ON DUPLICATE KEY UPDATE
    seat_row = VALUES(seat_row),
    seat_col = VALUES(seat_col);

-- Insert Payments
INSERT INTO Payments (user_id, amount, method)
VALUES
    (1, 20.00, 'Credit Card'),
    (2, 10.00, 'Store Credit'),
    (3, 15.00, 'PayPal'),
    (4, 25.00, 'Credit Card')
ON DUPLICATE KEY UPDATE
    amount = VALUES(amount),
    method = VALUES(method);
    
SET SQL_SAFE_UPDATES = 1;