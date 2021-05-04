package br.com.orange.teste;

import org.junit.Assert; 
import org.junit.Test;

import br.com.orange.dominio.Aluno;
import br.com.orange.dominio.Avaliacao;
import br.com.orange.dominio.RespostaQuestao;

public class TestaResposta {

	@Test(expected = IllegalArgumentException.class)
	public void naoAceitaAvaliacaoNula() {
		Avaliacao avaliacao = new Avaliacao ();
		Aluno joao = new Aluno ("Joao");
		RespostaQuestao resposta = new RespostaQuestao(avaliacao, joao, 10);
		Assert.fail();	
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void naoAceitaAlunoNula() {
//		Avaliacao avaliacao = new Avaliacao ("Teste");
//		Aluno joao = new Aluno ();
//		RespostaQuestao resposta = new RespostaQuestao(avaliacao, joao, 10);
//		
//		Assert.fail();
//		
//	}
	
	

}
