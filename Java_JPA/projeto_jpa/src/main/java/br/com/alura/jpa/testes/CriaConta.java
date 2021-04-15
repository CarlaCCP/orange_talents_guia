package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class CriaConta {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		Conta conta = new Conta();
		conta.setTitular("Carla Cristina");
		conta.setNumero(1245);
		conta.setAgencia(2145);
		
		
		em.getTransaction().begin(); // a tabela só será populada dentro de uma transação
		em.persist(conta); // Passa  o objeto que queremos persistir em conta
		em.getTransaction().commit(); // se todos os comando da transação funcionarem acontece o commit
		
		// Se a linha de comandos não funcionar acontece o rollback
	}

}
