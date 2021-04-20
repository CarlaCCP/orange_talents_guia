# Spring Boot API REST: Segurança da API, Cache e Monitoramento

- Paginação e Ordenação de recursos:

  - Pageable - interface de paginação

    Receberá os parametros da URL, que estão anotados como @RequestParam, para que sejam obrigatórios no GetMapping

    Obs: Fazer paginação é necessária para que aplicação não retorne uma quantidade grande de dados.

    -- O método inteiro terá que retornar o objeto Page - que será um jason com algumas configurações de paginação. 

    ```
    Pageable paginacao = PageRequest.of(pagina, qtd, Direction.DESC, ordenacao);
    ```

    

  - Outra forma de fazer paginação:

    Ao invés de passar todos os parâmetros como argumentos do método

    ```
    @RequestParam(required = false) String nomeCurso, 
    			@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao
    ```

    Passe somente a o objeto Pageable 

    ```
    Pageable paginacao
    ```

    E anote no main da aplicação, pois essa ferramenta não vem como padrão.

    ```
    @EnableSpringDataWebSupport
    ```

    Os parâmetros na URL ficarão desta forma:

    ```
    http://localhost:8080/topicos?page=0&size=3&sort=id,desc&sort=dataCriacao,asc
    ```

    Colocando como default 

- Spring Cache 

  Dependência - Usará um provedor de cache padrão - não aconselhável utilizar esse padrão em um ambiente   

  ```
  	
  		<dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-data-jpa</artifactId>
  		</dependency>
  ```

  Anotação main 

  ```
  @EnableCaching
  ```

  Anotação no GetMapping

  ```
  @Cacheable(value = "listaDeTopicos")
  ```

  O cache servirá para guardar em memória comandos de métodos passados, assim ele não repetirá o método, somente devolverá os resultados no mesmo método chamado anteriormente. 

  A string passada como parâmetro para a anotação `@Cacheable` funciona como um identificador único do cache.

  - Invalidação do Cache - necessário para que a requisição não fique desatualizada.

    Colocando a anotação em outra requisição - PostMapping		

    ```
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    ```

  Cuidados com o cache: ficar utilizando muito o cache pode piorar o processamento da aplicação. Dessa forma, onde é melhor utilizar? Em tabelas que raramente sofrem atualização

- Proteção com Spring Security

  ```
  <dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-security</artifactId>
  		</dependency>
  ```

  ```
  @EnableWebSecurity
  @Configuration
  public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  }
  ```

  Três métodos `configure` da classe webSecurityConfigurerAdapter

  

```
//Configuração de paginas estaticas
	@Override
	public void configure(WebSecurity web) throws Exception {

	}
	
	// Configuração de Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}
	
```

​	E a configuração de autorização 

```

@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		.anyRequest().authenticated() // qualquer outra requisição, deverá estar autenticado
		.and().formLogin(); // gera o formulário de autentificação
	}
```

Na classe usuário deveremos implementar a interface `UserDetails` que assumirá os detalhes do usuário. Ela trará os seguintes métodos

```
@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return perfis;
	}

	@Override
	public String getPassword() {
		
		return this.senha;
	}

	@Override
	public String getUsername() {
		
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
	
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return false;
	}

	@Override
	public boolean isEnabled() {
		
		return false;
	}

```

Deveremos criar uma nova entidade JPA "Perfil" que assumirá os perfis de autorização `GrantedAuthority` e terá o override do método getAuthority, passando o atributo da classe: 

```
@Override
	public String getAuthority() {
		
		return this.nome;
	}
```

​	Configuração de Autenticação

```
	@Autowired
	private AutentificacaoService autentificacaoService;
	
	// Configuração de Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autentificacaoService) 
		// passa o service responsável pelo processo de autenticacao 
		.passwordEncoder(new BCryptPasswordEncoder()); 
		// identifica qual hash criptografará a senha
	}
```

`AutentificacaoService` 

```
// Essa service é chamada assim que o botao de sign in e clicado
//irá procurar pelo username passado
@Service
public class AutentificacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = repository.findByEmail(username);
		
		if(usuario.isPresent()) {
			return usuario.get();
		}
		
		throw new UsernameNotFoundException("Dados inválidos!");
	}

	
}

```

- Gerando Token com JWT (Jason Web Token)

**`Bearer`** é um dos mecanismos de autenticação utilizados no protocolo HTTP, tal como o `Basic` e o `Digest`.

`AuthenticationManager` deve ser utilizada apenas na lógica de autenticação via *username/password*, para a geração do *token*.

- Monitoramento - Spring Boot Actuator
- Swagger