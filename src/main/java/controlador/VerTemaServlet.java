package controlador;

import modelo.DAO.TemaDAO;
import modelo.DAO.RespuestaDAO;
import modelo.DTO.Tema;
import modelo.DTO.Respuesta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/verTema")
public class VerTemaServlet extends HttpServlet {

    private TemaDAO temaDAO;
    private RespuestaDAO respuestaDAO;

    @Override
    public void init() throws ServletException {
        temaDAO = new TemaDAO();
        respuestaDAO = new RespuestaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTemaStr = request.getParameter("id");
        if (idTemaStr == null || idTemaStr.isEmpty()) {
            response.sendRedirect("foro");
            return;
        }

        try {
            int idTema = Integer.parseInt(idTemaStr);

            // ✅ INCREMENTAR VISTAS ANTES DE OBTENERLO
            temaDAO.incrementarVistas(idTema);

            Tema tema = temaDAO.obtenerTemaPorId(idTema);

            if (tema == null) {
                request.setAttribute("error", "Tema no encontrado.");
                request.getRequestDispatcher("foro.jsp").forward(request, response);
                return;
            }

            List<Respuesta> respuestas = respuestaDAO.listarRespuestasPorTema(idTema);

            request.setAttribute("tema", tema);
            request.setAttribute("listaRespuestas", respuestas);

            request.getRequestDispatcher("verTema.jsp").forward(request, response);

        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener el tema: " + e.getMessage());
            request.getRequestDispatcher("foro.jsp").forward(request, response);
        }
    }
}
