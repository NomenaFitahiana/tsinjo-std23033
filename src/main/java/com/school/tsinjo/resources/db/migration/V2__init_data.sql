INSERT INTO donation (donor_email, donor_name, payment_id, amount, date, payment_method, payment_status)
VALUES ('test1@example.com', 'Test Donateur', 'MP250804.0904.A01637', 100.0, CURRENT_TIMESTAMP, 'Carte', 'SUCCEEDED');

INSERT INTO aid (beneficiary_email, beneficiary_name, payment_id, amount, date, accident_description)
VALUES ('benef1@example.com', 'Bénéficiaire Test', 'MP250804.1224.B31974', 200.0, CURRENT_TIMESTAMP, 'Urgence médicale');