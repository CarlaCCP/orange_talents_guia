package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class AlteraConta {

	public static void main(String[] args) {
		

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		// Aqui ele procura um usuario especifico, passando o id e o seu objeto
		Conta contaCarla = em.find(Conta.class, 1L);
		
		em.getTransaction().begin();
		
		contaCarla.setSaldo(1000.0);
		
		em.getTransaction().commit();

		/* A entidade conta já está sendo gerenciada pelo JPA
		  por isso não é necessário usar o em.persist(conta). A Conta está Managed*/
	}

}
