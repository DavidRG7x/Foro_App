package controlador;

import modelo.DAO.TemaDAO;
import modelo.DTO.Tema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import modelo.DAO.RespuestaDAO;
import modelo.DTO.Respuesta;

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
            response.sendRedirect("foro"); // Redirigir a la lista si no hay id
            return;
        }

        try {
            int idTema = Integer.parseInt(idTemaStr);
            Tema tema = temaDAO.obtenerTemaPorId(idTema);

            if (tema == null) {
                request.setAttribute("error", "Tema no encontrado.");
                request.getRequestDispatcher("foro.jsp").forward(request, response);
                return;
            }

            // 🔴 AQUÍ: cargar respuestas desde DAO
            List<Respuesta> respuestas = respuestaDAO.listarRespuestasPorTema(idTema);

            request.setAttribute("tema", tema);
            request.setAttribute("listaRespuestas", respuestas); // 🔥 Esto es necesario para que verTema.jsp funcione

            request.getRequestDispatcher("verTema.jsp").forward(request, response);

        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener el tema: " + e.getMessage());
            request.getRequestDispatcher("foro.jsp").forward(request, response);
        }
    }
}

