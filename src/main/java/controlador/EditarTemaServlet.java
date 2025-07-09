package controlador;

import modelo.DAO.TemaDAO;
import modelo.DTO.Tema;
import modelo.DTO.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditarTemaServlet")
public class EditarTemaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String titulo = request.getParameter("titulo");
            String contenido = request.getParameter("contenido");

            TemaDAO dao = new TemaDAO();
            Tema tema = dao.obtenerTemaPorId(id);

            if (tema != null && tema.getIdUsuario() == usuario.getId()) {
                dao.actualizarTema(id, titulo, contenido);
            }

            response.sendRedirect("verTema?id=" + id);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("foro");
        }
    }
}
