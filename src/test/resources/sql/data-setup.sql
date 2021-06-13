begin;

DELETE FROM contact;

INSERT INTO contact(uuid, name, email, telegram_handle, twitter_id, phone_number) VALUES
('test_uuid_1', 'test_name_1', 'test_name_1@test-email.com', 'test_name_1', '@testName1', '+919988776655'),
('test_uuid_2', 'test_name_2', 'test_name_2@test-email.com', 'test_name_2', '@testName2', '+919889776655'),
('test_uuid_3', 'test_name_3', 'test_name_3@test-email.com', 'test_name_3', '@testName3', '+919987786655'),
('test_uuid_4', 'test_name_4', 'test_name_4@test-email.com', 'test_name_4', '@testName4', '+919988766755'),
('test_uuid_5', 'test_name_5', 'test_name_5@test-email.com', 'test_name_5', '@testName5', '+919988776556');

-- ('test_uuid_6', 'test_name_6', 'test_name_6@test-email.com', 'test_name_6', '@testName6', '+919985877656');
-- UPDATE contact SET deleted = now() WHERE uuid = 'test_uuid_6';

commit;