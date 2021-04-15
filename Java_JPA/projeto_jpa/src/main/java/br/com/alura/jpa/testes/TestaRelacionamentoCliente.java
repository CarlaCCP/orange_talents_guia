package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Cliente;
import br.com.alura.jpa.modelo.Conta;

public class TestaRelacionamentoCliente {

	public static void main(String[] args) {
		
		Conta conta = new Conta();
		conta.setId(1L); // para associar a uma conta especifica
		
		Cliente cliente = new Cliente ();
		cliente.setConta(conta);
		cliente.setEndereco("Testestes, teste , 90");
		cliente.setNome("Teste");
		cliente.setProfissao("Developer");
		

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(cliente);
		em.getTransaction().commit();
		em.close();
		
		
	}

}
