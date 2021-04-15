package br.com.bancoZup.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.bancoZup.modelo.Aluno;
import br.com.bancoZup.modelo.AvaliacaoResposta;

public class QueryJPQL {

	/*
	 * Caso você precise carregar uma auto correção e tenha que descobrir o nome
	 * do(a) aluno(a) que fez, como você faria? Algum ponto de atenção que deveria
	 * ter?
	 */
	public static void main(String[] args) {

		// select nome from aluno join avalaicaoresposta on idaluno = idlauno;

		AvaliacaoResposta resposta = new AvaliacaoResposta();
		resposta.setId(2L);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoZup");
		EntityManager em = emf.createEntityManager();
		 // select x.nome from aluno x inner join avaliacaoresposta y on y.aluno_id = x.id where y.id =1;
		String jpql = "select x from Aluno x inner join AvaliacaoResposta y on y.aluno.id = x.id  where y = :pResposta";

		TypedQuery<Aluno> query = em.createQuery(jpql, Aluno.class);
		
		 query.setParameter("pResposta", resposta);
		// List<ContaMovimentacao> movimentacoes = query.getResultList();

		List<Aluno> alunos = query.getResultList();
		
		for (Aluno aluno: alunos) {
			System.out.println("Resposta da avaliacao: " +  aluno.getNome());
		}
	}

}
