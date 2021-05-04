# Spring Data JPA: Repositórios, Consultas, Projeções e Specifications

Spring data já faz o EntityManager debaixo nos panos

Repositórios do JPA

- Repository
- CRUDRepository
- PaginationAndSortingRepository
- JPARepository

** Derived Queries, JPQL e Native Queries**

```
 List<Funcionario> findByNome(String nome);
```

```
List<Funcionario> findByNomeLike(String nome);
String nome = "%maria%";
```

```
List<Funcionario> findByNomeEndingWith(String nome)
```

```
List<Funcionario> findByNomeStartingWith(String nome)
```

```
List<Funcionario> findByNomeIsNull()
```

```
List<Funcionario> findByNomeIsNotNull()
```

```
List<Funcionario> findByNomeOrderByNomeAsc(String nome);
```

## Métodos longos

E como dica, como definimos os critérios de pesquisa por meio do nome do método, precisamos ter cuidado para não criar nomes gigantes e prejudicar a legibilidade. Nesse caso devemos favorecer as consultas com JPQL apresentadas no próximo vídeo.

Link: 

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

- Caso que precise consultas um pouco mais complexas, por exemplo usando relacionamentos e vários parâmetros, dê a preferência aos métodos com `@Query` para não prejudicar o entendimento pois os nomes dos métodos vão ficar muito longos para definir todos os critérios de busca. 
- Derived Queries

```
@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome "
			+ "AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate dataContratacao);
```

- Native Query

```
// Native query
	@Query(value ="SELECT * FROM tb_func f WHERE f.dataContratacao >= :data",
			nativeQuery = true) // para que ele não force um JPQL
	List <Funcionario> findDataContratacaoMaior(LocalDate data);
```

Resumo: 

- **Derived Queries** - queries criadas através de comandos Java
- **JPQL** - queries criadas através de uma estrutura SQL, porém com os nomes das entidades Java
- **Native Query** - queries padrões SQL que conseguimos executar no nosso Client SQL

**Paginação e Ordenação**

```
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>
```

```
	private void visualizar(Scanner scanner) {
		System.out.println("Qual página deseja visualizar?");
		Integer page = scanner.nextInt();
		Pageable pageable = PageRequest.of(page, 5, Sort.unsorted());
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Pagina atual " + funcionarios.getNumber());
		System.out.println("Total de elementos " + funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}
```

Exemplo com findByNome

```
@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository <Funcionario, Integer> {

    List<Funcionario> findByNome(String nome);

    //novo método com paginação
    List<Funcionario> findByNome(String nome, Pageable pageable);

    //outros métodos omitidos
}
```

Obs: Sort.unsorted() - faz a ordenação default pelos IDS

  - Para ordenar por outros atributos

    ```
    Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "nome"));
    ```

- Trabalhando com projeções de dados - entidade reduzida.

  ```
  @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f" nativeQuery = true)
  Lit<FuncionarioProjecao> findFuncionarioSalario();
  
  ```

  - Crie uma interface: O objetivo de criar essa interface é encapsular os valores de retorno da consulta dentro de métodos. **Interface based Projection**. Um DTO também poderia funcionar, mas é mais trabalhoso, apesar de poder ter mais métodos que ajudariam na visualização

```

public interface FuncionarioProjecao {

	Integer getId();
	String getNome();
	Double getSalario();
	
}

```

O método 

```
private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> list = repository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionario: id: " + f.getId()
		+ " | nome: " + f.getNome()+ " | salario: " + f.getSalario()));
	}
```

- Consultas dinâmicas (Specification)

Criteria API - em JPA

Specification - Em Spring DATA

O objetivo é entregar, ao desenvolver um objeto pronto, para que ele só tenha que se preocupar com qual operação SQL ele deseja executar.

```
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
JpaSpecificationExecutor<Funcionario>{

```

- crie um package.specification
- Nova Classe "SpecificationFuncionario"

```
public static Specification<Funcionario> nome(String nome){
return (root,criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), "%" + nome + "%")
}
```

O método 

```
	private void inicial (Scanner scanner) {
		System.out.println("Digite o nome: ");
		String nome = scanner.next(); 
		
		List<Funcionario> funcionarios = repository.findAll(
				Specification.where(SpecificationFuncionario.nome(nome)));
	}
```

O método 

```
	private void inicial (Scanner scanner) {
		System.out.println("Digite o nome: ");
		String nome = scanner.next(); 
		
		if (nome.equalsIgnoreCase("NULL")) {
			nome = null;
		}
		
		
		System.out.println("Digite o cpf: ");
		String cpf = scanner.next(); 
		
		if (cpf.equalsIgnoreCase("NULL")) {
			cpf = null;
		}
		
		
		System.out.println("Digite o SALARIO: ");
		Double salario = scanner.nextDouble(); 
		
		if (salario == 0) {
			salario = null;
		}
		
		System.out.println("Digite o data contratacao: ");
		String data = scanner.next();
		
		LocalDate dataContratacao;
		if (data.equalsIgnoreCase("NULL")) {
			dataContratacao = null;
		} else {
			dataContratacao = LocalDate.parse(data, formatter);
		}
		
		List<Funcionario> funcionarios = repository.findAll(
				Specification.where(SpecificationFuncionario.nome(nome))
				.or(SpecificationFuncionario.cpf(cpf))
				.or(SpecificationFuncionario.salario(salario))
				.or(SpecificationFuncionario.dataContratacao(dataContratacao))
				);
		funcionarios.forEach(System.out::println);
	}
```

- Em banco de dados não relacional

  É importante ressaltar que o framework do Spring Data permite a utilização de banco de dados relacionais, conforme estamos aprendendo, entretanto, ele também permite o uso de banco de dados não relacionais. Vamos ver como podemos utilizar o framework com o MongoDB, considerando que existem outras possibilidades de uso para bancos não relacionais.

  Quando queremos utilizar um banco de dados não relacional, não há necessidade de adicionarmos a dependência do JPA, nem mesmo do drive do banco, pois o Spring já entrega para nós uma dependência com tudo o que for necessário para acessarmos esse terminado banco. Por exemplo, o MongoDB utiliza a seguinte dependência:

  ```
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-mongodb</artifactId>
  </dependency>COPIAR CÓDIGO
  ```

  Apesar do acesso ao banco dentro do arquivo de propriedade ser bem semelhante, as tags mudam um pouco, sai o:

  ```
  spring.datasource.url=jdbc:mariadb://{URL}:{PORTA}/{NOME_DO_BANCO}COPIAR CÓDIGO
  ```

  E entra a tag:

  ```
  spring.data.mongodb.uri: mongodb://{URL}:{PORTA}/{NOME_DO_BANCO}COPIAR CÓDIGO
  ```

  > obs.: Em alguns bancos não relacionais, é muito comum adicionar o usuário e senha na própria URI, entretanto o Spring também nos dá a opção de adicionarmos os valores de forma apartada:

  ```
  spring.data.mongodb.username=root
  spring.data.mongodb.password=root
  spring.data.mongodb.database=test_db
  spring.data.mongodb.port=27017
  spring.data.mongodb.host=localhostCOPIAR CÓDIGO
  ```

  Com a alteração para um banco de dados não relacional, deixamos de lado nosso `CrudRepository`, pois o Spring nos entrega um repositório específico para cada tipo de banco de dados não relacional, e dentro dele já temos todos os recursos encapsulados.

  No caso do Mongo, utilizamos a interface `MongoRepository`. Esse repositório segue o mesmo princípio dos demais, sendo necessário passar no diamante o objeto que desejamos manipular, e o tipo do seu ID. Pronto! Basta utilizar esses passos que sua aplicação vai trabalhar perfeitamente com banco de dados não relacionais.