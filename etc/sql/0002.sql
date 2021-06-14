
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
-- Grants
GRANT ALL ON contact TO sc_admin;
-- GRANT ALL ON contact_id_seq TO sc_admin;
-- Alter statements
ALTER TABLE contact ADD CONSTRAINT pk_contact PRIMARY KEY (id);
ALTER TABLE contact ADD CONSTRAINT uq_contact_uuid UNIQUE (uuid);
ALTER TABLE contact ADD CONSTRAINT uq_contact_email UNIQUE (email);


CREATE TABLE IF NOT EXISTS channel(

    -- PRIMARY KEY
    id SERIAL,

    uuid varchar(64) NOT NULL,
    name VARCHAR(32) NOT NULL,
    configuration text NOT NULL, -- FIXME: Can be a JSON object -> Will need special handling in Java

    -- Book Keeping
    created TIMESTAMP WITH TIME ZONE DEFAULT now(),
    modified TIMESTAMP WITH TIME ZONE DEFAULT now(),
    deleted TIMESTAMP WITH TIME ZONE
);
-- Grants
GRANT ALL ON channel TO sc_admin;
-- Alter statements
ALTER TABLE channel ADD CONSTRAINT pk_channel PRIMARY KEY (id);
ALTER TABLE channel ADD CONSTRAINT uq_channel_name UNIQUE (name);
ALTER TABLE channel ADD CONSTRAINT uq_channel_uuid UNIQUE (uuid);


CREATE TABLE IF NOT EXISTS message(

    -- PRIMARY KEY
    id SERIAL,

    uuid varchar(64) NOT NULL,

    -- FOREIGN KEYS
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    channel_id INT NOT NULL,

    body varchar(1024),
    subject varchar(32),

    delivery_status VARCHAR(16),

    -- TS when the message was sent
    created TIMESTAMP WITH TIME ZONE DEFAULT now(),
    -- TS when message status was updated
    modified TIMESTAMP WITH TIME ZONE DEFAULT now(),
    deleted TIMESTAMP WITH TIME ZONE
);
-- Grants
GRANT ALL ON message TO sc_admin;
-- Alter statements
ALTER TABLE message ADD CONSTRAINT pk_message PRIMARY KEY (id);
ALTER TABLE message ADD CONSTRAINT uq_message_uuid UNIQUE (uuid);
ALTER TABLE message ADD CONSTRAINT fk_message_sender
    FOREIGN KEY (sender_id) REFERENCES contact (id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE message ADD CONSTRAINT fk_message_receiver
    FOREIGN KEY (receiver_id) REFERENCES contact (id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE message ADD CONSTRAINT fk_message_channel
    FOREIGN KEY (channel_id) REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE CASCADE;

commit;