package controlador;

import modelo.DAO.RespuestaDAO;
import modelo.DTO.Respuesta;
import modelo.DTO.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditarRespuestaServlet")
public class EditarRespuestaServlet extends HttpServlet {

    private RespuestaDAO respuestaDAO;

    @Override
    public void init() {
        respuestaDAO = new RespuestaDAO();
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession(false);
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    try {
        int id = Integer.parseInt(request.getParameter("id"));
        String contenido = request.getParameter("contenido");
        int idTema = Integer.parseInt(request.getParameter("idTema"));

        RespuestaDAO respuestaDAO = new RespuestaDAO();
        Respuesta respuesta = respuestaDAO.obtenerPorId(id);

        if (respuesta == null || respuesta.getIdUsuario() != usuario.getId()) {
            response.sendRedirect(request.getContextPath() + "/foro");
            return;
        }

        respuestaDAO.actualizarContenido(id, contenido);

        response.sendRedirect(request.getContextPath() + "/verTema?id=" + idTema);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect(request.getContextPath() + "/foro");
    }
}
}
