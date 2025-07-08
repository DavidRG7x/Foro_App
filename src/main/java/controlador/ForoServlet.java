package controlador;

import modelo.DAO.TemaDAO;
import modelo.DTO.Tema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/foro")
public class ForoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            TemaDAO dao = new TemaDAO();
            List<Tema> listaTemas = dao.listarTemas();
            request.setAttribute("listaTemas", listaTemas);
            request.getRequestDispatcher("foro.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los temas: " + e.getMessage());
            request.getRequestDispatcher("foro.jsp").forward(request, response);
        }
    }
}
