package controlador;

import modelo.DAO.UsuarioDAO;
import modelo.DTO.Usuario;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        UsuarioDAO dao = new UsuarioDAO();
        try {
            Usuario usuario = dao.validarUsuario(correo, clave);
            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                response.sendRedirect("foro.jsp");
            } else {
                request.setAttribute("error", "Correo o contraseña incorrectos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno del servidor.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
