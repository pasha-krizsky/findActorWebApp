/* table to store rememberme related stuff */
CREATE TABLE persistent_logins (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) NOT NULL,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL,
  CONSTRAINT persistent_logins_pk PRIMARY KEY (series)
);

/* table for all roles of users */
CREATE TABLE user_profile (
  id   NUMBER      NOT NULL,
  type VARCHAR(30) NOT NULL,
  CONSTRAINT user_profile_pk PRIMARY KEY (id),
  CONSTRAINT constraint_type UNIQUE (type)
);

/* table for all users */
CREATE TABLE app_user (
  id         NUMBER       NOT NULL,
  sso_id     VARCHAR(30)  NOT NULL,
  password   VARCHAR(100) NOT NULL,
  first_name VARCHAR(30)  NOT NULL,
  last_name  VARCHAR(30)  NOT NULL,
  email      VARCHAR(30)  NOT NULL,
  CONSTRAINT user_pk PRIMARY KEY (id),
  CONSTRAINT constraint_sso_id UNIQUE (sso_id)
);

CREATE TABLE worksheet (
  id             NUMBER       NOT NULL,
  user_id        NUMBER,/*NOT NULL!!!!!!!!!!*/
  submissionDate TIMESTAMP    NOT NULL,
  age            NUMBER       NOT NULL,
  height         BINARY_FLOAT NOT NULL,
  weight         BINARY_FLOAT NOT NULL,
  sex            CHAR(20)     NOT NULL,
  nationality    CHAR(20)     NOT NULL,
  eye_color      CHAR(20)     NOT NULL,
  skin_color     CHAR(20)     NOT NULL,
  hair_color     CHAR(20)     NOT NULL,
  experience     CHAR(100)    NOT NULL,
  reason         CHAR(100)    NOT NULL,
  /* R - Review, C - Casting, O - Offer, D - Declined Default - R */
  status         CHAR(1) DEFAULT ON NULL 'R',
  CONSTRAINT worksheet_pk PRIMARY KEY (id),
  CONSTRAINT worksheet_fk FOREIGN KEY (user_id) REFERENCES app_user (id)
);

/* join table for many-to-many relationship */
CREATE TABLE app_user_user_profile (
  user_id         NUMBER NOT NULL,
  user_profile_id NUMBER NOT NULL,
  CONSTRAINT app_user_user_profile_pk PRIMARY KEY (user_id, user_profile_id),
  CONSTRAINT app_user_table_fk FOREIGN KEY (user_id) REFERENCES app_user (id),
  CONSTRAINT user_profile_table_fk FOREIGN KEY (user_profile_id) REFERENCES user_profile (id)
);