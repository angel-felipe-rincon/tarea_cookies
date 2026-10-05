
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet2
 */
@WebServlet("/Servlet2")
public class Servlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub


			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			Map<String, String> datos = new HashMap<>();
			Cookie[] cookies = request.getCookies(); // es null si el navegador no envió ninguna
			if (cookies != null) {
				for (Cookie c : cookies) {
					datos.put(c.getName(), URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8));
				}
			}
			
			String[] requeridas = { "uname", "uedad", "ucorreo", "ugenero", "uestudiante" };
			for (String nombre : requeridas) {
				if (!datos.containsKey(nombre)) {
					out.print("<h3>No se encontraron los datos (cookies vacías, expiradas o bloqueadas).</h3>");
					out.print("<a href='index.html'>Volver al formulario</a>");
					out.close();
					return;
				}
			}
			
			// 3. Mostrar los datos
			boolean estudiante = Boolean.parseBoolean(datos.get("uestudiante"));
			out.print("<h3>Hello " + datos.get("uname") + "</h3>");
			out.print("<ul>");
			out.print("<li>Edad: " + datos.get("uedad") + "</li>");
			out.print("<li>Correo: " + datos.get("ucorreo") + "</li>");
			out.print("<li>Género: " + datos.get("ugenero") + "</li>");
			out.print("<li>Estudiante: " + estudiante + "</li>");
			out.print("</ul>");
			out.print("<a href='index.html'>Volver</a>");
			out.close();
		}

	}
