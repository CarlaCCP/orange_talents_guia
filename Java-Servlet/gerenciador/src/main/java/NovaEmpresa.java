

import java.io.IOException;
import java.io.PrintWriter;

import gerenciador.Banco;
import gerenciador.Empresa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//Usando só o service, a requisição aceita tanto get ou post. Use doPost!

@WebServlet("/novaEmpresa")
public class NovaEmpresa extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print("O servel está funcionando\n");
		String nome = request.getParameter("nome");
	
		Empresa empresa = new Empresa();
		empresa.setNome(nome);
		
		Banco banco = new Banco();
		banco.adiciona(empresa);
		
		PrintWriter out = response.getWriter();
		out.println(" <html> <body> <h1> Nova empresa cadastrada com sucesso, nome: " + nome + " </h1></body> </html>");
	}

}
