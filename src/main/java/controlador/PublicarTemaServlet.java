package controlador;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import modelo.DAO.TemaDAO;
import modelo.DTO.Tema;
import modelo.DTO.Usuario;

@WebServlet("/PublicarTemaServlet")
public class PublicarTemaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");

        if (titulo == null || contenido == null || titulo.trim().isEmpty() || contenido.trim().isEmpty()) {
            request.setAttribute("error", "Debes completar todos los campos.");
            request.getRequestDispatcher("nuevoTema.jsp").forward(request, response);
            return;
        }

        Tema tema = new Tema();
        tema.setTitulo(titulo);
        tema.setContenido(contenido);
        tema.setIdUsuario(usuario.getId());  // importante: debe tener ID seteado en la sesión

        try {
            TemaDAO dao = new TemaDAO();
            boolean guardado = dao.guardarTema(tema);

            if (guardado) {
                response.sendRedirect("foro");
            } else {
                request.setAttribute("error", "No se pudo publicar el tema.");
                request.getRequestDispatcher("nuevoTema.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar el tema: " + e.getMessage());
            request.getRequestDispatcher("nuevoTema.jsp").forward(request, response);
        }
    }
}
