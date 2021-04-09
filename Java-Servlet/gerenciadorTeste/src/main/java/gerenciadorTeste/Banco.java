package gerenciadorTeste;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gerenciadorTeste.Banco;
import gerenciadorTeste.Empresa;

public class Banco {

private static List<Empresa> lista = new ArrayList<> ();
private static Integer  chaveSequencial = 1;
	static {
        Empresa empresa = new Empresa();
        empresa.setId(chaveSequencial++);
        empresa.setNome("Alura");
        Empresa empresa2 = new Empresa();
        empresa2.setNome("Caelum");
        empresa2.setId(chaveSequencial);
        lista.add(empresa);
        lista.add(empresa2);


    }
	
	public void adiciona(Empresa empresa) {
		empresa.setId(Banco.chaveSequencial ++);
		Banco.lista.add(empresa);
	}
	
	public List<Empresa> getEmpresa(){
		return Banco.lista;
	}
	
	// Array list nõ consegue excluir enquanto itera
	// por isso foi necessário usar o iterator
	public void removeEmpresa(Integer id) { 

	    Iterator<Empresa> it = lista.iterator();

	    while(it.hasNext()) { 
	        Empresa emp = it.next();

	        if(emp.getId() == id ) { 
	            it.remove();
	        }
	    }
	}
	
	public Empresa buscaEmpresaPelaId(Integer id) { 
	    for (Empresa empresa: lista) { 
	        if(empresa.getId() == id)  {
	            return empresa;
	        }

	    }
	    return null;
	}
}
