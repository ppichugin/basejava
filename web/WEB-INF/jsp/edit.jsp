<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.OrganizationsSection" %>
<%@ page import="com.urise.webapp.model.Organization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <script>
        function toggleButton() {
            var fullName = document.getElementById('fullName');
            var length = fullName.value.trim().length;
            if (length === 0) {
                document.getElementById('submitButton').disabled = true;
                alert("поле ФИО не может быть пустым или только из пробелов");
                fullName.classList.add("warning");
                fullName.focus();
                setTimeout(function () {
                    fullName.classList.remove("warning");
                }, 3000);
            } else {
                document.getElementById('submitButton').disabled = false;
            }
        }
    </script>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <h2>ФИО:</h2>
            <dd><input required type="text" name="fullName" id="fullName" size=50 value="${fn:trim(resume.fullName)}"
                       title="Введите полное имя" onchange="toggleButton()"></dd>
        </dl>
        <h3>Контакты:</h3>
        <div class="contacts">
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size=30 placeholder="${type.title}"
                               value="${resume.getContact(type)}"></dd>
                </dl>
            </c:forEach>
        </div>
        <h3>Секции:</h3>
        <c:forEach var="section" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${section.name() == SectionType.OBJECTIVE || section.name() == SectionType.PERSONAL ||
                section.name() == SectionType.ACHIEVEMENT || section.name() == SectionType.QUALIFICATIONS}">
                    <div class="field-label">
                        <div class="title">${section.title}</div>
                        <textarea class="field" name=${section.name()} cols="65"
                                  rows="6">${resume.getSection(section)}</textarea>
                    </div>
                </c:when>

                <c:when test="${section.name() == SectionType.EXPERIENCE || section.name() == SectionType.EDUCATION}">
                    <div class="field-label">
                        <div class="title">${section.title}</div>
                        <div>
                            <input name="${section.name()}0org" placeholder="Организация" type="text" size="58">
                            <input name="${section.name()}0org_positionSize" hidden value="1">
                            <input name="${section.name()}0url" placeholder="url" type="url"><br>
                            <input name="${section.name()}0position1startDate" type="date">
                            <input name="${section.name()}0position1endDate" type="date">
                            <input name="${section.name()}0position1position" placeholder="Позиция" type="text"
                                   size="40"><br>
                            <c:if test="${section.name() == SectionType.EXPERIENCE}">
                                <input name="${section.name()}0position1description" placeholder="Описание" type="text"
                                       size="84"><br>
                            </c:if>
                        </div>
                        <br>
                        <hr style="width:98%">

                        <c:set var="orgSection" value="${resume.getSection(section)}"/>
                        <jsp:useBean id="orgSection" class="com.urise.webapp.model.OrganizationsSection"
                                     scope="request"/>
                        <input type="hidden" name="${section.name()}size" value="${orgSection.organizations.size()}">
                        <c:forEach var="organization" items="${orgSection.organizations}" varStatus="orgCounter">
                            <c:set var="orgId" value="${orgCounter.count}"/>
                            <input type="hidden" name="${section.name()}${orgId}org_positionSize"
                                   value="${organization.positions.size()}">
                            <div class="period-position-input">
                                <div class="site-name-url">
                                    <div>
                                        <strong>Организация:</strong>
                                        <textarea class="org-name" cols="36" rows="1"
                                                  name="${section.name()}${orgId}org">${organization.site.name}</textarea>
                                    </div>
                                    <div>
                                        <strong>web:</strong>
                                        <input type="url" name="${section.name()}${orgId}url"
                                               value="${organization.site.url}">
                                    </div>
                                </div>

                                <c:forEach var="position" items="${organization.positions}" varStatus="positionCounter">
                                    <c:set var="posId" value="${positionCounter.count}"/>
                                    <div class="dates-title-input">
                                        <div>
                                            <strong>Начало:</strong>
                                            <input type="date"
                                                   name="${section.name()}${orgId}position${posId}startDate"
                                                   value="${position.startDate}">
                                        </div>
                                        <div>
                                            <strong>Окончание:</strong>
                                            <c:set var="endDate" value="${position.endDate}"/>
                                            <c:choose>
                                                <c:when test="${endDate=='3000-01-01'}">
                                                    <c:set var="endDate" value=""/>
                                                </c:when>
                                            </c:choose>

                                            <input type="date"
                                                   name="${section.name()}${orgId}position${posId}endDate"
                                                   value="${endDate}">
                                        </div>
                                        <div>
                                            <strong>Позиция:</strong>
                                            <input type="text"
                                                   name="${section.name()}${orgId}position${posId}position"
                                                   size="41"
                                                   value="${position.title}">
                                        </div>
                                    </div>
                                    <c:if test="${section.name() == SectionType.EXPERIENCE}">
                                        <div class="description-input">
                                            <strong>Описание:</strong>
                                            <textarea cols="79" rows="3"
                                                      name="${section.name()}${orgId}position${posId}description">${position.description}</textarea>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <hr style="width:98%">
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
        <br>
        <button type="submit" id="submitButton">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>