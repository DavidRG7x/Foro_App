<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.DTO.Tema" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Locale" %>

<%
    List<Tema> lista = (List<Tema>) request.getAttribute("listaTemas");

    SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
    sdf.setTimeZone(TimeZone.getTimeZone("America/Guayaquil"));
%>

<%@ include file="includes/header.jsp" %>

<main class="container my-5">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-light">Temas recientes</h2>
        <% if (usuario != null) { %>
            <a href="nuevoTema.jsp" class="btn btn-info fw-semibold shadow-sm">
                <i class="bi bi-pencil-square me-1"></i> Nuevo Tema
            </a>
        <% } %>
    </div>

    <div class="table-responsive rounded shadow-sm">
        <table class="table table-dark table-hover align-middle">
            <thead class="table-info text-dark">
                <tr>
                    <th>Asunto / Iniciado por</th>
                    <th class="text-center">Respuestas / Vistas</th>
                    <th>Último mensaje</th>
                </tr>
            </thead>
            <tbody>
                <% if (lista != null && !lista.isEmpty()) {
                       for (Tema tema : lista) {
                           String fechaTema = (tema.getFechaPublicacion() != null)
                               ? sdf.format(tema.getFechaPublicacion())
                               : "";
                %>
                <tr>
                    <td>
                        <strong>
                            <a href="verTema?id=<%= tema.getId() %>" class="text-info text-decoration-none">
                                <%= tema.getTitulo() %>
                            </a>
                        </strong><br>
                        <small>Iniciado por <span class="text-info"><%= tema.getNombreUsuario() %></span></small>
                    </td>
                    <td class="text-center">
                        <span><%= tema.getNumRespuestas() != null ? tema.getNumRespuestas() : 0 %></span>
                        /
                        <span><%= tema.getNumVistas() != null ? tema.getNumVistas() : 0 %></span>
                    </td>
                    <td>
                        <div class="d-flex align-items-center">
                            <i class="bi bi-person-circle text-info me-2" style="font-size: 32px;"></i>                            <div>
                                <small class="text-light"><%= fechaTema %></small><br>
                                <small class="text-info"><%= tema.getNombreUsuario() %></small>
                            </div>
                        </div>
                    </td>
                </tr>
                <%   }
                   } else { %>
                <tr>
                    <td colspan="3" class="text-center text-light">No hay temas para mostrar.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

</main>

<%@ include file="includes/footer.jsp" %>
