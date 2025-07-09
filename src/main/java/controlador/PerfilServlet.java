package controlador;


import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.DAO.TemaDAO;
import modelo.DAO.RespuestaDAO;
import modelo.DAO.UsuarioDAO;
import modelo.DTO.Usuario;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        TemaDAO temaDAO = new TemaDAO();
        RespuestaDAO respuestaDAO = new RespuestaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            int numTemas = temaDAO.contarTemasPorUsuario(usuario.getId());
            int numMensajes = respuestaDAO.contarRespuestasPorUsuario(usuario.getId());
            Date fechaRegistro = usuarioDAO.obtenerFechaRegistro(usuario.getId());

            request.setAttribute("numTemas", numTemas);
            request.setAttribute("numMensajes", numMensajes);
            request.setAttribute("fechaRegistro", fechaRegistro);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/perfil.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
