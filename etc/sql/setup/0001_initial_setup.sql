create database communications;

-- FIXME: The password needs to be given separately, remove before commiting
create user sc_admin with CREATEDB PASSWORD 'SET_PASSWORD_HERE_WHEN_RUNNING_BUT_DO_NOT_COMMIT_WITH_PASSWORD';

alter user sc_admin  CREATEROLE;