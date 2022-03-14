package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationsSection;
import com.urise.webapp.model.Period;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume testResume = createResume("UUID1", "Grigoriy Kislin");

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
        Resume testResume = new Resume(uuid, fullName);
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
                , List.of(
                new Period(DateUtil.of(2013, Month.OCTOBER)
                        , LocalDate.now()
                        , "Автор проекта"
                        , "Создание, организация и проведение Java онлайн проектов и стажировок."))));
        organizations.add(new Organization("Wrike"
                , "https://www.wrike.com/"
                , (List.of(
                new Period(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY)
                        , "Старший разработчик (backend)"
                        , "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).")))));
        organizations.add(new Organization("RIT Center"
                , null
                , (List.of(
                new Period(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER)
                        , "Java архитектор"
                        , "Организация процесса разработки системы ERP для разных окружений: " +
                        "релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                        "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO")))));
        testResume.setSection(EXPERIENCE, new OrganizationsSection(organizations));

        List<Organization> educations = new ArrayList<>();
        educations.add(new Organization("Coursera", "https://www.coursera.org/course/progfun"
                , (List.of(
                new Period(DateUtil.of(2013, Month.MARCH)
                        , DateUtil.of(2013, Month.MAY)
                        , "\"Functional Programming Principles in Scala\" by Martin Odersky"
                        , null)))));
        educations.add(new Organization("Luxoft"
                , "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"
                , (List.of(
                new Period(DateUtil.of(2011, Month.MARCH)
                        , DateUtil.of(2011, Month.APRIL)
                        , "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
                        , null)))));
        educations.add(new Organization("Siemens AG"
                , "http://www.siemens.ru/"
                , (List.of(
                new Period(DateUtil.of(2005, Month.JANUARY)
                        , DateUtil.of(2005, Month.APRIL)
                        , "3 месяца обучения мобильным IN сетям (Берлин)"
                        , null)))));
        educations.add(new Organization("Санкт-Петербургский национальный исследовательский университет"
                , "http://www.ifmo.ru/"
                , (List.of(
                new Period(DateUtil.of(1993, Month.OCTOBER)
                        , DateUtil.of(1996, Month.SEPTEMBER)
                        , "Аспирантура (программист С, С++)", null),
                new Period(DateUtil.of(1987, Month.OCTOBER)
                        , DateUtil.of(1993, Month.SEPTEMBER)
                        , "Инженер (программист Fortran, C)", null)))));

        educations.add(new Organization("Заочная физико-техническая школа при МФТИ"
                , "http://www.school.mipt.ru/"
                , (List.of(
                new Period(DateUtil.of(1984, Month.SEPTEMBER)
                        , DateUtil.of(1987, Month.JUNE)
                        , "Закончил с отличием"
                        , null)))));

        testResume.setSection(EDUCATION, new OrganizationsSection(educations));
        return testResume;
    }
}