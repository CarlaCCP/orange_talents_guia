# Docker: Criando containers sem dor de cabeça

Virtual Machine - uma máquina lógica que possuirá uma parte da memória do servidor. Um pedaço do hardware. Melhor aproveitamento do hardware físico. "Hypervisor"

Porém as VM necessitam de custo de processamento de sistema operacionais. O custo pode ser mais caro. Custo de configuração. 

**Container**

Cada container usará o mesmo sistema operacional, tornando a aplicação bem mais leve e rápida.

**Docker**

É quem faz o intermédio entre os containers e o sistema operacional.

"Tecnologias de containers para prover ferramentas modernas para deployar e rodar aplicações"

**Docker compose**

"Um jeito fácil de definir e orquestrar múltiplos containers"

**Imagens**

Terá as instruções para a criação do container.

**Comados**

- docker ps (lista containers rodando)
- docker ps -a (lista todos os containers já feitos, até os parados)
- docker run -it ubuntu (irá tornar o seu terminal um terminal de dentro do container)
- docker rm idcontainer (remove)
- docker container prune (remove todos os containers inativos)
- docker rmi hello-world (remove imagens)
- docker run -d nome (não irá atrelar o terminal ao o container)
- docker stop idContainer (para a aplicação depois de 10 segundos)
- docker stop -t 0 idContainer (especifica o tempo que você quer aguardar para parar a aplicação)
- docker run -d -P dockersamples/static-site (para atrelar a porta da aplicação a nossa porta local)
- docker run -d -P --name meu-site dockersamples/static-site
- docker run -d -p 12345:80 dockersamples/static-site (a diferença é que nesse caso você esta atrelando a porta 12345 da sua maquina com a porta 80 do container)
- docker run -d -P -e AUTHOR="Carla" dockersamples/static-site(seta uma variável de ambiente)
- docker ps -q (retorna só os ids)
- docker stop $(docker ps -q)  (irá parar todos os containers)

**Layered Filesystem**

Toda imagem que baixamos é composta de uma ou mais camadas, e esse sistema tem o nome de ***Layered File System\***. Essas camadas podem ser **reaproveitadas** em outras imagens.

**Volumes**

Quando escrevemos em um *container*, assim que ele for removido, os dados também serão. Mas podemos criar um local especial dentro dele, e especificamos que esse local será o nosso **volume de dados**.

Quando criamos um volume de dados, o que estamos fazendo é apontá-lo para uma pequena pasta no **Docker Host**. Então, quando criamos um volume, criamos uma pasta dentro do *container*, e o que escrevermos dentro dessa pasta na verdade estaremos escrevendo do Docker Host.

Isso faz com que não percamos os nossos dados, pois o *container* até pode ser removido, mas a pasta no **Docker Host** ficará intacta.

```
docker run -v "/var/www" ubuntu
```

No exemplo acima, criamos o volume **/var/www**, mas a que pasta no **Docker Host** ele faz referência? Para descobrir, podemos inspecionar o *container*, executando o comando **`docker inspect`**, passando o seu **id** para o mesmo:

```
docker inspect 8cf7b40ce226
```

```
"Mounts": [
    {
        "Type": "volume",
        "Name": "5e1cbfd48d07284680552e56087c9d5196659600ccd6874bfa3831b51ddd0576",
        "Source": "/var/lib/docker/volumes/5e1cbfd48d07284680552e56087c9d5196659600ccd6874bfa3831b51ddd0576/_data",
        "Destination": "/var/www",
        "Driver": "local",
        "Mode": "",
        "RW": true,
        "Propagation": ""
    }
]
```

Nele, podemos ver que o **/var/www** será escrito na nossa máquina no diretório **/var/lib/docker/volumes/5e1cbfd48d07284680552e56087c9d5196659600ccd6874bfa3831b51ddd0576/_data**, endereço que foi gerado automaticamente pelo Docker. Ou seja, tudo que escrevermos na pasta **/var/www** do *container*, na verdade estaremos escrevendo na pasta **/var/lib/docker/volumes/5e1cbfd48d07284680552e56087c9d5196659600ccd6874bfa3831b51ddd0576/_data** da nossa máquina.

E ao remover o *container*, a pasta continuará na nossa máquina. Essa pasta gerada pelo Docker pode ser configurada, podemos dizer a pasta que será referenciada pela pasta **/var/www** do *container*. Por exemplo, se quisermos escrever dentro do Desktop da nossa máquina, devemos passá-lo antes do volume, separando-os com dois pontos. Além disso, vamos executar o *container* no modo interativo:

```
docker run -it -v "C:\Users\Alura\Desktop:/var/www" ubuntu
```

**Montando o dockerfile**

Exemplo: 

```
FROM node:latest
MAINTAINER Douglas Quintanilha
COPY . /var/www
WORKDIR /var/www
RUN npm install
ENTRYPOINT npm start
EXPOSE 3000
```

criando a imagem, passe o nome do seu usuário:

```
docker build -f Dockerfile -t douglasq/node .
```

depois crie o container com a imagem feita:

```
docker run -d -p 8080:3000 douglasq/node
```

- Usando variáveis de ambiente 

  ```
  FROM node:latest
  MAINTAINER Douglas Quintanilha
  ENV PORT=3000
  COPY . /var/www
  WORKDIR /var/www
  RUN npm install
  ENTRYPOINT npm start
  EXPOSE $PORT
  ```

**Comunicação entre containers***

- Networking no Docker

No Docker, por padrão, já existe uma ***default network\***. Isso significa que, quando criamos os nossos *containers*, por padrão eles funcionam na mesma rede

Então, o Docker criar uma rede virtual, em que todos os *containers* fazem parte dela, com os IPs automaticamente atribuídos. Mas quando os IPs são atribuídos, cada hora em que subirmos um *container*, ele irá receber um IP novo, que será determinado pelo Docker. Logo, se não sabemos qual o IP que será atribuído, isso não é muito útil quando queremos fazer a comunicação entre os *containers*. Por exemplo, podemos querer colocar dentro do aplicativo o endereço exato do banco de dados, e para saber exatamente o endereço do banco de dados, devemos configurar um nome para aquele *container*.

- Criando um rede e o container associando a rede:

```
docker network create --driver bridge minha-rede
```

```
docker run -it --name meu-container-de-ubuntu --network minha-rede ubuntu
```

Outros exemplos, associando o network em dois containers:

```
docker run -d --name meu-mongo --network minha-rede mongo
```

```
docker run --network minha-rede -d -p 8080:3000 douglasq/alura-books:cap05
```

**Tralhando com o Docker Compose**

Auxilia na construção de containers, sem precisar executar no terminal. 

Exemplo docker-compose.yml:

```
version: '3'
services:
    nginx:
        build:
            dockerfile: ./docker/nginx.dockerfile
            context: .
        image: carlapaula/nginx
        container_name: nginx
        port:
            - "80:80"
        networks:
            - production-network
        depends_on:
            - "node1"
            - "node2"
            - "node3"
    
    mongodb:
        image: mongo
        networks: 
            - production-network
    
    node1:
        build:
            dockerfile: ./docker/alura-books.dockerfile
            context: .
        image: carlapaula/alura-books
        container_name: alura-books1
        port:
            - "3000"
        networks:
            - production-network
        depends_on:
            - "mongodb"

    node2:
        build:
            dockerfile: ./docker/alura-books.dockerfile
            context: .
        image: carlapaula/alura-books
        container_name: alura-books2
        port:
            - "3000"
        networks:
            - production-network
        depends_on:
            - "mongodb"

    node3:
        build:
            dockerfile: ./docker/alura-books.dockerfile
            context: .
        image: carlapaula/alura-books
        container_name: alura-books3
        port:
            - "3000"
        networks:
            - production-network
        depends_on:
            - "mongodb"

networks:
    production-network:
        driver: bridge        
```

Depois dê o comando para verificar todas as pastas:

```
docker-compose build
```

E para criar todos os containers:

```
docker-compose up
```

Para remover e parar todos os containers:

```
docker-compose down
```

Caso queira restartar:

```
docker-compose restart
```



Segue a lista com os principais comandos utilizados durante o curso:

- Comandos relacionados às informações
  - `docker version` **-** exibe a versão do docker que está instalada.
  - `docker inspect ID_CONTAINER` **-** retorna diversas informações sobre o container.
  - `docker ps` **-** exibe todos os containers em execução no momento.
  - `docker ps -a` **-** exibe todos os containers, independentemente de estarem em execução ou não.

- Comandos relacionados à execução
  - `docker run NOME_DA_IMAGEM` **-** cria um container com a respectiva imagem passada como parâmetro.
  - `docker run -it NOME_DA_IMAGEM` **-** conecta o terminal que estamos utilizando com o do container.
  - `docker run -d -P --name NOME dockersamples/static-site` **-** ao executar, dá um nome ao container e define uma porta aleatória.
  - `docker run -d -p 12345:80 dockersamples/static-site` **-** define uma porta específica para ser atribuída à porta 80 do container, neste caso 12345.
  - `docker run -v "CAMINHO_VOLUME" NOME_DA_IMAGEM` **-** cria um volume no respectivo caminho do container.
  - `docker run -it --name NOME_CONTAINER --network NOME_DA_REDE NOME_IMAGEM` **-** cria um container especificando seu nome e qual rede deverá ser usada.

- Comandos relacionados à inicialização/interrupção
  - `docker start ID_CONTAINER` **-** inicia o container com id em questão.
  - `docker start -a -i ID_CONTAINER` **-** inicia o container com id em questão e integra os terminais, além de permitir interação entre ambos.
  - `docker stop ID_CONTAINER` **-** interrompe o container com id em questão.

- Comandos relacionados à remoção
  - `docker rm ID_CONTAINER` **-** remove o container com id em questão.
  - `docker container prune` **-** remove todos os containers que estão parados.
  - `docker rmi NOME_DA_IMAGEM` **-** remove a imagem passada como parâmetro.

- Comandos relacionados à construção de Dockerfile
  - `docker build -f Dockerfile` **-** cria uma imagem a partir de um Dockerfile.
  - `docker build -f Dockerfile -t NOME_USUARIO/NOME_IMAGEM` **-** constrói e nomeia uma imagem não-oficial.
  - `docker build -f Dockerfile -t NOME_USUARIO/NOME_IMAGEM CAMINHO_DOCKERFILE` **-** constrói e nomeia uma imagem não-oficial informando o caminho para o Dockerfile.

- Comandos relacionados ao Docker Hub
  - `docker login` **-** inicia o processo de login no Docker Hub.
  - `docker push NOME_USUARIO/NOME_IMAGEM` **-** envia a imagem criada para o Docker Hub.
  - `docker pull NOME_USUARIO/NOME_IMAGEM` **-** baixa a imagem desejada do Docker Hub.

- Comandos relacionados à rede
  - `hostname -i` **-** mostra o ip atribuído ao container pelo docker (funciona apenas dentro do container).
  - `docker network create --driver bridge NOME_DA_REDE` **-** cria uma rede especificando o driver desejado.
- Comandos relacionados ao docker-compose
  - `docker-compose build` **-** Realiza o build dos serviços relacionados ao arquivo docker-compose.yml, assim como verifica a sua sintaxe.
  - `docker-compose up` **-** Sobe todos os containers relacionados ao docker-compose, desde que o build já tenha sido executado.
  - `docker-compose down` **-** Para todos os serviços em execução que estejam relacionados ao arquivo docker-compose.yml.



Para rodar MySQL:

docker container run -d --name mysql --rm -p 3306:3306 -e MYSQL_DATABASE=ot4 -e MYSQL_ROOT_PASSWORD=123456 -v volume-mysql:/var/lib/mysql mysql