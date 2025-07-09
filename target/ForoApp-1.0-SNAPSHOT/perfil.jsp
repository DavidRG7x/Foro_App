<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<%@ page import="modelo.DTO.Usuario" %>

<%@ include file="includes/header.jsp" %>
<%
   
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer numTemas = (Integer) request.getAttribute("numTemas");
    Integer numMensajes = (Integer) request.getAttribute("numMensajes");
    Date fechaRegistro = (Date) request.getAttribute("fechaRegistro");

    SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    String fechaFormateada = (fechaRegistro != null) ? sdf.format(fechaRegistro) : "Desconocida";
%>


<main class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">

            <div class="card bg-dark text-light shadow-sm border-0">
                <div class="card-header bg-secondary text-light text-center">
                    <h2 class="mb-0">Perfil de <strong><%= usuario.getUsuario() %></strong></h2>
                </div>
                <div class="card-body">

                    <div class="d-flex align-items-center mb-4">
                        <i class="bi bi-person-circle display-1 text-info me-4"></i>
                        <div>
                            <h4 class="mb-1 text-white"><%= usuario.getNombre() %> <%= usuario.getApellido() %></h4>
                            <p class="mb-0" style="color: #b0c4de;"><i class="bi bi-envelope"></i> <%= usuario.getCorreo() %></p>
                            <small style="color: #a9a9a9;"><i class="bi bi-calendar-event"></i> Registrado el <%= fechaFormateada %></small>
                        </div>
                    </div>

                    <div class="row text-center">
                        <div class="col-6 mb-3">
                            <div class="p-4 rounded" style="background-color: rgba(13, 110, 253, 0.2);">
                                <h5 class="text-info fw-bold">Temas creados</h5>
                                <p class="display-5 mb-0 text-info"><%= numTemas != null ? numTemas : 0 %></p>
                            </div>
                        </div>
                        <div class="col-6 mb-3">
                            <div class="p-4 rounded" style="background-color: rgba(25, 135, 84, 0.2);">
                                <h5 class="text-success fw-bold">Mensajes publicados</h5>
                                <p class="display-5 mb-0 text-success"><%= numMensajes != null ? numMensajes : 0 %></p>
                            </div>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <a href="${pageContext.request.contextPath}/foro" class="btn btn-outline-info me-2">
                            <i class="bi bi-arrow-left-circle"></i> Volver al Foro
                        </a>
                        <a href="${pageContext.request.contextPath}/logout.jsp" class="btn btn-outline-light">
                            <i class="bi bi-box-arrow-right"></i> Cerrar sesión
                        </a>
                    </div>

                </div>
            </div>

        </div>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
