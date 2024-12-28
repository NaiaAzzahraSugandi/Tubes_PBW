INSERT INTO race_participants (race_id, user_id, registration_date, duration, image_location) VALUES
(2, 2, '2023-11-02 09:15:00', 5400, ''),  -- 1 hour 30 minutes
(3, 7, '2023-09-16 10:00:00', 7200, ''),  -- 2 hours
(4, 8, '2023-12-02 11:45:00', 4500, ''),  -- 1 hour 15 minutes

INSERT INTO races (name, distance, start_date_time, end_date_time, participants, status, description, image_location)
VALUES
('Lakefront Half Marathon', 21.10, '2025-07-12 06:00:00', '2025-07-12 09:30:00', 0, 'Closed', 'Half marathon along the lake', '/images/lakefront_half.jpg'),
('Charity Fun Run', 3.50, '2024-08-18 09:00:00', '2024-12-31 11:00:00', 0, 'Open', 'Charity event for local schools', '/images/charity_run.jpg'),
('Forest Ultra Marathon', 50.00, '2024-12-11 05:00:00', '2024-12-31 14:00:00', 0, 'Open', 'Extreme ultramarathon through the forest', '/images/forest_ultra.jpg'),
('Desert Dash 15K', 15.00, '2024-10-11 07:00:00', '2024-12-31 10:00:00', 0, 'Open', 'Hot and challenging desert race', '/images/desert_dash.jpg'),
('Bridge to Bridge 12K', 12.00, '2025-11-05 07:30:00', '2025-11-05 10:00:00', 0, 'Closed', 'Scenic run across famous bridges', '/images/bridge_run.jpg'),
('Winter Wonderland 7K', 7.00, '2024-12-20 09:00:00', '2024-12-31 11:00:00', 0, 'Open', 'Cold and festive winter race', '/images/winter_run.jpg');
