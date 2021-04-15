package br.com.bancoZup.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class NotaAluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer nota;

	
	@JoinColumn(unique=true)
	@OneToOne
	private AvaliacaoResposta avaliacaoResposta;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getNota() {
		return nota;
	}


	public void setNota(Integer nota) {
		this.nota = nota;
	}


	public AvaliacaoResposta getAvaliacaoResposta() {
		return avaliacaoResposta;
	}


	public void setAvaliacaoResposta(AvaliacaoResposta avaliacaoResposta) {
		this.avaliacaoResposta = avaliacaoResposta;
	}
	
	

}
