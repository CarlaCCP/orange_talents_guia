package br.com.bancoZup.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bancoZup.modelo.Avaliacao;

public class CriaTabelaAvaliacao {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoZup");
		EntityManager em = emf.createEntityManager();
		
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setTitulo("Java");
		avaliacao.setDescricao("Faça um projeto com JPA");
		
		em.getTransaction().begin();
		em.persist(avaliacao);
		em.getTransaction().commit();
		em.close();

	}

}
