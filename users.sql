CREATE TABLE IF NOT EXISTS users (
id INTEGER PRIMARY KEY,
username TEXT NOT NULL UNIQUE,
password TEXT NOT NULL);

INSERT INTO users (username, password) VALUES ('alex_kim', 'hyunbi101');
INSERT INTO users (username, password) VALUES ('monkeyBoy69', 'poopy123');
