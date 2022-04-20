package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Storage sqlStorage = Config.get().getSqlStorage();
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<style>\n" +
                "table, th, td { border:1px solid black;\n }\n </style>");
        out.println("<head>");
        out.println("<title>Resume database</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"WHITE\">");
        out.println("<h2>RESUMES:</h2>");
        out.println("<table style=\"width:50%\">\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>FullName</th>\n" +
                "  </tr>");
        for (Resume resume : sqlStorage.getAllSorted()) {
            out.println("<tr>");
            out.print("<td>" + resume.getUuid());
            out.print("<td>" + resume.getFullName());
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}