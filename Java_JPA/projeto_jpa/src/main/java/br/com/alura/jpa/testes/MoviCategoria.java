package br.com.alura.jpa.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Categoria;
import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.ContaMovimentacao;
import br.com.alura.jpa.modelo.tipoMovimentacao;

public class MoviCategoria {

	public static void main(String[] args) {
		
		Categoria categoria = new Categoria ("Viagens");
		Categoria categoria2 = new Categoria ("Negocios");
		
		Conta conta = new Conta();
		conta.setId(2L); // para associar as movimentacoes a uma conta especifica
		
		ContaMovimentacao contaMovi = new ContaMovimentacao();
		contaMovi.setDescricao("Viagem à SP");
		contaMovi.setConta(conta);
		contaMovi.setData(LocalDateTime.now());
		contaMovi.setTipoMovimentacao(tipoMovimentacao.SAIDA);
		contaMovi.setValor(new BigDecimal(200.0));
		contaMovi.setCategoria(Arrays.asList(categoria, categoria2));
		
		

		ContaMovimentacao contaMovi2 = new ContaMovimentacao();
		contaMovi2.setDescricao("Viagem à RJ");
		contaMovi2.setConta(conta);
		contaMovi2.setData(LocalDateTime.now());
		contaMovi2.setTipoMovimentacao(tipoMovimentacao.SAIDA);
		contaMovi2.setValor(new BigDecimal(200.0));
		contaMovi2.setCategoria(Arrays.asList(categoria, categoria2));
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		
		em.getTransaction().begin();
		em.persist(contaMovi);
		em.persist(contaMovi2);
		em.persist(categoria);
		em.persist(categoria2);
		
		em.getTransaction().commit();
		em.close();
	}

}
