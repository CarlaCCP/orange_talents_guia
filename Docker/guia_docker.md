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