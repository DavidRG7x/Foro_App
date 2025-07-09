<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String paginaActual = "inicio";
    request.setAttribute("paginaActual", paginaActual);
%>

<jsp:include page="/includes/header.jsp" />

<div class="container d-flex align-items-center justify-content-center" style="min-height: 80vh;">
    <div class="main-box w-100" style="max-width: 900px;">

        <h1 class="section-title mb-4 text-center">Bienvenido a MediForo</h1>
        <p class="lead text-center mb-4">
            Tu espacio para compartir experiencias, resolver dudas médicas y encontrar apoyo en comunidad.
        </p>

        <div class="row text-center">
            <div class="col-md-4 mb-4">
                <i class="bi bi-person-lines-fill text-info" style="font-size: 3rem;"></i>
                <h5 class="mt-2">Usuarios Registrados</h5>
                <p>Conéctate con personas que viven lo mismo que tú.</p>
            </div>
            <div class="col-md-4 mb-4">
                <i class="bi bi-chat-dots-fill text-info" style="font-size: 3rem;"></i>
                <h5 class="mt-2">Foro Activo</h5>
                <p>Participa en debates abiertos sobre salud y bienestar.</p>
            </div>
            <div class="col-md-4 mb-4">
                <i class="bi bi-heart-pulse-fill text-info" style="font-size: 3rem;"></i>
                <h5 class="mt-2">Bienestar Primero</h5>
                <p>Accede a consejos, experiencias y soporte emocional.</p>
            </div>
        </div>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/registro.jsp" class="btn btn-info me-2">Crear Cuenta</a>
            <a href="${pageContext.request.contextPath}/foro" class="btn btn-outline-info">Explorar Foro</a>
        </div>

    </div>
</div>

<jsp:include page="/includes/footer.jsp" />
