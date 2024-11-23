USE AcmePlexDB;
SET SQL_SAFE_UPDATES = 0;

-- Clear Existing Data with WHERE Clause for Safe Update Mode
DELETE FROM Tickets WHERE ticket_id IS NOT NULL;
DELETE FROM Seats WHERE seat_id IS NOT NULL;
DELETE FROM Showtimes WHERE showtime_id IS NOT NULL;
DELETE FROM Movies WHERE movie_id IS NOT NULL;
DELETE FROM Users WHERE user_id IS NOT NULL;

-- Reset Auto-Increment Counters
ALTER TABLE Tickets AUTO_INCREMENT = 1;
ALTER TABLE Seats AUTO_INCREMENT = 1;
ALTER TABLE Showtimes AUTO_INCREMENT = 1;
ALTER TABLE Movies AUTO_INCREMENT = 1;
ALTER TABLE Users AUTO_INCREMENT = 1;

-- Insert Users
INSERT INTO Users (name, email, card_number, is_registered, account_recharge, credit_balance)
VALUES
('John Doe', 'john.doe@example.com', '1111222233334444', TRUE, '2025-12-31', 3.45),
('Jane Smith', 'jane.smith@example.com', NULL, FALSE, NULL, 0.00);

-- Insert Movies
INSERT INTO Movies (title, genre, duration, rating, poster_path, description)
VALUES
('Inception', 'Sci-Fi', 148, 8.8, 'frontend/images/Venom.jpg', "A thief who steals corporate secrets must plant an idea in a target\'s mind."),
('The Dark Knight', 'Action', 152, 9.0, 'frontend/images/Venom.jpg', 'Batman faces the Joker, a criminal mastermind, in Gotham City.'),
('Interstellar', 'Sci-Fi', 169, 8.6, 'frontend/images/Venom.jpg', 'A team of explorers travels through a wormhole in space to save humanity.');

-- Insert Showtimes
INSERT INTO Showtimes (movie_id, start_time, end_time)
VALUES
(1, '2024-11-21 18:00:00', '2024-11-21 20:00:00'),
(2, '2024-11-21 19:00:00', '2024-11-21 20:35:00');

-- Insert Seats
INSERT INTO Seats (showtime_id, seat_pos, number, is_reserved, reserved_by)
VALUES
(1, 'A', 1, FALSE, NULL),
(1, 'A', 2, TRUE, 1),
(2, 'B', 1, FALSE, NULL);

-- Insert Tickets
INSERT INTO Tickets (user_id, seat_id, payment_status, cancelled, refund_amount)
VALUES
(1, 2, 'Paid', FALSE, NULL);
SET SQL_SAFE_UPDATES = 1;
