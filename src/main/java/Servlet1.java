
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			String nombre = limpiar(request.getParameter("userName"));
			String edadTxt = limpiar(request.getParameter("edad"));
			String correo = limpiar(request.getParameter("correo"));
			String genero = limpiar(request.getParameter("genero"));
			boolean estudiante = request.getParameter("estudiante") != null;
			
			List<String> errores = new ArrayList<>();
			
			if (nombre.isEmpty()) {
				errores.add("El nombre es obligatorio.");
			} else if (nombre.length() < 2 || nombre.length() > 50) {
				errores.add("El nombre debe tener entre 2 y 50 caracteres.");
			} else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
				errores.add("El nombre solo puede tener letras y espacios.");
			}
			
			int edad = 0;
			if (edadTxt.isEmpty()) {
				errores.add("La edad es obligatoria.");
			} else {
				try {
					edad = Integer.parseInt(edadTxt);
					if (edad < 1 || edad > 120) {
						errores.add("La edad debe estar entre 1 y 120.");
					}
				} catch (NumberFormatException e) {
					errores.add("La edad debe ser un número entero.");
				}
			}
			
			if (correo.isEmpty()) {
				errores.add("El correo es obligatorio.");
			} else if (!correo.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
				errores.add("El correo no tiene un formato válido.");
			}
			
			if (genero.isEmpty()) {
				errores.add("Debe seleccionar un género.");
			} else if (!genero.equals("Masculino") && !genero.equals("Femenino") && !genero.equals("Otro")) {
				errores.add("El género seleccionado no es válido.");
			}
			
			if (!errores.isEmpty()) {
				out.print("<h3>Corrige los siguientes errores:</h3><ul>");
				for (String e : errores) {
					out.print("<li>" + e + "</li>");
				}
				out.print("</ul><a href='index.html'>Volver al formulario</a>");
				out.close();
				return;
			}
			
			
			agregarCookie(request, response, "uname", nombre);
			agregarCookie(request, response, "uedad", String.valueOf(edad));
			agregarCookie(request, response, "ucorreo", correo);
			agregarCookie(request, response, "ugenero", genero);
			agregarCookie(request, response, "uestudiante", String.valueOf(estudiante));
	 
			out.print("Welcome " + nombre);
			out.print("<form action='Servlet2' method='post'>");
			out.print("<input type='submit' value='go'>");
			out.print("</form>");
			out.close();
		}
			
			
	private void agregarCookie(HttpServletRequest request, HttpServletResponse response, String nombre,
			String valor) {
		Cookie ck = new Cookie(nombre, URLEncoder.encode(valor, StandardCharsets.UTF_8));
		ck.setMaxAge(60 * 60); // vive 1 hora
		response.addCookie(ck);
	}	

	private String limpiar(String s) {
		if (s == null) {
		    return "";
		} else {
		    return s.trim();
		}
	}
}
