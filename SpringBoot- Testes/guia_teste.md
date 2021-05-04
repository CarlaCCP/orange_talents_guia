- **Autorização baseada em Roles**

```
.antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR")
```

- **Spring profiles**

   	Com profiles podemos ter configurações distintas para cada tipo de ambiente, como *desenvolvimento*, *testes* e *produção*.

  @Profile("dev") - na classe que você precisa diferenciar nos ambientes 

  - Qual é o perfil ativo? 

    Run as > Run Configurations > Arguments

    ```
    -Dspring.profiles.active=dev
    ```

    A anotação `@Profile(“prod”)` indica ao Spring que determinada classe deve apenas ser carregada se o profile ativo for *prod*.

- Testes Automatizados 

  A anotação `@SpringBootTest` serve para que o Spring inicialize o servidor e carregue os beans da aplicação durante a execução dos testes automatizados.

  - Anotação das classes de teste

    ```
    @RunWith(SpringRunner.class)
    @SpringBootTest
    ```

    

  - Anotação para testar interfaces - Troque o @SpringBootTest

    ```
    @DataJpaTest // anotação para testar interface 
    ```

    A anotação `@DataJpaTest` simplifica a escrita de testes automatizados de interfaces `Repository`.

  - Spring boot considera que você fará os teste com um banco de dados em memória
  - Mudando o banco de dados. Você deverá anotar na classe de teste `@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)`

  - É melhor utilizar um banco de dados vazio e/ou em memória nos testes. Faça um outro application.properties com as configurações do novo banco. 
  - Coloque `@ActiveProfiles("test")` - irá forçar que quando rodar a classe de testes, este profile será o ativo.
  - `spring.datasource.initialization-mode=never` (para não carregar o data.sql)

  - Teste em controller

    

```
package br.com.alura.forum.controller;

import static org.junit.Assert.*;

import java.net.URI;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") // para mudar o perfil ativo
@AutoConfigureMockMvc // para conseguirmos injetar o MockMvc
public class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc; // irá simular uma requisição mvc
	
	@Test
	public void deveriaDevolver400() throws Exception {
		URI uri = new URI ("/auth");
		String json = "{\"email\": \"email@email.com\", \"senha\": \"123476\"}";

		mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(400));
	}
	

}

```



- Deploy da aplicação 

  Run as > Maven Install (irá gerar o build com .jar)

  Para rodar a aplicação no terminal: java -jar forum-0.0.1-SNAPSHOT.jar

- **Externalizando senhas com variáveis de ambiente**

  ```
  # datasource
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.url=${FORUM_DATABASE_URL}
  spring.datasource.username=${FORUM_DATABASE_USERNAME}
  spring.datasource.password=${FORUM_DATABASE_PASSWORD}
  ```

  java -jar -Dspring.profiles.active=prod forum.jar

  No prompt

  export FORUM_DATABASE_URL=jdbc:h2:mem:alura-forum

  export FORUM_DATABASE_USERNAME=sa

  export FORUM_DATABASE_PASSWORD=

  export FORUM_JWT_SECRET=123456

  echo > $FORUM_DATABASE_URL  (para checar se configurou as variaveis de ambiente)

Ao se utilizar variáveis de ambiente, evitamos deixar exposto no código fonte da aplicação senhas e outras informações sensíveis.

Ao se utilizar variáveis de ambiente, podemos alterar facilmente uma ou mais propriedades da aplicação, sem que seja necessário alterar o código fonte dela.

- Gerando um deploy usando WAR

  - Mude o pom.xml

    O valor padrão da tag `<packaging>` é `jar`.

    ```
    <packaging>war</packaging>
    ```

  - Coloque a dependência 

    Em um deploy tradicional com arquivo `.war` a biblioteca do tomcat não deve ser embutida na aplicação.

    ```
    <dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-tomcat</artifactId>
    			<scope>provided</scope>
    		</dependency>
    	
    ```

  - Na classe main

    É necessário realizar essa alteração para que o Spring Boot seja inicializado corretamente no servidor de aplicação externo.

```
extends SpringBootServletInitializer
```

​	Sobrescreva o método configure 

```
@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(ForumApplication.class);
	}
```

- Deploy com Docker  e na nuvem

  - Crie um file na raiz do projeto chamado "Dockerfile"

     Arquivo `Dockerfile` serve justamente para indicar ao Docker o passo a passo de construção da imagem Docker de nossa aplicação, que utiliza o Spring Boot.

    ```
    FROM openjdk:8-jdk-alpine
    RUN addgroup -S spring && adduser -S spring -G spring
    USER spring:spring
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["java", "-jar", "/app.jar"]
    ```

  - No terminal rode - criando uma imagem no docker 

    ```
    docker build -t alura/forum .
    ```

  - Comando para rodar o container do docker (docker run alura/forum)

  - Setando o profile para o docker

    ```
    docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE='prod'-e FORUM_DATABASE_URL='jdbc:h2:mem:alura-forum' -e FORUM_DATABASE_USERNAME='sa' -e FORUM_JWT_SECRET='123456' alura/forum
    ```

    

- Deploy no heroku 

```
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx512m", "-Dserver.port=${PORT}", "-jar", "/app.jar"]

```

- No application. properties

  colocar, para não dar conflito com a porta do docker: 

  ```
  server.port=${PORT}
  ```

- No Config Vars - no site da sua aplicação (settings) será colocado as variaveis de ambiente 

