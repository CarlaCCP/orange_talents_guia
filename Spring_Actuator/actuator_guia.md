# Spring Boot Actuator 

Link de referência: https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints

Configure no seu pom.xml

```
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
</dependencies>
```

Principais endpoints: 

```
http://localhost:8080/actuator/health
```

Algumas configurações que poderão melhorar o visibilidade das informações. Configure o application.properties.

```
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=*

info.app.name=@project.name@
info.app.version=@project.version@
```

Não esqueça de proteger o endpoint na configuração de autenticação e autorização.

1º Habilitar somente o que é utilizado, para isto é necessário adicionar a propriedade:

```
management.endpoints.web.exposure.include=health,metrics,prometheus
```

2º Remover os não utilizados, para isto é necessário adicionar a propriedade:

```
management.endpoints.web.exposure.exclude=env,beans
```

- Utilizar o CORS pode ajudar na segurança

  O CORS (Cross-origin Resource Sharing) é um mecanismo utilizado pelos navegadores para compartilhar recursos entre diferentes origens. O CORS é uma especificação do W3C e faz uso de headers do HTTP para informar aos navegadores se determinado recurso pode ser ou não acessado.

  Permitindo receber somente de uma origem, aumenta demais a segurança das APIs do Spring Boot Actuator!

  Para isto, basta adicionar as seguintes propriedades no arquivo `application.properties`:

  ```
  management.endpoints.web.cors.allowed-origins=https://example.com
  management.endpoints.web.cors.allowed-methods=GET
  ```

**Um pouco sobre segurança em cloud-native **

Dica: Segurança deve ser tratada igualmente como qualquer outra feature do seu sistema. Durante todo o processo de desenvolvimento preocupações inerentes a segurança devem ser levantadas e tratadas de maneira regular e com frequência.

Incorporar segurança no seu design é um princípio bastante importante.

- Ofuscamento de dados sensíveis:

  - Logs: 

    Log de dados é um arquivo de texto gerado por um software para descrever eventos sobre o seu funcionamento, utilização por usuários ou interação com outros sistemas. Um log, após ser gerado, passa a ser incrementado ao longo do tempo com informações que permitem diagnosticar possíveis bugs! Spring Boot utiliza por padrão o Logback.

    ```
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    
    public class Exemplo {
    
        private final Logger logger = LoggerFactory.getLogger(Exemplo.class);
    
        public void log() {
            logger.info("Log de informação");
            logger.warn("Log de aviso, algo está errado ou faltando! cuidado!");
            logger.error("Log de erro, algo de errado aconteceu!");
            logger.debug("Log de depuração, contém informações mais refinadas, que são mais úteis para depurar um aplicativo");
            logger.trace("Log de rastreabilidade, contém informações mais refinadas do que o DEBUG");
        }
    
    }
    ```

    Link de referencia: https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-logging

    Exemplo: Colocar um log na Request, quando converte os dados entidade. 

    - Como logar em formato Json no Spring

      - Precisamos adicionar a seguinte propriedade:

        ```
        <properties>
            <!-- Omitidas outras propriedades -->
            <ch.qos.logback.version>1.2.3</ch.qos.logback.version>
        </properties>
        ```

        Agora que adicionamos as propriedades, precisamos adicionar um gerenciador de dependência, conforme abaixo:

        ```
        <dependencyManagement>
        
            <dependencies>
                <!-- Omitidas outras dependências -->
                <dependency>
                    <groupId>ch.qos.logback</groupId>
                    <artifactId>logback-core</artifactId>
                    <version>${ch.qos.logback.version}</version>
                </dependency>
        
                <dependency>
        
                    <groupId>ch.qos.logback</groupId>
                    <artifactId>logback-classic</artifactId>
                    <version>${ch.qos.logback.version}</version>
                </dependency>
        
                <dependency>
                    <groupId>ch.qos.logback</groupId>
                    <artifactId>logback-access</artifactId>
                    <version>${ch.qos.logback.version}</version>
                </dependency>
          <dependency>
              <groupId>net.logstash.logback</groupId>
              <artifactId>logstash-logback-encoder</artifactId>
              <version>6.4</version>
            </dependency>
            </dependencies>
        
        </dependencyManagement>
        ```

        

        Agora que está tudo configurado, precisamos adicionar o seguinte arquivo `logback-spring.xml` na pasta `/src/main/resources/`

        **logback-spring.xml**

        ```
        <configuration>
        
          <appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
            <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
              <providers>
                <timestamp/>
                <version/>
                <loggerName/>
                <threadName/>
                <logLevel/>
                <mdc/>
                <message/>
                <stackTrace/>
              </providers>
            </encoder>
          </appender>
        
          <logger name="jsonLogger" additivity="false" level="DEBUG">
            <appender-ref ref="consoleAppender"/>
          </logger>
        
          <root level="INFO">
            <appender-ref ref="consoleAppender"/>
          </root>
        
        </configuration>
        ```

        

        https://12factor.net/pt_br/

  - *Personal Identifiable Information* 

    Sempre que você precisar "logar" uma informação que seja passível de identificação de uma pessoa é necessário realizar o ofuscamento do dado. O ofuscamento é uma prática que "embaralha" os caracteres para proteger nossa informação de maneira que se a informação for analisada por qualquer fonte não seja possível identificar o dado. O termo adequado para dados que permitam identificar uma pessoa é PII *Personal Identifiable Information*

    A regra geral é sempre que tiver dúvida pergunte ao time de segurança. Em geral documentos pessoais, número de cartões de credito, senhas, informações pertinentes a saúde e informações que dizem aos dados pessoais devem ser ofuscadas no log da nossa aplicação ou qualquer outra camada que haja escrita do dado.

    Você deve ofuscar dados sensíveis sempre, sempre que houver um log com dado sensível. Os lugares mais comuns são:

    - Logs de aplicação
    - Logs de APIs no API Manager

- Encriptar os dados em trânsito

  Para nos proteger desses ataques **sempre** precisamos usar um canal de comunicação seguro, um modelo que nos permita nos autenticar e realizar a encriptação da mensagem antes do envio. Neste caso quando o atacante obter acesso às informações essas informações vão estar criptografadas de maneira que a informação não tenha serventia ao atacante.

- Segurança em nível de produção

  Principle of Least Privilege (POLP)  - Exemplo, se sua aplicação precise de acesso de leitura no banco de dados crie uma conta que permita somente leitura, não conceda privilégio de escrita sem a real necessidade. Esse tópico garante que nossa aplicação reduza o espaço ou brechas de segurança.

  link de referencia: https://digitalguardian.com/blog/what-principle-least-privilege-polp-best-practice-information-security-and-compliance 	



## Como implementar um Health Check utilizando Spring Boot Actuator

Link de referência: https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/health/Health.html  	

https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#writing-custom-healthindicators

Sobre os Status do objeto Health: https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/health/Status.html

- **DOWN:** Status indicando que o componente ou subsistema sofreu uma falha inesperada.
- **OUT_OF_SERVICE:** Status indicando que o componente ou subsistema foi retirado de serviço e não deve ser usado.
- **UNKNOWN:** Status indicando que o componente ou subsistema está em um estado desconhecido.
- **UP:** Status indicando que o componente ou subsistema está funcionando conforme o esperado.

```
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MeuHealthCheck implements HealthIndicator {

	@Override
	public Health health() {
		
		 Map<String, Object> details = new HashMap<>();
	        details.put("versão", "1.2.3");
	        details.put("descrição", "Meu primeiro Health Check customizado!");
	        details.put("endereço", "127.0.0.1");
	        
	        return Health.status(Status.UP).withDetails(details).build();
	}

}
```

