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
            writeWithException(r.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            //SECTIONS
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(sections.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE, PERSONAL -> dos.writeUTF(((TextSection) entry.getValue()).getTopic());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeWithException(((ListSection) sections.get(entry.getKey()))
                            .getBlocks(), dos, dos::writeUTF);
                    case EXPERIENCE, EDUCATION -> writeWithException(((OrganizationsSection) sections.get(entry.getKey()))
                            .getOrganizations(), dos, organization -> {
                        WebLink site = organization.getSite();
                        dos.writeUTF(site.getName());
                        dos.writeUTF(site.getUrl() == null ? "" : site.getUrl());
                        writeWithException(organization.getPositions(), dos, position -> {
                            writeDateByParts(dos, position.getStartDate());
                            writeDateByParts(dos, position.getEndDate());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription() == null ? "" : position.getDescription());
                        });
                    });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            // CONTACTS
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            // SECTIONS
            readWithException(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                AbstractSection section = null;
                switch (type) {
                    case OBJECTIVE, PERSONAL -> section = new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> listSection = new ArrayList<>();
                        readWithException(dis, () -> listSection.add(dis.readUTF()));
                        section = new ListSection(listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(dis, () -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            WebLink site = new WebLink(name, url.equals("") ? null : url);
                            List<Organization.Position> positions = new ArrayList<>();
                            readWithException(dis, () -> {
                                LocalDate startDate = readDateByParts(dis);
                                LocalDate endDate = readDateByParts(dis);
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                positions.add(new Organization.Position(startDate, endDate, title, description.equals("") ? null : description));
                            });
                            organizations.add(new Organization(site, positions));
                        });
                        section = new OrganizationsSection(organizations);
                    }
                }
                resume.addSection(type, section);
            });
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, MyWriter<T> myWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            myWriter.write(t);
        }
    }

    private <T> void readWithException(DataInputStream dis, MyReader<T> myReader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            myReader.read();
        }
    }

    private void writeDateByParts(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
        dos.writeInt(date.getDayOfMonth());
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

    private interface MyReader<T> {
        void read() throws IOException;
    }
}