/* tables */
DROP TABLE persistent_logins;
DROP TABLE app_user_user_profile;
DROP TABLE app_user;
DROP TABLE user_profile;
DROP TABLE worksheet;

/* sequences */
DROP SEQUENCE app_user_seq;
DROP SEQUENCE user_profile_seq;
DROP SEQUENCE worksheet_seq;

/* triggers */
DROP TRIGGER app_user_id_trg;
DROP TRIGGER user_profile_id_trg;
DROP TRIGGER worksheet_id_trg;
