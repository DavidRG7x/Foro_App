<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="includes/header.jsp" %>

<main class="login-main d-flex justify-content-center align-items-center py-5">
    <div class="main-box login-box p-3">

        <%-- Mensajes de error y éxito --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger text-center py-2 mb-3" role="alert">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        <% if (request.getAttribute("exito") != null) { %>
            <div class="alert alert-success text-center py-2 mb-3" role="alert">
                <%= request.getAttribute("exito") %>
            </div>
        <% } %>

        <div class="text-center mb-2">
            <i class="bi bi-person-plus-fill" style="font-size: 2.5rem; color: #0dcaf0;"></i>
        </div>

        <h2 class="section-title text-center mb-3 login-title">Crear Cuenta</h2>
        <p class="text-center text-light mb-3 login-subtitle">Únete a la comunidad médica de MediForo.</p>

        <form action="${pageContext.request.contextPath}/registrar" method="post" class="d-flex flex-column align-items-center">
            <!-- Nombre -->
            <div class="input-container mb-2">
                <div class="d-flex align-items-center gap-2 mb-1">
                    <i class="bi bi-person text-info"></i>
                    <label for="nombre" class="form-label text-light mb-0">Nombre</label>
                </div>
                <input type="text" class="form-control login-input" id="nombre" name="nombre" required>
            </div>

            <!-- Apellido -->
            <div class="input-container mb-2">
                <div class="d-flex align-items-center gap-2 mb-1">
                    <i class="bi bi-person text-info"></i>
                    <label for="apellido" class="form-label text-light mb-0">Apellido</label>
                </div>
                <input type="text" class="form-control login-input" id="apellido" name="apellido" required>
            </div>

            <!-- Nombre de usuario -->
            <div class="input-container mb-2">
                <div class="d-flex align-items-center gap-2 mb-1">
                    <i class="bi bi-person-badge text-info"></i>
                    <label for="usuario" class="form-label text-light mb-0">Nombre de usuario</label>
                </div>
                <input type="text" class="form-control login-input" id="usuario" name="usuario" required>
            </div>

            <!-- Correo -->
            <div class="input-container mb-2">
                <div class="d-flex align-items-center gap-2 mb-1">
                    <i class="bi bi-envelope text-info"></i>
                    <label for="correo" class="form-label text-light mb-0">Correo electrónico</label>
                </div>
                <input type="email" class="form-control login-input" id="correo" name="correo" required>
            </div>

            <!-- Contraseña -->
            <div class="input-container mb-2">
                <div class="d-flex align-items-center gap-2 mb-1">
                    <i class="bi bi-lock-fill text-info"></i>
                    <label for="clave" class="form-label text-light mb-0">Contraseña</label>
                </div>
                <input type="password" class="form-control login-input" id="clave" name="clave" required>
            </div>

            <!-- Confirmar contraseña -->
            <div class="input-container mb-3">
                <div class="d-flex align-items-center gap-2 mb-1">
                    <i class="bi bi-lock text-info"></i>
                    <label for="confirmarClave" class="form-label text-light mb-0">Confirmar contraseña</label>
                </div>
                <input type="password" class="form-control login-input" id="confirmarClave" name="confirmarClave" required>
            </div>

            <!-- Botón -->
            <button type="submit" class="btn btn-info px-4 py-1 fw-semibold login-btn shadow-sm">
                <i class="bi bi-person-check-fill me-1"></i> Registrarse
            </button>
        </form>

        <div class="text-center mt-3">
            <small class="text-light">¿Ya tienes cuenta? <a href="login.jsp" class="text-info">Inicia sesión aquí</a></small>
        </div>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
