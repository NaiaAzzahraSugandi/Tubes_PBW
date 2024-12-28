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