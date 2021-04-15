package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


//Dever� chamar a JPA e criar a nossa tabela
/*
 Usaremos o entitymanager - que � um objeto central 
 que permitir� fazermos todas as opera��es 
 no banco de dados. Mas precisamos de um fabrica para cria-lo
 */
// Persistence faz referencia ao xml
// o createEntity receber� o nome da nossa unidade de persistencia
public class TesteCriaTabelas {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		
		EntityManager createEntityManager = emf.createEntityManager(); // iniciando o objeto
		
        emf.close(); // fechando a fabrica para somente ver o hibernete criar a tabela
	}

}
