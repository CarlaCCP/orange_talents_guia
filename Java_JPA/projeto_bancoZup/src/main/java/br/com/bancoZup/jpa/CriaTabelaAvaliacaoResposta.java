package br.com.bancoZup.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bancoZup.modelo.Aluno;
import br.com.bancoZup.modelo.Avaliacao;
import br.com.bancoZup.modelo.AvaliacaoResposta;

public class CriaTabelaAvaliacaoResposta {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoZup");
		EntityManager em = emf.createEntityManager();
		
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setId(1L);
		
		Aluno aluno = new Aluno();
		aluno.setId(2L);
		
		AvaliacaoResposta resposta = new AvaliacaoResposta();
		
		resposta.setAluno(aluno);
		resposta.setResposta("Faça um projeto Maven...");
		resposta.setAvaliacao(avaliacao);
		
		
		em.getTransaction().begin();
		em.persist(resposta);
		em.getTransaction().commit();
		em.close();
		

	}

}
