-- DELETE FROM documents;
-- DELETE FROM document_types;

-- INSERT INTO document_types (name, fields) VALUES
-- ('CIN', '[{"name":"nom","type":"string"},{"name":"prenom","type":"string"},{"name":"date_naissance","type":"date"},{"name":"lieu_naissance","type":"string"},{"name":"numero_cin","type":"string"}]'),
-- ('Passport', '[{"name":"passport_number","type":"string"},{"name":"full_name","type":"string"},{"name":"nationality","type":"string"},{"name":"date_of_birth","type":"date"},{"name":"expiry_date","type":"date"}]'),
-- ('Invoice', '[{"name":"invoice_number","type":"string"},{"name":"supplier","type":"string"},{"name":"client","type":"string"},{"name":"issue_date","type":"date"},{"name":"total_amount","type":"decimal"},{"name":"currency","type":"string"}]'),
-- ('BankStatement', '[{"name":"account_number","type":"string"},{"name":"account_holder","type":"string"},{"name":"statement_period","type":"string"},{"name":"opening_balance","type":"decimal"},{"name":"closing_balance","type":"decimal"}]');

-- INSERT INTO documents (created_at, type_id, content) VALUES
-- ('2025-01-10 09:15:00', (SELECT id FROM document_types WHERE name = 'CIN'), '{"nom":"Alaoui","prenom":"Youssef","date_naissance":"1998-04-12","lieu_naissance":"Rabat","numero_cin":"AB123456"}'),
-- ('2025-01-11 10:20:00', (SELECT id FROM document_types WHERE name = 'CIN'), '{"nom":"El Idrissi","prenom":"Salma","date_naissance":"2000-09-03","lieu_naissance":"Fes","numero_cin":"CD789012"}'),
-- ('2025-01-12 11:35:00', (SELECT id FROM document_types WHERE name = 'CIN'), '{"nom":"Benali","prenom":"Karim","date_naissance":"1995-01-27","lieu_naissance":"Casablanca","numero_cin":"EF345678"}'),
-- ('2025-01-13 14:05:00', (SELECT id FROM document_types WHERE name = 'CIN'), '{"nom":"Naciri","prenom":"Imane","date_naissance":"1999-07-19","lieu_naissance":"Marrakech","numero_cin":"GH901234"}'),

-- ('2025-01-14 09:40:00', (SELECT id FROM document_types WHERE name = 'Passport'), '{"passport_number":"PA556677","full_name":"Youssef Alaoui","nationality":"Moroccan","date_of_birth":"1998-04-12","expiry_date":"2033-04-11"}'),
-- ('2025-01-15 10:10:00', (SELECT id FROM document_types WHERE name = 'Passport'), '{"passport_number":"PA667788","full_name":"Salma El Idrissi","nationality":"Moroccan","date_of_birth":"2000-09-03","expiry_date":"2034-09-02"}'),
-- ('2025-01-16 12:25:00', (SELECT id FROM document_types WHERE name = 'Passport'), '{"passport_number":"PA778899","full_name":"Karim Benali","nationality":"Moroccan","date_of_birth":"1995-01-27","expiry_date":"2032-01-26"}'),
-- ('2025-01-17 16:45:00', (SELECT id FROM document_types WHERE name = 'Passport'), '{"passport_number":"PA889900","full_name":"Imane Naciri","nationality":"Moroccan","date_of_birth":"1999-07-19","expiry_date":"2035-07-18"}'),

-- ('2025-01-18 08:30:00', (SELECT id FROM document_types WHERE name = 'Invoice'), '{"invoice_number":"INV-2025-001","supplier":"TechSupply SARL","client":"Atlas Retail","issue_date":"2025-01-05","total_amount":"15400.00","currency":"MAD"}'),
-- ('2025-01-19 09:50:00', (SELECT id FROM document_types WHERE name = 'Invoice'), '{"invoice_number":"INV-2025-002","supplier":"OfficePro","client":"BlueWave LLC","issue_date":"2025-01-08","total_amount":"2750.50","currency":"MAD"}'),
-- ('2025-01-20 13:15:00', (SELECT id FROM document_types WHERE name = 'Invoice'), '{"invoice_number":"INV-2025-003","supplier":"NetCom Services","client":"GreenFarm Coop","issue_date":"2025-01-12","total_amount":"9800.00","currency":"MAD"}'),
-- ('2025-01-21 15:05:00', (SELECT id FROM document_types WHERE name = 'Invoice'), '{"invoice_number":"INV-2025-004","supplier":"BuildMart","client":"Urban Projects","issue_date":"2025-01-14","total_amount":"43200.75","currency":"MAD"}'),

-- ('2025-01-22 10:00:00', (SELECT id FROM document_types WHERE name = 'BankStatement'), '{"account_number":"MA120001234567890","account_holder":"Alaoui Consulting","statement_period":"2024-12","opening_balance":"125000.00","closing_balance":"132450.25"}'),
-- ('2025-01-23 10:35:00', (SELECT id FROM document_types WHERE name = 'BankStatement'), '{"account_number":"MA120009876543210","account_holder":"El Idrissi Studio","statement_period":"2024-12","opening_balance":"45200.10","closing_balance":"38990.40"}'),
-- ('2025-01-24 11:20:00', (SELECT id FROM document_types WHERE name = 'BankStatement'), '{"account_number":"MA120001111222233","account_holder":"Benali Trading","statement_period":"2024-12","opening_balance":"98500.00","closing_balance":"101240.00"}'),
-- ('2025-01-25 12:55:00', (SELECT id FROM document_types WHERE name = 'BankStatement'), '{"account_number":"MA120004444555566","account_holder":"Naciri Events","statement_period":"2024-12","opening_balance":"21000.00","closing_balance":"26780.60"}');

SELECT 1;
