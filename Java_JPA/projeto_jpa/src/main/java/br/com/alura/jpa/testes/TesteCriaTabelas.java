package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


//Deverá chamar a JPA e criar a nossa tabela
/*
 Usaremos o entitymanager - que é um objeto central 
 que permitirá fazermos todas as operações 
 no banco de dados. Mas precisamos de um fabrica para cria-lo
 */
// Persistence faz referencia ao xml
// o createEntity receberá o nome da nossa unidade de persistencia
public class TesteCriaTabelas {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		
		EntityManager createEntityManager = emf.createEntityManager(); // iniciando o objeto
		
        emf.close(); // fechando a fabrica para somente ver o hibernete criar a tabela
	}

}
