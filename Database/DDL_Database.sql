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