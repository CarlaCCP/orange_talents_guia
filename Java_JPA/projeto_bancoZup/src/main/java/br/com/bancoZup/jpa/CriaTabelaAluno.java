package br.com.bancoZup.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bancoZup.modelo.Aluno;

public class CriaTabelaAluno {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoZup");
		EntityManager em = emf.createEntityManager();
		
		// Tabela aluno criada e populada
		Aluno aluno = new Aluno();
		
		aluno.setNome("Carla Cristina");
		aluno.setIdade(25);
		aluno.setEmail("Carla@carla.com");
		
		em.getTransaction().begin();
		em.persist(aluno);
		em.getTransaction().commit();
		
		em.close();

	}

}
