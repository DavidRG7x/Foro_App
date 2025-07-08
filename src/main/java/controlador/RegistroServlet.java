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

@WebServlet("/registrar")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Obtener datos del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String usuario = request.getParameter("usuario");
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        String confirmarClave = request.getParameter("confirmarClave");

        // Validar claves
        if (!clave.equals(confirmarClave)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        try {
            // Verificar si el correo ya existe
            if (dao.correoExiste(correo)) {
                request.setAttribute("error", "El correo ya está registrado.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            // Verificar si el nombre de usuario ya existe
            if (dao.usuarioExiste(usuario)) {
                request.setAttribute("error", "El nombre de usuario ya está en uso.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            // Crear objeto Usuario
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setApellido(apellido);
            nuevo.setUsuario(usuario);
            nuevo.setCorreo(correo);
            nuevo.setClave(clave); // en producción debería cifrarse

            // Registrar en BD
            boolean exito = dao.registrarUsuario(nuevo);

            if (exito) {
                request.setAttribute("exito", "Usuario registrado correctamente. Ahora puedes iniciar sesión.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No se pudo registrar el usuario.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar usuario: " + e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
        
    }
}