<%@ page import="modelo.DTO.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>MediForo - Comunidad de Salud</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
    
    <!-- Font Awesome CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css" />
</head>
<body>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3 shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand fs-3 fw-bold text-info" href="${pageContext.request.contextPath}/index.jsp">
            MediForo
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link nav-link-principal <%= "inicio".equals(request.getAttribute("paginaActual")) ? "active" : "" %>" 
                       href="${pageContext.request.contextPath}/index.jsp">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link nav-link-principal <%= "foro".equals(request.getAttribute("paginaActual")) ? "active" : "" %>" 
                       href="${pageContext.request.contextPath}/foro">Foro</a>
                </li>
            </ul>

            <form class="d-flex me-3 search-form" role="search" action="${pageContext.request.contextPath}/ForoController" method="GET">
                <input type="search" name="busqueda" class="form-control search-input me-2" 
                       placeholder="Buscar temas..." aria-label="Buscar" />
                <button type="submit" class="btn btn-info search-btn" title="Buscar">
                    <i class="bi bi-search"></i>
                </button>
            </form>

            <% if (usuario != null) { %>
                <span class="navbar-text me-2 text-light">
                    Hola, <strong><%= usuario.getUsuario() %></strong>
                </span>

                <a href="perfil.jsp" class="btn btn-sm btn-outline-light me-2" title="Ver perfil">
                    <i class="bi bi-person-circle"></i>
                </a>

                <a href="logout.jsp" class="btn btn-sm btn-outline-light" title="Cerrar sesión">
                    <i class="bi bi-box-arrow-right"></i>
                </a>
            <% } else { %>
                <a href="login.jsp" class="btn btn-sm btn-outline-light me-2" title="Iniciar sesión">
                    <i class="bi bi-box-arrow-in-right"></i>
                </a>
                <a href="registro.jsp" class="btn btn-sm btn-outline-light" title="Registrarse">
                    <i class="bi bi-person-plus"></i>
                </a>
            <% } %>

        </div>
    </div>
</nav>
