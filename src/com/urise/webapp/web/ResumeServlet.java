package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean isResumeNew = false;
        try {
            r = sqlStorage.get(uuid);
        } catch (NotExistStorageException e) {
            r = new Resume(uuid, fullName);
            isResumeNew = true;
        }
        r.setFullName(fullName.trim());
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            if (sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE) {
                continue;
            }
            String sectionValue = request.getParameter(sectionType.name());
            if (sectionValue != null && sectionValue.trim().length() != 0) {
                AbstractSection section = null;
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> section = new TextSection(sectionValue.trim());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        String[] listStr = sectionValue.trim().split("\n");
                        section = new ListSection(Arrays.stream(listStr)
                                .filter(c -> c.trim().length() > 0)
                                .collect(Collectors.toList()));
                    }
                }
                r.addSection(sectionType, section);
            } else {
                r.getSections().remove(sectionType);
            }
        }
        if (isResumeNew) {
            sqlStorage.save(r);
        } else {
            sqlStorage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> r = sqlStorage.get(uuid);
            case "add" -> r = new Resume("");
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}