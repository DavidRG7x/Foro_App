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
    private TemaDAO temaDAO;

    @Override
    public void init() {
        temaDAO = new TemaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String busqueda = request.getParameter("busqueda");

        try {
            List<Tema> listaTemas;
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                listaTemas = temaDAO.buscarTemas(busqueda.trim());
            } else {
                listaTemas = temaDAO.listarTemas();
            }
            request.setAttribute("listaTemas", listaTemas);
            request.getRequestDispatcher("foro.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener los temas: " + e.getMessage());
            request.getRequestDispatcher("foro.jsp").forward(request, response);
        }
    }
}
