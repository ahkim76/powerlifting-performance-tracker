CREATE TABLE IF NOT EXISTS users (
id INTEGER PRIMARY KEY AUTOINCREMENT,
username TEXT NOT NULL UNIQUE,
password TEXT NOT NULL,
squat REAL NOT NULL,
bench REAL NOT NULL,
deadlift REAL NOT NULL,
bodyweight REAL NOT NULL,
gender INTEGER NOT NULL DEFAULT 1  -- 1 for true/male, 0 for false/female
);

INSERT INTO users (username, password) VALUES ('alex_kim', '1234567890');
INSERT INTO users (username, password) VALUES ('monkeyBoy69', 'poopy123');


