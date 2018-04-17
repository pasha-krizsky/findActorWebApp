/* triger for applying sequence "app_user_seq" to table "app_user" */
CREATE OR REPLACE TRIGGER app_user_id_trg
BEFORE INSERT ON app_user
FOR EACH ROW
BEGIN
IF :NEW.id IS NULL THEN
SELECT app_user_seq.NEXTVAL
INTO :new.id FROM dual;
END IF;
END;

/* triger for applying sequence "user_profile_seq" to table "user_profile" */
CREATE OR REPLACE TRIGGER user_profile_id_trg
BEFORE INSERT ON user_profile
FOR EACH ROW
BEGIN
IF :NEW.id IS NULL THEN
SELECT user_profile_seq.NEXTVAL
INTO :new.id FROM dual;
END IF;
END;

/* triger for applying sequence "worksheet_seq" to table "worksheet" */
CREATE OR REPLACE TRIGGER worksheet_id_trg
BEFORE INSERT ON worksheet
FOR EACH ROW
BEGIN
IF :NEW.id IS NULL THEN
SELECT worksheet_seq.NEXTVAL
INTO :new.id FROM dual;
END IF;
END;