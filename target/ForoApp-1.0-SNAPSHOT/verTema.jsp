<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.DTO.Tema" %>
<%@ page import="modelo.DTO.Respuesta" %>
<%@ page import="modelo.DTO.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Locale" %>

<%
    Tema tema = (Tema) request.getAttribute("tema");
    List<Respuesta> respuestas = (List<Respuesta>) request.getAttribute("listaRespuestas");

    SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
    sdf.setTimeZone(TimeZone.getTimeZone("America/Guayaquil"));

    String fechaTema = (tema != null && tema.getFechaPublicacion() != null)
        ? sdf.format(tema.getFechaPublicacion())
        : "";
%>

<%@ include file="includes/header.jsp" %>

<main class="container my-5">

    <!-- Mostrar tema -->
    <div class="bg-dark text-light p-4 rounded shadow-sm">
        <h3><%= tema.getTitulo() %></h3>
        <p><%= tema.getContenido() %></p>
        <small class="text-light">
            Iniciado por <strong><%= tema.getNombreUsuario() %></strong> el <%= fechaTema %>
        </small>
    </div>

    <hr class="my-4" />

    <!-- Mostrar respuestas -->
    <h4 class="text-light">Respuestas (<%= respuestas != null ? respuestas.size() : 0 %>)</h4>

    <div class="list-group mb-4">
        <% if (respuestas != null && !respuestas.isEmpty()) {
            for (Respuesta r : respuestas) {
                String fechaRespuesta = (r.getFechaRespuesta() != null)
                    ? sdf.format(r.getFechaRespuesta())
                    : "";
                boolean esAutor = usuario != null && usuario.getId() == r.getIdUsuario();
        %>

        <div class="list-group-item bg-secondary text-light rounded mb-3" id="respuesta-<%= r.getId() %>">
            <div>
                <div class="d-flex justify-content-between align-items-start">
                    <div class="d-flex align-items-start">
                        <i class="bi bi-person-circle fs-2 me-3"></i>
                        <div>
                            <!-- Texto de la respuesta -->
                            <p class="contenido-respuesta" id="contenido-texto-<%= r.getId() %>"><%= r.getContenido() %></p>

                            <!-- Formulario oculto para editar -->
                            <% if (esAutor) { %>
                            <form class="form-editar-respuesta" id="form-editar-<%= r.getId() %>" method="post" action="${pageContext.request.contextPath}/EditarRespuestaServlet" style="display:none;">
                                <input type="hidden" name="id" value="<%= r.getId() %>">
                                <input type="hidden" name="idTema" value="<%= tema.getId() %>">
                                <textarea name="contenido" class="form-control mb-2" rows="4" required><%= r.getContenido() %></textarea>
                                <button type="submit" class="btn btn-sm btn-success me-2">Guardar</button>
                                <button type="button" class="btn btn-sm btn-secondary btn-cancelar" data-id="<%= r.getId() %>">Cancelar</button>
                            </form>
                            <% } %>

                            <small class="text-muted">
                                Respondido por <strong><%= r.getNombreUsuario() %></strong> el <%= fechaRespuesta %>
                            </small>
                        </div>
                    </div>

                    <% if (esAutor) { %>
                    <div class="ms-3 text-end">
                        <button type="button" class="btn btn-sm btn-outline-light me-1 btn-editar" data-id="<%= r.getId() %>">
                            <i class="bi bi-pencil-square"></i> Editar
                        </button>
                        <a href="${pageContext.request.contextPath}/EliminarRespuestaServlet?id=<%= r.getId() %>&idTema=<%= tema.getId() %>" 
                           class="btn btn-sm btn-outline-light"
                           onclick="return confirm('¿Estás seguro de eliminar esta respuesta?');">
                            <i class="bi bi-trash"></i> Eliminar
                        </a>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>

        <%  }
           } else { %>
            <p class="text-light">No hay respuestas aún.</p>
        <% } %>
    </div>

    <!-- Formulario para nueva respuesta -->
    <% if (usuario != null) { %>
        <button id="btnMostrarFormulario" class="btn btn-info mb-3">Responder</button>

        <form id="formRespuesta"
              action="${pageContext.request.contextPath}/PublicarRespuestaServlet"
              method="post"
              style="display:none;">
            <input type="hidden" name="idTema" value="<%= tema.getId() %>">
            <div class="mb-3">
                <label for="contenidoRespuesta" class="form-label text-light">Tu respuesta</label>
                <textarea class="form-control" id="contenidoRespuesta" name="contenido" rows="5" required></textarea>
            </div>
            <button type="submit" class="btn btn-success">Enviar respuesta</button>
        </form>
    <% } else { %>
        <p class="text-light">Debes <a href="login.jsp">iniciar sesión</a> para responder.</p>
    <% } %>

</main>

<script>
    // Mostrar/Ocultar formulario nueva respuesta
    document.getElementById('btnMostrarFormulario')?.addEventListener('click', function () {
        const form = document.getElementById('formRespuesta');
        if (form.style.display === 'none') {
            form.style.display = 'block';
            this.textContent = 'Cancelar';
        } else {
            form.style.display = 'none';
            this.textContent = 'Responder';
        }
    });

    // Botones Editar inline
    document.querySelectorAll('.btn-editar').forEach(button => {
        button.addEventListener('click', () => {
            const id = button.getAttribute('data-id');
            document.getElementById('contenido-texto-' + id).style.display = 'none';
            document.getElementById('form-editar-' + id).style.display = 'block';
            button.style.display = 'none'; // Oculta botón Editar
        });
    });

    // Botones Cancelar edición
    document.querySelectorAll('.btn-cancelar').forEach(button => {
        button.addEventListener('click', () => {
            const id = button.getAttribute('data-id');
            document.getElementById('contenido-texto-' + id).style.display = 'block';
            document.getElementById('form-editar-' + id).style.display = 'none';
            const editarBtn = document.querySelector(`.btn-editar[data-id='${id}']`);
            if (editarBtn) editarBtn.style.display = 'inline-block';
        });
    });
</script>

<%@ include file="includes/footer.jsp" %>
