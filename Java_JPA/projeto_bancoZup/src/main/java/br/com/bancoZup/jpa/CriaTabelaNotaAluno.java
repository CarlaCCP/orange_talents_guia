package br.com.bancoZup.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bancoZup.modelo.AvaliacaoResposta;
import br.com.bancoZup.modelo.NotaAluno;

public class CriaTabelaNotaAluno {

	public static void main(String[] args) {
		

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoZup");
		EntityManager em = emf.createEntityManager();
		
		AvaliacaoResposta resp = new AvaliacaoResposta();
		resp.setId(2L);
		
		NotaAluno nota = new NotaAluno();
		nota.setNota(5);
		nota.setAvaliacaoResposta(resp);
		
		em.getTransaction().begin();
		em.persist(nota);
		em.getTransaction().commit();
		em.close();
		
	}

}
