INSERT INTO users (name, email, password, peran) VALUES
('John Doe', 'john.doe@example.com', 'password123', 'user'),
('Jane Smith', 'jane.smith@example.com', 'password456', 'admin'),
('Mike Johnson', 'mike.j@example.com', 'securepass', 'user'),
('Emily Davis', 'emily.d@example.com', 'mypassword', 'user'),
('Chris Brown', 'chris.b@example.com', 'topsecret', 'user'),
('Sophia Miller', 'sophia.m@example.com', 'welcome1', 'user'),
('Liam Wilson', 'liam.w@example.com', 'qwertyuiop', 'admin'),
('Olivia Martinez', 'olivia.m@example.com', 'hello123', 'user'),
('Ethan Thomas', 'ethan.t@example.com', 'letmein', 'user'),
('Ava Taylor', 'ava.t@example.com', 'runrunrun', 'admin'),
('Admin', 'admin@gmail.com', 'admin123', 'admin');

INSERT INTO races (id, name, distance, start_date_time, end_date_time, participants, status, description, image_location) 
VALUES 
(6, 'Autumn Challenge', 20.0, '2024-12-20 08:00:00', '2024-12-20 14:00:00', 1, 'Closed', 'Challenging autumn race', NULL),
(10, 'Charity Walk', 3.5, '2025-03-15 08:00:00', '2025-03-15 14:00:00', 0, 'Scheduled', 'Community charity event', NULL),
(2, 'City Sprint', 10.0, '2023-12-10 08:00:00', '2023-12-10 14:00:00', 1, 'Closed', 'Fast-paced city race', NULL),
(4, 'Night Run', 15.0, '2024-11-15 08:00:00', '2024-11-15 14:00:00', 1, 'Closed', 'Evening city lights run', NULL),
(8, 'River Run', 12.0, '2025-01-03 08:00:00', '2025-01-03 14:00:00', 1, 'Closed', 'River side run', NULL),
(1, 'Spring Marathon', 42.2, '2023-11-15 08:00:00', '2023-11-15 14:00:00', 2, 'Closed', 'Annual spring marathon', NULL),
(5, 'Summer Fun Run', 5.0, '2024-12-15 08:00:00', '2024-12-15 14:00:00', 1, 'Closed', 'Casual summer run', NULL),
(3, 'Trail Adventure', 25.5, '2023-12-20 08:00:00', '2023-12-20 14:00:00', 1, 'Closed', 'Mountain trail run', NULL),
(7, 'Winter Dash', 8.0, '2024-12-30 08:00:00', '2024-12-30 14:00:00', 1, 'Closed', 'Quick winter dash', NULL),
(9, 'Halloween Hustle', 13.0, '2025-01-13 11:00:00', '2025-01-13 17:00:00', 0, 'Scheduled', 'Spooky themed run', NULL);


-- Dummy data for activities table (running-related only)
INSERT INTO activities (id_user, title, distance, duration, date, time, description, image_location) VALUES
(1, 'Sunday Sprint', 3.00, '00:15:00', '2025-01-12', '06:00:00', 'Quick sprint to start the week', NULL),
(1, 'Tuesday Jog', 5.00, '00:30:00', '2025-01-14', '07:00:00', 'Casual jog around the neighborhood', NULL),
(1, 'Thursday Trail', 7.00, '00:45:00', '2025-01-16', '07:30:00', 'Trail run in the woods', NULL),
(1, 'Friday Run', 6.50, '00:40:00', '2025-01-17', '08:00:00', 'Morning run to end the work week', NULL),
(1, 'Saturday Long Run', 10.00, '01:10:00', '2025-01-18', '06:30:00', 'Long run to boost endurance', NULL);
(1, 'Morning Run', 5.00, '00:30:00', '2025-06-01', '06:30:00', 'Morning run for testing', NULL),
(1, 'Evening Run', 6.00, '00:35:00', '2025-07-05', '18:00:00', 'Evening run for testing', NULL),
(1, 'Night Run', 7.00, '00:45:00', '2025-09-10', '20:30:00', 'Night run for testing', NULL),
(1, 'Hill Run', 8.00, '01:00:00', '2025-11-05', '09:00:00', 'Hill run for testing', NULL),
(1, 'Long Distance Run', 10.00, '01:10:00', '2025-11-20', '07:30:00', 'Long distance run for testing', NULL),
(1, 'Park Jog', 4.50, '00:30:00', '2025-12-01', '08:00:00', 'Jog in the park for testing', NULL),
(1, 'Trail Run', 6.50, '00:40:00', '2025-12-15', '07:15:00', 'Trail run for testing', NULL),
(1, 'Beach Run', 5.50, '00:35:00', '2025-12-25', '09:30:00', 'Beach run for testing', NULL),
(1, 'Sprint Run', 4.00, '00:20:00', '2025-12-30', '06:15:00', 'Sprint run for testing', NULL),
(1, 'Spring Run', 5.00, '00:30:00', '2025-03-15', '06:30:00', 'Spring run to enjoy the weather', NULL),
(1, 'Summer Run', 6.50, '00:40:00', '2025-06-10', '18:00:00', 'Summer evening run for fitness', NULL),
(1, 'Autumn Trail', 7.00, '00:50:00', '2025-09-05', '07:30:00', 'Autumn trail running through the forest', NULL),
(1, 'Winter Jog', 5.50, '00:35:00', '2025-12-20', '08:00:00', 'Jogging in winter conditions', NULL),
(1, 'New Year Run', 6.00, '00:40:00', '2025-01-01', '07:30:00', 'New Year’s Day run to start the year', NULL),
(1, 'January Jog', 4.50, '00:30:00', '2025-01-15', '08:00:00', 'Jogging through the city park', NULL),
(1, 'Winter Sprint', 5.00, '00:25:00', '2025-02-05', '06:30:00', 'Short winter sprint training', NULL),
(1, 'Spring Trail', 8.00, '01:00:00', '2025-03-25', '07:45:00', 'Springtime trail run along the hills', NULL),
(1, 'Morning Jog', 6.50, '00:40:00', '2025-04-10', '06:15:00', 'Early morning jog for freshness', NULL),
(1, 'Mountain Run', 9.00, '01:15:00', '2025-05-03', '08:00:00', 'Running on mountain trails', NULL),
(1, 'Park Run', 4.00, '00:30:00', '2025-05-15', '06:30:00', 'Easy run through the park', NULL);

-- Insert records to ensure good coverage for weekly, monthly, and yearly charts (username John Doe)
INSERT INTO activities (id_user, title, distance, duration, date, time, description, image_location)
VALUES
(1, 'Morning Run', 5.00, '00:30:00', '2024-06-01', '06:30:00', 'Morning run for testing', NULL),
(1, 'Evening Run', 6.00, '00:35:00', '2024-07-05', '18:00:00', 'Evening run for testing', NULL),
(1, 'Night Run', 7.00, '00:45:00', '2024-09-10', '20:30:00', 'Night run for testing', NULL),
(1, 'Hill Run', 8.00, '01:00:00', '2024-11-05', '09:00:00', 'Hill run for testing', NULL),
(1, 'Long Distance Run', 10.00, '01:10:00', '2024-11-20', '07:30:00', 'Long distance run for testing', NULL),
(1, 'Park Jog', 4.50, '00:30:00', '2024-12-01', '08:00:00', 'Jog in the park for testing', NULL),
(1, 'Trail Run', 6.50, '00:40:00', '2024-12-15', '07:15:00', 'Trail run for testing', NULL),
(1, 'Beach Run', 5.50, '00:35:00', '2024-12-25', '09:30:00', 'Beach run for testing', NULL),
(1, 'Sprint Run', 4.00, '00:20:00', '2024-12-30', '06:15:00', 'Sprint run for testing', NULL);
(1, 'Spring Run', 5.00, '00:30:00', '2023-03-15', '06:30:00', 'Spring run to enjoy the weather', NULL),
(1, 'Summer Run', 6.50, '00:40:00', '2023-06-10', '18:00:00', 'Summer evening run for fitness', NULL),
(1, 'Autumn Trail', 7.00, '00:50:00', '2023-09-05', '07:30:00', 'Autumn trail running through the forest', NULL),
(1, 'Winter Jog', 5.50, '00:35:00', '2023-12-20', '08:00:00', 'Jogging in winter conditions', NULL);
(1, 'New Year Run', 6.00, '00:40:00', '2024-01-01', '07:30:00', 'New Year’s Day run to start the year', NULL),
(1, 'January Jog', 4.50, '00:30:00', '2024-01-15', '08:00:00', 'Jogging through the city park', NULL),
(1, 'Winter Sprint', 5.00, '00:25:00', '2024-02-05', '06:30:00', 'Short winter sprint training', NULL),
(1, 'Spring Trail', 8.00, '01:00:00', '2024-03-25', '07:45:00', 'Springtime trail run along the hills', NULL),
(1, 'Morning Jog', 6.50, '00:40:00', '2024-04-10', '06:15:00', 'Early morning jog for freshness', NULL),
(1, 'Mountain Run', 9.00, '01:15:00', '2024-05-03', '08:00:00', 'Running on mountain trails', NULL),
(1, 'Park Run', 4.00, '00:30:00', '2024-05-15', '06:30:00', 'Easy run through the park', NULL);

-- Insert dummy race participants (no NULL values)
INSERT INTO race_participants (id, race_id, user_id, registration_date, distance, duration, speed_km_min, image_location)
VALUES
(1, 1, 1, '2023-11-14 12:00:00', 42.2, '03:45:00', 0.1875, 'marathon1.jpg'),
(2, 1, 3, '2023-11-14 10:00:00', 42.2, '04:00:00', 0.1750, 'marathon2.jpg'),
(3, 2, 4, '2023-12-09 10:00:00', 10.0, '00:50:00', 0.2000, 'citysprint1.jpg'),
(4, 3, 5, '2023-12-11 10:00:00', 25.5, '02:10:00', 0.1960, 'trail1.jpg'),
(5, 4, 1, '2024-11-14 10:00:00', 15.0, '01:10:00', 0.2143, 'nightrun1.jpg'),
(6, 5, 2, '2024-12-14 10:00:00', 5.0, '00:25:00', 0.2000, 'summerfun1.jpg'),
(7, 6, 3, '2024-12-19 10:00:00', 20.0, '01:45:00', 0.1905, 'autumn1.jpg'),
(8, 7, 4, '2024-12-29 10:00:00', 8.0, '00:40:00', 0.2000, 'winterdash1.jpg'),
(9, 8, 5, '2025-01-02 10:00:00', 12.0, '01:00:00', 0.2000, 'river1.jpg');


INSERT INTO notifications (user_id, created_date, message) VALUES
(1, '2024-01-20 08:00:00', 'You have been removed from Spring Marathon.'),
(3, '2024-02-05 09:15:00', 'Trail Adventure registration confirmed.'),
(4, '2024-03-12 16:45:00', 'City Sprint results are available.'),
(5, '2025-06-01 10:30:00', 'Reminder: Summer Fun Run is approaching!'),
(1, '2024-12-29 14:20:00', 'Night Run registration confirmed.');
