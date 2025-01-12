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

INSERT INTO races (name, distance, start_date_time, end_date_time, participants, status, description, image_location) VALUES
('Spring Marathon', 42.20, '2025-04-15 08:00:00', '2025-04-15 14:00:00', 0, 'Scheduled', 'Annual spring marathon', NULL),
('City Sprint', 10.00, '2024-03-10 09:00:00', '2024-03-10 11:00:00', 0, 'Closed', 'Fast-paced city race', NULL),
('Trail Adventure', 25.50, '2024-06-20 07:30:00', '2024-06-20 12:00:00', 0, 'Scheduled', 'Mountain trail run', NULL),
('Night Run', 15.00, '2024-12-30 19:00:00', '2025-01-01 21:30:00', 0, 'Ongoing', 'Evening city lights run', NULL),
('Summer Fun Run', 5.00, '2025-07-10 07:00:00', '2025-07-10 08:30:00', 0, 'Scheduled', 'Casual summer run', NULL),
('Autumn Challenge', 20.00, '2024-11-01 07:00:00', '2024-11-01 11:00:00', 0, 'Closed', 'Challenging autumn race', NULL),
('Winter Dash', 8.00, '2024-12-15 09:00:00', '2024-12-15 10:30:00', 0, 'Closed', 'Quick winter dash', NULL),
('River Run', 12.00, '2025-08-05 08:00:00', '2025-08-05 10:00:00', 0, 'Scheduled', 'River side run', NULL),
('Halloween Hustle', 13.00, '2024-10-31 18:00:00', '2024-10-31 20:30:00', 0, 'Closed', 'Spooky themed run', NULL),
('Charity Walk', 3.50, '2025-09-15 10:00:00', '2025-09-15 11:30:00', 0, 'Scheduled', 'Community charity event', NULL);

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
INSERT INTO race_participants (race_id, user_id, registration_date, distance, duration, speed_km_min, image_location) VALUES
(1, 1, '2024-01-10 12:00:00', 42.20, '03:45:00', 0.1875, 'marathon1.jpg'),
(1, 3, '2024-01-12 14:30:00', 42.20, '04:00:00', 0.1750, 'marathon2.jpg'),
(2, 4, '2024-02-01 10:00:00', 10.00, '00:50:00', 0.2000, 'citysprint1.jpg'),
(3, 5, '2024-03-15 09:00:00', 25.50, '02:10:00', 0.1960, 'trail1.jpg'),
(4, 1, '2024-04-10 17:00:00', 15.00, '01:10:00', 0.2143, 'nightrun1.jpg'),
(5, 2, '2024-05-20 14:00:00', 5.00, '00:25:00', 0.2000, 'summerfun1.jpg'),
(6, 3, '2024-11-01 06:45:00', 20.00, '01:45:00', 0.1905, 'autumn1.jpg'),
(7, 4, '2024-12-01 10:30:00', 8.00, '00:40:00', 0.2000, 'winterdash1.jpg'),
(8, 5, '2025-07-25 08:00:00', 12.00, '01:00:00', 0.2000, 'river1.jpg'),
(9, 1, '2024-10-15 18:00:00', 13.00, '01:05:00', 0.2000, 'halloween1.jpg');


INSERT INTO notifications (user_id, created_date, message) VALUES
(1, '2024-01-20 08:00:00', 'You have been removed from Spring Marathon.'),
(3, '2024-02-05 09:15:00', 'Trail Adventure registration confirmed.'),
(4, '2024-03-12 16:45:00', 'City Sprint results are available.'),
(5, '2025-06-01 10:30:00', 'Reminder: Summer Fun Run is approaching!'),
(1, '2024-12-29 14:20:00', 'Night Run registration confirmed.');
