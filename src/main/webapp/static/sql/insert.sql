/* insert first user sam */
INSERT INTO app_user (
  sso_id,
  password,
  first_name,
  last_name,
  email)
VALUES (
  'pasha',
  '$2a$10$0c6nX6AhWCucM1dJ2mhBvu5cSAjdRc7Rwjcp5AuOyf2VCawKc8UdW',
  'pasha',
  'pasha',
  'pasha@gmail.com'
);

/* Populate user_profile table */
INSERT INTO user_profile (type) VALUES ('USER');
INSERT INTO user_profile (type) VALUES ('ADMIN');
INSERT INTO user_profile (type) VALUES ('AGENT');
INSERT INTO user_profile (type) VALUES ('DIRECTOR');

/* Populate JOIN Table */
INSERT INTO app_user_user_profile (user_id, user_profile_id)
  SELECT
    app_user.id,
    user_profile.id
  FROM app_user, user_profile
  WHERE app_user.sso_id = 'pasha' AND user_profile.type = 'ADMIN';
