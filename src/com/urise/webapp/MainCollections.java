package com.urise.webapp;

import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationsSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Johnson");

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2, "Fine");

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3, "Marty");

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4, "Julian");

    public static void main(String[] args) {
//        Collection<Resume> collection = new ArrayList<>();
//        collection.add(RESUME_1);
//        collection.add(RESUME_2);
//        collection.add(RESUME_3);
//        collection.add(RESUME_4);
//
//        for (Resume r : collection) {
//            System.out.println(r);
//            if (Objects.equals(r.getUuid(), UUID_1)) {
////                collection.remove(r);
//            }
//        }

//        Iterator<Resume> iterator = collection.iterator();
//        while (iterator.hasNext()) {
//            Resume r = iterator.next();
//            System.out.println(r);
//            if (Objects.equals(r.getUuid(), UUID_1)) {
//                iterator.remove();
//            }
//        }
//        System.out.println(collection);


        Map<Resume, Resume> map = new HashMap<>();
//        map.put(UUID_1, RESUME_1);
//        map.put(UUID_2, RESUME_2);
//        map.put(UUID_3, RESUME_3);

//        // Bad!
//        for (String uuid : map.keySet()) {
//            System.out.println(map.get(uuid));
//        }
//
//        for (Map.Entry<String, Resume> entry : map.entrySet()) {
//            System.out.println(entry.getValue());
//        }
//
        map.put(RESUME_1, RESUME_1);
        map.put(RESUME_2, RESUME_2);
        map.put(RESUME_3, RESUME_3);

        for (Map.Entry<Resume, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
        System.out.println(map.containsKey(RESUME_1));
        System.out.println(map.containsValue(RESUME_1));
        map.remove(RESUME_1);
        System.out.println(map.containsValue(RESUME_1));
        System.out.println(map.containsKey(RESUME_1));
        System.out.println(map.containsValue(UUID_1));
        System.out.println(map.containsKey(UUID_1));

        Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
        sections.put(SectionType.EDUCATION, new OrganizationsSection(
                new Organization("org", null, new Organization.Position(2020, Month.JANUARY, "aspirant", null))));

        System.out.println(sections);
        sections.put(SectionType.EDUCATION, new OrganizationsSection(
                new Organization("org2", null, new Organization.Position(2022, Month.JANUARY, "student", null))));
        System.out.println(sections);

        List<Organization> education = new ArrayList<>();
        education.add(new Organization("org", null, new Organization.Position(2020, Month.JANUARY, "aspirant", null)));
        education.add(new Organization("org2", null, new Organization.Position(2022, Month.JANUARY, "student", null)));
        sections.put(SectionType.EDUCATION, new OrganizationsSection(education));
        System.out.println(sections);

    }
}
