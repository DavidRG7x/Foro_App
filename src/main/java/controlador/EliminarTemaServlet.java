package controlador;

import modelo.DAO.TemaDAO;
import modelo.DTO.Tema;
import modelo.DTO.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EliminarTemaServlet")
public class EliminarTemaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            TemaDAO dao = new TemaDAO();
            Tema tema = dao.obtenerTemaPorId(id);

            if (tema != null && tema.getIdUsuario() == usuario.getId()) {
                dao.eliminarTema(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("foro");
    }
}
