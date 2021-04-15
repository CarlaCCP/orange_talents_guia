package br.com.bancoZup.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.bancoZup.modelo.Aluno;
import br.com.bancoZup.modelo.AvaliacaoResposta;

public class QueryJPQL2 {

	public static void main(String[] args) {
		/*
		  Caso você precise carregar as respostas de um(a) 
		  aluno(a) a partir do objeto da classe Aluno, como você faria? 
		  Algum ponto de atenção?*/
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoZup");
		EntityManager em = emf.createEntityManager();
		
		Aluno aluno = new Aluno();
		aluno.setId(1L);
		// "select x from Aluno x inner join AvaliacaoResposta y on y.aluno.id = x.id  where y = :pResposta";
		String jpql = "select x from AvaliacaoResposta x inner join Aluno y on x.aluno.id = y.id where y = :pAluno";
		
		TypedQuery<AvaliacaoResposta> query = em.createQuery(jpql, AvaliacaoResposta.class);
		
		query.setParameter("pAluno", aluno);
		
		List<AvaliacaoResposta> avaliacoes = query.getResultList();
		
		for (AvaliacaoResposta avaliacaoResposta: avaliacoes) {
			
			System.out.println("Resposta: " + avaliacaoResposta.getResposta());
			
		}
	}

}
