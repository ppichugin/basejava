INSERT INTO resume (uuid, full_name)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
       ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2'),
       ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'Name3'),
       ('7ee99a92-b510-4746-b79f-842a78171cfa', 'Petr Pichugin');

INSERT INTO contact (resume_uuid, type, value)
VALUES ('7ee99a92-b510-4746-b79f-842a78171cfa', 'PHONE', '+77772555062'),
       ('7ee99a92-b510-4746-b79f-842a78171cfa', 'EMAIL', 'petr.pichugin@gmail.com'),
       ('7ee99a92-b510-4746-b79f-842a78171cfa', 'WEB', 'https://pichugin.kz');

INSERT INTO section (resume_uuid, type, value)
VALUES ('7ee99a92-b510-4746-b79f-842a78171cfa', 'OBJECTIVE',
        '{"CLASSNAME":"com.urise.webapp.model.TextSection","INSTANCE":{"topic":"Java / Kotlin developer"}}');
