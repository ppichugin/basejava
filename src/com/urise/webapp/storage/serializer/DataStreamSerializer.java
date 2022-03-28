package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationsSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.model.WebLink;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializerStrategy {
    /**
     * DataStreamSerializer based on DataOutputStream/DataInputStream.
     * (ConcreteStrategy for Pattern Strategy)
     */

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            //CONTACTS
            writeWithException(r.getContacts().entrySet(), dos, contactTypeStringEntry -> {
                dos.writeUTF(contactTypeStringEntry.getKey().name());
                dos.writeUTF(contactTypeStringEntry.getValue());
            });

            //SECTIONS
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                final SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        String topic = ((TextSection) entry.getValue()).getTopic();
                        dos.writeUTF(topic);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        final List<String> blocks = ((ListSection) sections.get(sectionType)).getBlocks();
                        dos.writeInt(blocks.size());
                        for (String block : blocks) {
                            dos.writeUTF(block);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        final List<Organization> organizationList = ((OrganizationsSection) sections.get(sectionType))
                                .getOrganizations();
                        final int quantityOfOrganizations = organizationList.size();
                        dos.writeInt(quantityOfOrganizations);

                        for (Organization organization : organizationList) {
                            WebLink site = organization.getSite();
                            dos.writeUTF(site.getName());
                            dos.writeUTF(site.getUrl() == null ? "" : site.getUrl());

                            int quantityOfPositions = organization.getPositions().size();
                            dos.writeInt(quantityOfPositions);
                            for (int j = 0; j < quantityOfPositions; j++) {
                                List<Organization.Position> positions = organization.getPositions();
                                Organization.Position position = positions.get(j);
                                writeDateByParts(dos, position.getStartDate());
                                writeDateByParts(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription() == null ? "" : position.getDescription());
                            }
                        }
                    }
                }
            }
        }
    }

    private <T> void writeWithException(Collection<T> contacts, DataOutputStream dos,
                                        MyWriter<T> myWriter) throws IOException {
        dos.writeInt(contacts.size());
        for (T contact : contacts) {
            myWriter.write(contact);
        }
    }

    private void writeDateByParts(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getDayOfMonth());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            // CONTACTS
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // SECTIONS
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                AbstractSection section = null;
                switch (type) {
                    case OBJECTIVE, PERSONAL -> section = new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int quantityOfBlocks = dis.readInt();
                        List<String> listSection = new ArrayList<>();
                        for (int j = 0; j < quantityOfBlocks; j++) {
                            listSection.add(dis.readUTF());
                        }
                        section = new ListSection(listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        final int quantityOfOrganizations = dis.readInt();
                        List<Organization> organizations = new ArrayList<>(quantityOfOrganizations);
                        for (int j = 0; j < quantityOfOrganizations; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            WebLink site = new WebLink(name, url.equals("") ? null : url);
                            int quantityOfPositions = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>(quantityOfPositions);
                            for (int k = 0; k < quantityOfPositions; k++) {
                                LocalDate startDate = readDateByParts(dis);
                                LocalDate endDate = readDateByParts(dis);
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                positions.add(new Organization.Position(startDate, endDate, title, description.equals("") ? null : description));
                            }
                            organizations.add(new Organization(site, positions));
                        }
                        section = new OrganizationsSection(organizations);
                    }
                }
                resume.addSection(type, section);
            }
            return resume;
        }
    }

    private LocalDate readDateByParts(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        int month = dis.readInt();
        int day = dis.readInt();
        return LocalDate.of(year, month, day);
    }

    private interface MyWriter<T> {
        void write(T t) throws IOException;
    }
}