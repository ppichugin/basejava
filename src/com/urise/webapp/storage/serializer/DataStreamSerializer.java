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
                        dos.writeUTF(site.getUrl());
                        writeWithException(organization.getPositions(), dos, position -> {
                            writeDateByParts(dos, position.getStartDate());
                            writeDateByParts(dos, position.getEndDate());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
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
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        AbstractSection section;
        switch (sectionType) {
            case OBJECTIVE, PERSONAL -> section = new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> section = new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE, EDUCATION -> section = new OrganizationsSection(
                    readList(dis, () -> new Organization(new WebLink(dis.readUTF(), dis.readUTF()),
                            readList(dis, () -> new Organization.Position(readDateByParts(dis), readDateByParts(dis), dis.readUTF(), dis.readUTF())))));
            default -> throw new IllegalStateException("Unexpected value: " + sectionType);
        }
        return section;
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
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
    }

    private LocalDate readDateByParts(DataInputStream dis) throws IOException {
        int year = dis.readInt();
        int month = dis.readInt();
        return LocalDate.of(year, month, 1);
    }

    private interface MyWriter<T> {
        void write(T t) throws IOException;
    }

    private interface MyReader<T> {
        void read() throws IOException;
    }

    private interface ElementReader<T> {
        T read() throws IOException;
    }
}