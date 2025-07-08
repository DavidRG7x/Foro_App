<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="includes/header.jsp" %>

<main class="login-main d-flex justify-content-center align-items-center py-5">
    <div class="main-box login-box p-4">

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
            <i class="bi bi-person-circle icon-login"></i>
        </div>

        <h2 class="section-title text-center mb-3 login-title">Iniciar Sesión</h2>
        <p class="text-center text-light mb-3 login-subtitle">
            Accede a tu cuenta para participar en el foro.
        </p>

        <form action="${pageContext.request.contextPath}/login" method="post" class="d-flex flex-column align-items-center">
            <div class="input-container mb-3">
                <div class="mb-2 d-flex align-items-center gap-2 login-label-container">
                    <i class="bi bi-envelope text-info"></i>
                    <label for="correo" class="form-label mb-0 text-light">Correo electrónico</label>
                </div>
                <input type="email" class="form-control login-input" id="correo" name="correo" required>
            </div>

            <div class="input-container mb-3">
                <div class="mb-2 d-flex align-items-center gap-2 login-label-container">
                    <i class="bi bi-lock-fill text-info"></i>
                    <label for="clave" class="form-label mb-0 text-light">Contraseña</label>
                </div>
                <input type="password" class="form-control login-input" id="clave" name="clave" required>
            </div>

            <button type="submit" class="btn btn-info px-4 py-1 fw-semibold login-btn shadow-sm">
                <i class="bi bi-box-arrow-in-right me-1 fs-6"></i> Ingresar
            </button>
        </form>

        <div class="text-center mt-3">
            <small class="text-light">¿No tienes cuenta? <a href="registro.jsp" class="text-info">Regístrate aquí</a></small>
        </div>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
