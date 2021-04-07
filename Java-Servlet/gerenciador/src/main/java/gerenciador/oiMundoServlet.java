package gerenciador;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/oi")
public class oiMundoServlet extends HttpServlet{
// Usando só o service, a requisição aceita tanto get ou post. Use doPost!
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Printa no navegador
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Testando ola mundo com o servlet</h1>");
		out.println("</body>");
		out.println("</html>");
		
		//Printa no console
		System.out.print("o servlet foi chamado");
		
	}
}
