package controlador;

import modelo.DAO.RespuestaDAO;
import modelo.DTO.Respuesta;
import modelo.DTO.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/PublicarRespuestaServlet")
public class PublicarRespuestaServlet extends HttpServlet {

    private RespuestaDAO respuestaDAO;

    @Override
    public void init() throws ServletException {
        respuestaDAO = new RespuestaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        Usuario usuario = null;

        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuarioLogueado");
        }

        // Si el usuario no está logueado, redirigir al login
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Obtener parámetros del formulario
            int idTema = Integer.parseInt(request.getParameter("idTema"));
            String contenido = request.getParameter("contenido");

            if (contenido == null || contenido.trim().isEmpty()) {
                // Contenido vacío, redirigir de nuevo al tema
                response.sendRedirect(request.getContextPath() + "/verTema?id=" + idTema);
                return;
            }

            // Crear objeto respuesta
            Respuesta respuesta = new Respuesta();
            respuesta.setIdUsuario(usuario.getId()); // Usa el ID del usuario logueado
            respuesta.setIdTema(idTema);
            respuesta.setContenido(contenido.trim());

            // Guardar respuesta usando DAO
            boolean exito = respuestaDAO.guardarRespuesta(respuesta);

            // Redirigir al mismo tema para ver la respuesta agregada
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/verTema?id=" + idTema);
            } else {
                // Algo salió mal al guardar
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
