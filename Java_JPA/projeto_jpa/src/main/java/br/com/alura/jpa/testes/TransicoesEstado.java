package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class TransicoesEstado {

	public static void main(String[] args) {

		// Transient
		// Quando a conta não possui referencia na Jpa
		// Objeto desvinculado 
		
		Conta conta = new Conta();
		conta.setAgencia(124578);
		conta.setTitular("João Victor");
		conta.setNumero(12458);
		conta.setSaldo(500.0);
		
		
		// Transient - Managed
		// Agora ela possui referencia
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(conta);
		
		// Managed - Removed
		
		em.remove(conta);
		
		em.getTransaction().commit();
	}

}
