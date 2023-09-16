CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name text,
    text text,
    link text UNIQUE,
    created TIMESTAMP
);


