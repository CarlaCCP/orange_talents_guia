package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class CriaContaComSaldo {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		Conta conta = new Conta ();
		
		conta.setAgencia(124578);
		conta.setTitular("Victor Saade");
		conta.setNumero(12458);
		conta.setSaldo(500.0);
		
		
		em.getTransaction().begin();
		em.persist(conta);
		em.getTransaction().commit();
		
	}

}
