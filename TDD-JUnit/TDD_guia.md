# Java e Testes: Test Driven Development com JUnit



Qual a ordem correta dos parâmetros do `assertEquals()` (e de todos os outros métodos similares) da classe `Assert`?

A ordem é sempre `(esperado, calculado)`.

Apesar de não fazer diferença nenhuma (afinal, esperamos que os dois números sejam iguais), é importante manter essa ordem.

Quando o teste falha, o JUnit usa esses valores para exibir uma mensagem de erro amigável. Por exemplo, `expected 10, but was 20`. Ou seja, esperava 10, mas veio 20. Se invertêssemos os valores, a mensagem ficaria errada!

- O pacote `junit.framework` é o pacote da versão mais antiga do JUnit, e deve ser evitado.



Ao testar uma lista, quantas verificações (quantidade de asserts) geralmente fazemos?



Precisamos sempre garantir todo o conteúdo da lista retornada. Veja que só garantir o tamanho da lista não nos ajuda muito, afinal a lista pode ter o tamanho certo, mas ter o conteúdo inválido.





Tratar o caso da lista com um elemento separado do caso da lista com vários elementos faz todo sentido. É muito comum, durante a implementação, pensarmos direto no caso complicado, e esquecermos de casos simples, mas que acontecem. Por esse motivo é importante testarmos esses casos.

Quando lidamos com listas, por exemplo, é sempre interessante tratarmos o caso da lista cheia, da lista com apenas um elemento, e da lista vazia.

Se estamos lidando com algoritmos cuja ordem é importante, precisamos testar ordem crescente, decrescente, e randômica.

Um código que apresente um `if(salario>=2000)`, por exemplo, precisa de três diferentes testes:

- Um cenário com salário menor do que 2000
- Um cenário com salário maior do que 2000
- Um cenário com salário igual a 2000

Afinal, quem nunca confundiu um > por um >= ?

O grande desafio da área dos testadores é encontrar todos as classes de equivalência; tarefa essa que não é fácil !

- Foque-se na classe que você está testando. Pense sobre o que você espera dela. Como ela deve funcionar? Se você passar tais parâmetros para ela, como ela deve reagir?



- Cuidando dos seus testes

Ao contrário do `@Before`, métodos anotados com `@After` são executados após a execução do método de teste.

Utilizamos métodos `@After` quando nossos testes consomem recursos que precisam ser finalizados. Exemplos podem ser testes que acessam banco de dados, abrem arquivos, abrem sockets, e etc.

(Apesar desses testes não serem mais considerados testes de unidade, afinal eles falam com outros sistemas, desenvolvedores utilizam JUnit para escrever testes de integração. Os mesmos são discutidos no curso online de [Testes de Integração](https://cursos.alura.com.br/course/teste-de-integracao)).

---

Métodos anotados com `@BeforeClass` são executados apenas uma vez, antes de todos os métodos de teste.

O método anotado com `@AfterClass`, por sua vez, é executado uma vez, após a execução do último método de teste da classe.

Eles podem ser bastante úteis quando temos algum recurso que precisa ser inicializado apenas uma vez e que pode ser consumido por todos os métodos de teste sem a necessidade de ser reinicializado.