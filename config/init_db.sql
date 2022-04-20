CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);


CREATE TABLE section
(
    id          serial
        CONSTRAINT section_pk
            PRIMARY KEY,
    resume_uuid text NOT NULL
        CONSTRAINT section_resume_uuid_fkey
            REFERENCES resume
            ON DELETE CASCADE,
    type        text NOT NULL,
    value       text NOT NULL
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid DESC, type ASC);