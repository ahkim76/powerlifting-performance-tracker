DROP TABLE IF EXISTS users;

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

INSERT OR IGNORE INTO users (username, password, squat, bench, deadlift, bodyweight, gender) VALUES ('alex_kim', '1234567890', 100.0, 200.0, 300.0, 159.0, 1);
INSERT OR IGNORE INTO users (username, password, squat, bench, deadlift, bodyweight, gender) VALUES ('monkeyBoy69', 'poopy123', 1.0, 1.0, 1.0, 1.0, 0);
