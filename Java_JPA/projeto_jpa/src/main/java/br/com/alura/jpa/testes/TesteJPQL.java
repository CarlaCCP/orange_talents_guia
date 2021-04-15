package br.com.alura.jpa.testes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.alura.jpa.modelo.Categoria;
import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.ContaMovimentacao;

public class TesteJPQL {
// Passando um query de relacional para orientada a objetos
	public static void main(String[] args) {
		
		Conta conta = new Conta();
		conta.setId(2L);
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		// :pconta - forma de identificar que conta é um parametro
		String jpql = "select m from ContaMovimentacao m where m.conta = :pconta order By m.descricao desc";
		
		
		TypedQuery<ContaMovimentacao> query = em.createQuery(jpql, ContaMovimentacao.class);
		query.setParameter("pconta", conta);
		
		
		
		List<ContaMovimentacao> movimentacoes = query.getResultList();
		
		
		for (ContaMovimentacao ContaMovimetacao: movimentacoes ) {

			System.out.println("\nDescrição: " + ContaMovimetacao.getDescricao());
			System.out.println("Tipo: " + ContaMovimetacao.getTipoMovimentacao());
			System.out.println("Valor: " + ContaMovimetacao.getValor());
		}
		
		

	}

}
