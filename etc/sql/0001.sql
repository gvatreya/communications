
begin;

CREATE TABLE IF NOT EXISTS contact(

    id SERIAL,

    uuid varchar(64) NOT NULL,
    name varchar(64) NOT NULL,
    email varchar(64) NOT NULL,
    telegram_handle varchar(64),
    twitter_id varchar(32),
    phone_number varchar(15),

    -- Book Keeping
    created TIMESTAMP WITH TIME ZONE DEFAULT now(),
    modified TIMESTAMP WITH TIME ZONE DEFAULT now(),
    deleted TIMESTAMP WITH TIME ZONE
);

GRANT ALL ON contact TO sc_admin;
-- GRANT ALL ON contact_id_seq TO sc_admin;

ALTER TABLE contact ADD CONSTRAINT pk_user PRIMARY KEY (id);
ALTER TABLE contact ADD CONSTRAINT uq_user_uuid UNIQUE (uuid);
ALTER TABLE contact ADD CONSTRAINT uq_user_email UNIQUE (email);

commit;