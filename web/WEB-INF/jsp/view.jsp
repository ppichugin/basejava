<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.urise.webapp.model.Organization" %>
<%@ page import="com.urise.webapp.model.Organization.Position" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div class="name">
        ${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a>
    </div>

    <div class="contacts">
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </div>

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <h3><%=sectionEntry.getKey().getTitle()%>
        </h3>
        <c:set var="section" value="${sectionEntry.key}"/>
        <c:choose>
            <c:when test="${section == SectionType.OBJECTIVE}">
                <strong><%=sectionEntry.getValue()%>
                </strong>
            </c:when>

            <c:when test="${section == SectionType.ACHIEVEMENT || section == SectionType.QUALIFICATIONS}">
                <c:set var="value" value="${sectionEntry.value}"/>
                <jsp:useBean id="value" type="com.urise.webapp.model.ListSection"/>
                <div class="list">
                    <ul>
                        <c:forEach var="line" items="${value.blocks}">
                            <li>${line}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>

            <c:when test="${section == SectionType.EXPERIENCE || section == SectionType.EDUCATION}">
                <c:set var="organization" value="${sectionEntry.value}"/>
                <jsp:useBean id="organization" type="com.urise.webapp.model.OrganizationsSection"/>
                <c:forEach var="line" items="${organization.organizations}">
                    <div class="org-name">
                        <c:set var="url" value="${line.site.url}"/>
                        <c:choose>
                            <c:when test="${url.blank}">
                                ${line.site.name}
                            </c:when>
                            <c:otherwise>
                                <a href="${line.site.url}">${line.site.name}</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="period-position-description">
                        <c:forEach var="position" items="${line.positions}">
                            <%@ include file="dateFormatter.jspf" %>
                            <div class="dates">${startDate} - ${endDate == '01/3000' ? 'сегодня' : endDate}</div>
                            <div class="title">${position.title}</div>
                            <c:if test="${!position.description.blank}">
                                <div class="description">${position.description}<br></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <%=sectionEntry.getValue()%><br/>
            </c:otherwise>

        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>