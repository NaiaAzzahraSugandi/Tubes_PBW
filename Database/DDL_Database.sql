CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    email VARCHAR(60) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    peran VARCHAR(10) NOT NULL
);

CREATE TABLE activities (
    id SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    title VARCHAR(60) NOT NULL,
    distance INTEGER NOT NULL,
    duration INTEGER NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    description VARCHAR(150),
    image_location VARCHAR(255),
    FOREIGN KEY (id_user) REFERENCES users(id)
);

CREATE TABLE races(
	id SERIAL PRIMARY KEY,
	name VARCHAR(60) NOT NULL,
	distance DECIMAL(5,2) NOT NULL,
	start_date_time TIMESTAMP NOT NULL,
	end_date_time TIMESTAMP NOT NULL,
	participants INTEGER DEFAULT 0,
	status VARCHAR(10),
	description VARCHAR(255) NOT NULL,
	image_location VARCHAR(255)
);

CREATE TABLE race_participants (
    id SERIAL PRIMARY KEY,
    race_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
	registration_date TIMESTAMP NOT NULL,
	distance DECIMAL(5,2) NOT NULL,
	duration INTERVAL NOT NULL,
	speed_km_min DECIMAL(8,4) NOT NULL,
	image_location VARCHAR(255) NOT NULL,
    FOREIGN KEY (race_id) REFERENCES races(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (race_id, user_id)
);


-- CREATE VIEW
CREATE VIEW race_participants_view AS
	SELECT
		race_participants.id,
		race_participants.race_id,
		race_participants.user_id,
		users.name,
		race_participants.registration_date,
		race_participants.distance,
		race_participants.duration,
		race_participants.speed_km_min,
		race_participants.image_location
	FROM
		users
		JOIN race_participants ON users.id = race_participants.user_id
	ORDER BY
		speed_km_min DESC

-- FUNCTIONS
CREATE OR REPLACE FUNCTION update_participant_count()
RETURNS TRIGGER AS $$
BEGIN
    -- update isi kolom participants di kolom race
    UPDATE races
    SET participants = (SELECT COUNT(*) FROM race_participants WHERE race_id = NEW.race_id)
    WHERE id = NEW.race_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- trigger untuk insert
CREATE TRIGGER participant_insert
AFTER INSERT ON race_participants
FOR EACH ROW
EXECUTE FUNCTION update_participant_count();

-- trigger untuk delete
CREATE TRIGGER participant_delete
AFTER DELETE ON race_participants
FOR EACH ROW
EXECUTE FUNCTION update_participant_count();