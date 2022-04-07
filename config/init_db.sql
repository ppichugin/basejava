CREATE TABLE resume
(
    uuid      char(36) NOT NULL
        CONSTRAINT resume_pk PRIMARY KEY,
    full_name text     NOT NULL

);

ALTER TABLE resume OWNER TO postgres;

CREATE TABLE contact
(
    id          serial
        CONSTRAINT contact_pk PRIMARY KEY,
    type        text     NOT NULL,
    value       text     NOT NULL,
    resume_uuid char(36) NOT NULL
        CONSTRAINT contact_resume_uuid_fk REFERENCES resume ON DELETE CASCADE
);

CREATE UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);

ALTER TABLE contact OWNER TO postgres;
