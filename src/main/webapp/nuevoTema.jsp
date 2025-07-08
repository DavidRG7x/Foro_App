<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.DTO.Usuario" %>

<%
    
    Object obj = session.getAttribute("usuarioLogueado");
    if (obj == null) {
        response.sendRedirect("login.jsp?mensaje=Debe iniciar sesión para publicar un tema");
        return;
    }
%>

<%@ include file="includes/header.jsp" %>

<main class="container my-5">
    <div class="bg-dark text-light p-4 rounded shadow-sm">
        <h3 class="mb-4 text-info"><i class="bi bi-pencil-square me-2"></i>Crear nuevo tema</h3>
        
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>


        <form action="PublicarTemaServlet" method="post">
            <div class="mb-3">
                <label for="titulo" class="form-label">Asunto</label>
                <input type="text" class="form-control" id="titulo" name="titulo" required>
            </div>

            <div class="mb-3">
                <label for="contenido" class="form-label">Mensaje</label>
                <textarea class="form-control" id="contenido" name="contenido" rows="8" required></textarea>
            </div>

            <div class="d-flex justify-content-end gap-2">
                <button type="submit" class="btn btn-info">
                    <i class="bi bi-send-fill me-1"></i> Publicar
                </button>
                <button type="reset" class="btn btn-secondary">
                    <i class="bi bi-x-circle me-1"></i> Limpiar
                </button>
            </div>
        </form>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
