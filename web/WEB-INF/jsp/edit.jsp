<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
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
            <h3>ФИО:</h3>
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
        <c:forEach var="sections" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sections.name() == SectionType.OBJECTIVE || sections.name() == SectionType.PERSONAL ||
                sections.name() == SectionType.ACHIEVEMENT || sections.name() == SectionType.QUALIFICATIONS}">
                    <div class="field-label">
                        <div class="title">${sections.title}</div>
                        <textarea class="field" name=${sections.name()} cols="65"
                                  rows="6">${resume.getSection(sections)}</textarea>
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