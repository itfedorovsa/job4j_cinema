CREATE TABLE IF NOT EXISTS users (
  u_id SERIAL PRIMARY KEY,
  u_name VARCHAR NOT NULL,
  u_email VARCHAR NOT NULL UNIQUE,
  u_phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sessions (
  s_id SERIAL PRIMARY KEY,
  s_name text,
  s_year int,
  s_description text
);

CREATE TABLE IF NOT EXISTS tickets (
ticket_id SERIAL PRIMARY KEY,
session_id INT NOT NULL REFERENCES sessions(s_id),
seat_id INT NOT NULL,
user_id INT NOT NULL REFERENCES users(u_id)
);

INSERT INTO sessions(s_name, s_year, s_description)
VALUES ('The Fast And The Furious 1', 2001, 'Let''s race!'),
('The Fast And The Furious 2', 2003, 'Let''s race again!'),
('The Fast And The Furious 3', 2006, 'And again!')
