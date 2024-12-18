/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Modelo.Producto;
import controlador.ControladorCarrito;
import controlador.ControladorProducto;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class Carrito extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
// Obtener el nombre de usuario de la sesión
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");

        if (usuario != null && !usuario.isEmpty()) {
            // Crear una instancia del controlador
            ControladorCarrito controladorCarrito = new ControladorCarrito();

            // Obtener el HTML del carrito
            String productosHTML = controladorCarrito.cargarCarrito(usuario);

            // Pasar el HTML y el total al JSP
            request.setAttribute("productosHTML", productosHTML);
            // Si necesitas el total, puedes obtenerlo del controlador, pero en este caso no es necesario
            request.setAttribute("total", controladorCarrito.getTotal());

            // Redirigir al JSP que muestra el carrito
            request.getRequestDispatcher("carrito.jsp").forward(request, response);
        } else {
            // Si el usuario no está logueado, redirigir al inicio de sesión
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la sesión del usuario
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");

        if (usuario != null && !usuario.isEmpty()) {
            // Obtener el nombre del producto desde el formulario
            String nombreProducto = request.getParameter("productoNombre");
            // Crear instancia del controlador de carrito
            ControladorCarrito controladorCarrito = new ControladorCarrito();

            // Intentar agregar el producto al carrito
            boolean productoAgregado = controladorCarrito.agregarProductoAlCarrito(nombreProducto, usuario);

            // Si el producto se agregó correctamente, redirigir al carrito
            if (productoAgregado) {
                response.sendRedirect("tienda.jsp");
            } else {
                // Si ocurre un error, redirigir a la tienda o mostrar un mensaje de error
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al agregar el producto al carrito.");
            }
        } else {
            // Si el usuario no está autenticado, redirigir a la página de inicio de sesión
            response.sendRedirect("inicioSesion.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet que gestiona el carrito de compras";
    }// </editor-fold>

}
