CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name text,
    link text UNIQUE,
    text text,
    created TIMESTAMP
);


