CREATE TABLE donation (
    id SERIAL PRIMARY KEY,
    donor_email VARCHAR(255),
    donor_name VARCHAR(255),
    payment_id VARCHAR(50),
    amount DECIMAL(10,2),
    date TIMESTAMP,
    payment_method VARCHAR(50),
    payment_status VARCHAR(50)
);

CREATE TABLE aide (
    id SERIAL PRIMARY KEY,
    beneficiary_email VARCHAR(255),
    beneficiary_name VARCHAR(255),
    payment_id VARCHAR(50),
    amount DECIMAL(10,2),
    date TIMESTAMP,
    accident_description TEXT
);