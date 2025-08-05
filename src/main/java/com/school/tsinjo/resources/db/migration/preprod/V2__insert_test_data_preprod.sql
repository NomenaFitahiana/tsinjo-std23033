INSERT INTO donation (donor_email, donor_name, payment_id, amount, date, payment_method, payment_status)
VALUES ('jane.doe@example.com', 'Jane Doe', 'MP250804.0904.A01637', 50.00, '2025-08-05 11:00:00', 'PayPal', 'VERIFYING');

INSERT INTO aide (beneficiary_email, beneficiary_name, payment_id, amount, date, accident_description, payment_status)
VALUES ('john.smith@example.com', 'John Smith', 'MP250804.1224.B31974', 200.00, '2025-08-05 12:00:00', 'Accident de travail', 'SUCCEEDED');