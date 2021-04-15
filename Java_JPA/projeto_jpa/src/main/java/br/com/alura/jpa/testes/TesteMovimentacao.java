package br.com.alura.jpa.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.ContaMovimentacao;
import br.com.alura.jpa.modelo.tipoMovimentacao;

public class TesteMovimentacao {

	public static void main(String[] args) {
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		Conta conta = new Conta ();
		conta.setTitular("Maria Aparecida");
		conta.setAgencia(123432);
		conta.setNumero(45768899);
		conta.setSaldo(8000.0);

		
		ContaMovimentacao contaMovi = new ContaMovimentacao();
		contaMovi.setConta(conta);
		contaMovi.setData(LocalDateTime.now());
		contaMovi.setTipoMovimentacao(tipoMovimentacao.ENTRADA);
		contaMovi.setValor(new BigDecimal(200.0));
		contaMovi.setDescricao("Casa do Açai");
		
		
		
		em.getTransaction().begin();
		em.persist(conta);
		em.persist(contaMovi);
		em.getTransaction().commit();
	}

}
