package com.urise.webapp.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {
    private final String uuid;
    private final String fullname;

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
        this.fullname = "";
    }

    public Resume(String uuid, String fullname) {
        this.uuid = uuid;
        this.fullname = fullname;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullname() {
        return fullname;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Resume resume = (Resume) o;
//        return uuid.equals(resume.uuid);
//    }
//
//    @Override
//    public int hashCode() {
//        return uuid.hashCode();
//    }

    // re-assigned due to additional field 'fullname' added
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        if (!uuid.equals(resume.uuid)) return false;
        return fullname.equals(resume.fullname);
    }

    // re-assigned for proper work of HashMap, due to additional field 'fullname' added
    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullname.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
