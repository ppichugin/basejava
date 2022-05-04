package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationsSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.model.WebLink;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
        Map<String, String[]> parametersMap = request.getParameterMap();
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
            AbstractSection section = null;
            if (sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE) {
                String size = parametersMap.get(sectionType.name() + "size")[0];
                int quantityOfOrganizations = Integer.parseInt(Objects.equals(size, "") ? "0" : size);
                List<Organization> organizations = new ArrayList<>();
                for (int i = 0; i <= quantityOfOrganizations; i++) {
                    String organization = parametersMap.get(sectionType.name() + i + "org")[0].trim();
                    boolean isOrganizationCleared = Objects.equals(organization, "");
                    if (isOrganizationCleared) {
                        //if organization is empty then skip iteration in order to remove it
                        continue;
                    }
                    List<Organization.Position> positions = new ArrayList<>();
                    int quantityOfPositions = Integer.parseInt(parametersMap.get(sectionType.name() + i + "org_positionSize")[0]);
                    String url = parametersMap.get(sectionType.name() + i + "url")[0];
                    for (int j = 1; j <= quantityOfPositions; j++) {
                        String startDate = parametersMap.get(sectionType.name() + i + "position" + j + "startDate")[0];
                        boolean isPositionCleared = Objects.equals(startDate, "");
                        if (isPositionCleared && quantityOfPositions > 1) {
                            continue;
                        } else if (isPositionCleared && quantityOfPositions <= 1) {
                            // if this is the last position then do delete whole organization from List
                            organizations.remove(i);
                            positions.clear();
                            break;
                        }
                        String endDate = parametersMap.get(sectionType.name() + i + "position" + j + "endDate")[0];
                        String position = parametersMap.get(sectionType.name() + i + "position" + j + "position")[0].trim();
                        String description = null;
                        if (sectionType == SectionType.EXPERIENCE) {
                            description = parametersMap.get(sectionType.name() + i + "position" + j + "description")[0].trim();
                        }
                        if (Objects.equals(endDate, "")) {
                            positions.add(new Organization.Position(
                                    parseDate(startDate).getYear(),
                                    parseDate(startDate).getMonth(),
                                    position, description));
                        } else {
                            positions.add(new Organization.Position(
                                    parseDate(startDate).getYear(),
                                    parseDate(startDate).getMonth(),
                                    parseDate(endDate).getYear(),
                                    parseDate(endDate).getMonth(),
                                    position, description));
                        }
                    }
                    // If this organization already exists in the List then new positions to be added to this organization
                    // applies for EDUCATION section type since it can handle multiple positions as per model
                    // this is how a new position can be added in the web form
                    boolean isOrgExist = false;
                    if (sectionType == SectionType.EDUCATION) {
                        for (Organization value : organizations) {
                            isOrgExist = Objects.equals(value.getSite().getName(), organization);
                            if (isOrgExist) {
                                List<Organization.Position> existingPositions = value.getPositions();
                                existingPositions.addAll(positions);
                                break;
                            }
                        }
                    }
                    if (!isOrgExist) {
                        organizations.add(new Organization(new WebLink(organization, url), copyList(positions)));
                    }
                }
                if (organizations.size() > 0) {
                    section = new OrganizationsSection(copyList(organizations));
                    r.addSection(sectionType, section);
                }
                continue;
            }
            String sectionValue = request.getParameter(sectionType.name());
            if (sectionValue != null && sectionValue.trim().length() != 0) {
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

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        return LocalDate.parse(dateStr, formatter);
    }

    private <T> List<T> copyList(List<T> source) {
        List<T> dest = new ArrayList<T>();
        for (T item : source) {
            dest.add(item);
        }
        return dest;
    }
}