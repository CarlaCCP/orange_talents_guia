package br.com.orange.dominio;

public class RespostaQuestao {

	private Avaliacao avaliacao;

	private Aluno aluno;

	private int nota;
	

	public RespostaQuestao () {} 
	public RespostaQuestao(Avaliacao avaliacao, Aluno aluno, int nota) {
		
		if (avaliacao == null) {
			throw new IllegalArgumentException("A avaliação não pode ser nula");
		}
		else if (aluno == null) {
			throw new IllegalArgumentException("O aluno não pode ser nula");
		}
		else if (nota < 0) {
			throw new IllegalArgumentException("A nota não pode ser menor que zero");
		}
		else if (nota > 10) {
			throw new IllegalArgumentException("A nota não pode ser maior que10");
		}
		
		else {
			this.aluno = aluno;
			this.avaliacao = avaliacao;
			this.nota  = nota;
		}
		

	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public int getNota() {
		return nota;
	}
	
	
	
}
