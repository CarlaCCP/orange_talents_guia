package br.com.alura.jpa.testes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.alura.jpa.modelo.Categoria;
import br.com.alura.jpa.modelo.ContaMovimentacao;

public class TesteJPQLJoin {

	public static void main(String[] args) {
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager em = emf.createEntityManager();

        String sql = "select m from ContaMovimentacao m join m.categoria c  where c = :pCategoria";

        Categoria categoria = new Categoria();
        categoria.setId(2L);

        TypedQuery<ContaMovimentacao> query = em.createQuery(sql, ContaMovimentacao.class);
        query.setParameter("pCategoria", categoria);

        List<ContaMovimentacao> movimentacoes = query.getResultList();
        for (ContaMovimentacao movimentacao : movimentacoes) {
            System.out.println("Descrição: " + movimentacao.getDescricao());
            System.out.println("Valor: " + movimentacao.getValor());
            System.out.println("Tipo: " + movimentacao.getTipoMovimentacao());
        }

	}

}
