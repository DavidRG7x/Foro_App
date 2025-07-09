<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.invalidate(); // Cerrar sesión
    response.sendRedirect("foro"); // Redirigir al foro público
%>
