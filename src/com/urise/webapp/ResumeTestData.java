package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationsSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = new Resume("Grigoriy Kislin");
        testResume.setContact(MOBILE, "+7(921) 855-0482");
        testResume.setContact(SKYPE, "grigory.kislin");
        testResume.setContact(EMAIL, "gkislin@yandex.ru");
        testResume.setContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        testResume.setContact(GITHUB, "https://github.com/gkislin");
        testResume.setContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        testResume.setContact(WEB, "http://gkislin.ru/");

        testResume.setSection(OBJECTIVE, new TextSection("Ведущий стажировок " +
                "и корпоративного обучения по Java Web и Enterprise технологиям."));
        testResume.setSection(PERSONAL, new TextSection("Аналитический склад ума, " +
                "сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        testResume.setSection(ACHIEVEMENT, new ListSection(
                List.of("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven."
                        , "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike."
                        , "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.")));
        testResume.setSection(QUALIFICATIONS, new ListSection(
                List.of("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"
                        , "Version control: Subversion, Git, Mercury, ClearCase, Perforce"
                        , "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle")));

        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization("Java Online Projects"
                , "http://javaops.ru/"
                , LocalDate.of(2013, 11, 1)
                , LocalDate.now()
                , "Автор проекта"
                , "Создание, организация и проведение Java онлайн проектов и стажировок."));
        organizations.add(new Organization("Wrike"
                , "https://www.wrike.com/"
                , LocalDate.of(2014, 10, 1)
                , LocalDate.of(2016, 1, 1)
                , "Старший разработчик (backend)"
                , "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)."));
        organizations.add(new Organization("RIT Center"
                , null
                , LocalDate.of(2012, 4, 1)
                , LocalDate.of(2014, 10, 1)
                , "Java архитектор"
                , "Организация процесса разработки системы ERP для разных окружений: " +
                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO"));
        testResume.setSection(EXPERIENCE, new OrganizationsSection(organizations));

        List<Organization> educations = new ArrayList<>();
        educations.add(new Organization("Coursera", "https://www.coursera.org/course/progfun"
                , LocalDate.of(2013, 3, 1)
                , LocalDate.of(2013, 5, 1)
                , "\"Functional Programming Principles in Scala\" by Martin Odersky"
                , null));
        educations.add(new Organization("Luxoft"
                , "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"
                , LocalDate.of(2011, 3, 1)
                , LocalDate.of(2011, 4, 1)
                , "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
                , null));
        educations.add(new Organization("Siemens AG"
                , "http://www.siemens.ru/"
                , LocalDate.of(2005, 1, 1)
                , LocalDate.of(2005, 4, 1)
                , "3 месяца обучения мобильным IN сетям (Берлин)"
                , null));
        educations.add(new Organization("Заочная физико-техническая школа при МФТИ"
                , "http://www.school.mipt.ru/"
                , LocalDate.of(1984, 9, 1)
                , LocalDate.of(1987, 6, 1)
                , "Закончил с отличием"
                , null));
        testResume.setSection(EDUCATION, new OrganizationsSection(educations));

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
}
