package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;

import java.util.UUID;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = createResume(UUID.randomUUID().toString(), "Grigoriy Kislin");

        // CONTACTS
        System.out.println(testResume.getFullName().toUpperCase() + "\n");
        for (int i = 0; i < ContactType.values().length; i++) {
            if (testResume.getContact(ContactType.values()[i]) == null) continue;
            System.out.println(ContactType.values()[i].getTitle() + ": "
                    + testResume.getContact(ContactType.values()[i]));
        }
        System.out.println("----------------------------------------------------------------------");
        // SECTIONS
        for (int i = 0; i < SectionType.values().length; i++) {
            System.out.println(SectionType.values()[i].getTitle().toUpperCase() + "\n"
                    + testResume.getSection(SectionType.values()[i]) + "\n");
        }
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
//        resume.addContact(MOBILE, "+7(921) 855-0482");
//        resume.addContact(SKYPE, "grigory.kislin");
//        resume.addContact(EMAIL, "gkislin@yandex.ru");
//        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
//        resume.addContact(GITHUB, "https://github.com/gkislin");
//        resume.addContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
//        resume.addContact(WEB, "http://gkislin.ru/");
//
//        resume.addSection(OBJECTIVE, new TextSection("Ведущий стажировок " +
//                "и корпоративного обучения по Java Web и Enterprise технологиям."));
//        resume.addSection(PERSONAL, new TextSection("Аналитический склад ума, " +
//                "сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
//
//        resume.addSection(ACHIEVEMENT, new ListSection(
//                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven."
//                , "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike."
//                , "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM."));
//        resume.addSection(QUALIFICATIONS, new ListSection(
//                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"
//                , "Version control: Subversion, Git, Mercury, ClearCase, Perforce"
//                , "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"));
//
//        resume.addSection(EXPERIENCE, new OrganizationsSection(
//                new Organization("Java Online Projects", "http://javaops.ru/"
//                        , new Organization.Position(2013, Month.OCTOBER
//                        , "Автор проекта"
//                        , "Создание, организация и проведение Java онлайн проектов и стажировок.")),
//                new Organization("Wrike", "https://www.wrike.com/"
//                        , new Organization.Position(2014, Month.OCTOBER, 2016, Month.JANUARY
//                        , "Старший разработчик (backend)"
//                        , "Проектирование и разработка онлайн платформы управления проектами Wrike " +
//                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).")),
//                new Organization("RIT Center", null,
//                        new Organization.Position(2012, Month.APRIL, 2014, Month.OCTOBER
//                                , "Java архитектор"
//                                , "Организация процесса разработки системы ERP для разных окружений: " +
//                                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
//                                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO"))));
//
//        resume.addSection(EDUCATION, new OrganizationsSection(
//                new Organization("Coursera", "https://www.coursera.org/course/progfun"
//                        , new Organization.Position(2013, Month.MARCH, 2013, Month.MAY
//                        , "'Functional Programming Principles in Scala' by Martin Odersky", null)),
//                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"
//                        , new Organization.Position(2011, Month.MARCH, 2011, Month.APRIL
//                        , "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null)),
//                new Organization("Siemens AG", "http://www.siemens.ru/"
//                        , new Organization.Position(2005, Month.JANUARY, 2005, Month.APRIL
//                        , "3 месяца обучения мобильным IN сетям (Берлин)", null)),
//                new Organization("Санкт-Петербургский национальный исследовательский университет", "http://www.ifmo.ru/"
//                        , new Organization.Position(1993, Month.OCTOBER, 1996, Month.SEPTEMBER
//                        , "Аспирантура (программист С, С++)", null)
//                        , new Organization.Position(1987, Month.OCTOBER, 1993, Month.SEPTEMBER
//                        , "Инженер (программист Fortran, C)", null)),
//                new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/"
//                        , new Organization.Position(1984, Month.SEPTEMBER, 1987, Month.JUNE
//                        , "Закончил с отличием", null))));

        return resume;
    }
}