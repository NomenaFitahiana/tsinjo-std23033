INSERT INTO donation (donor_email, donor_name, payment_id, amount, date, payment_method, payment_status)
VALUES ('john.doe@example.com', 'John Doe', 'MP250804.0908.D15807', 100.00, '2025-08-05 10:00:00', 'CB', 'SUCCEEDED');

INSERT INTO aide (beneficiary_email, beneficiary_name, payment_id, amount, date, accident_description, payment_status)
VALUES ('mary.jane@example.com', 'Mary Jane', 'MP250804.1826.D63944', 150.00, '2025-08-05 13:00:00', 'Maladie grave', 'VERIFYING');